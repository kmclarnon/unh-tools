package tk.quasar.unhtools.parser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.jsoup.Jsoup;
import tk.quasar.unhtools.parser.models.Item;
import tk.quasar.unhtools.parser.models.OutputRow;
import tk.quasar.unhtools.parser.models.Questestinterop;
import tk.quasar.unhtools.parser.models.QuestionType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Parser {
  private static final Set<String> IGNORED_FILES = ImmutableSet.of("imsmanifest.xml", "assessment_meta.xml");
  private static final XmlMapper XML_MAPPER = createXmlMapper();
  private static final CsvMapper CSV_MAPPER = createCsvMapper();

  public static void parse(List<Path> inputPaths, String outputPath) {
    List<OutputRow> output = inputPaths.stream()
        .map(Parser::getOutputFromZip)
        .flatMap(List::stream)
        .collect(ImmutableList.toImmutableList());

    writeOutput(output, Paths.get(outputPath));
  }

  private static List<OutputRow> getOutputFromZip(Path path) {
    List<OutputRow> output = new ArrayList<>();
    try (FileSystem fs = FileSystems.newFileSystem(path, Collections.emptyMap())) {
      fs.getRootDirectories().forEach(p -> {
        try {
          List<Path> xmlFiles = Files.walk(p)
              .filter(entry -> Files.isRegularFile(entry) && entry.getFileName().toString().endsWith(".xml"))
              .collect(ImmutableList.toImmutableList());

          for (Path zipPath : xmlFiles) {
            if (shouldIgnoreFile(zipPath)) {
              continue;
            }
            output.addAll(getOutput(zipPath));
          }
        } catch (IOException e) {
          throw new RuntimeException("Failed to traverse zip files", e);
        }
      });
    } catch (Exception e) {
      throw new RuntimeException("Failed to create filesystem", e);
    }

    return output;
  }

  private static List<OutputRow> getOutput(Path path) {
    Questestinterop model = parseModel(path);
    return model.getAssessment()
        .getSections()
        .stream()
        .flatMap(s -> s.getItems().stream())
        .map(Parser::toOutput)
        .collect(ImmutableList.toImmutableList());
  }

  private static OutputRow toOutput(Item item) {
    return OutputRow.builder()
        .setName(item.getTitle())
        .setText(Jsoup.parse(item.getPresentation().getMaterial().getText().getContent()).text())
        .setType(QuestionType.getQuestionType(item))
        .build();
  }

  private static void writeOutput(List<OutputRow> rows, Path path) {
    try {
      Files.deleteIfExists(path);
    } catch (Exception e) {
      throw new RuntimeException("Failed to delete existing output file", e);
    }

    try {
      CsvSchema schema = CSV_MAPPER.schemaFor(OutputRow.class).withHeader();
      try (SequenceWriter writer = CSV_MAPPER.writer(schema).writeValues(path.toFile())) {
        writer.writeAll(rows);
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to write to output", e);
    }
  }

  private static Questestinterop parseModel(Path path) {
    try(InputStream is = Files.newInputStream(path)) {
      return XML_MAPPER.readValue(is, Questestinterop.class);
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse xml", e);
    }
  }

  private static boolean shouldIgnoreFile(Path path) {
    String name = path.getFileName().toString();
    return IGNORED_FILES.contains(name);
  }

  private static XmlMapper createXmlMapper() {
    XmlMapper mapper = new XmlMapper();
    mapper.registerModule(new Jdk8Module());
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return mapper;
  }

  private static CsvMapper createCsvMapper() {
    CsvMapper mapper = new CsvMapper();
    mapper.registerModule(new Jdk8Module());
    return mapper;
  }
}
