package tk.quasar.unhtools;

import com.google.common.collect.ImmutableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import tk.quasar.unhtools.parser.Parser;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class MainController {
  private static final FileChooser.ExtensionFilter ZIP_FILES = new FileChooser.ExtensionFilter(
      "Exported zip (*.zip)",
      "*.zip");
  private static final FileChooser.ExtensionFilter CSV_FILES = new FileChooser.ExtensionFilter(
      "Comma Separated Values (*.csv)",
      "*.csv");

  @FXML
  private Label input;

  @FXML
  private Button run;

  @FXML
  private Label output;

  private List<Path> inputPaths;

  @FXML
  protected void onChooseInput() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(ZIP_FILES);
    List<File> files = fileChooser.showOpenMultipleDialog(input.getScene().getWindow());
    setInput(files);
  }

  @FXML
  public void onRun() {
    if (inputPaths.isEmpty()) {
      output.setText("Please select some input first");
      return;
    }

    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(CSV_FILES);
    fileChooser.setInitialFileName("parsed-questions");
    File file = fileChooser.showSaveDialog(input.getScene().getWindow());
    if (file != null) {
      try {
        Parser.parse(inputPaths, file.getAbsolutePath());
        output.setText("Success");
      } catch (Exception e) {
        output.setText("Parser encountered an exception!");
      }
    }
  }

  private void setInput(List<File> files) {
    inputPaths = files == null
        ? Collections.emptyList()
        : files.stream().map(File::toPath).collect(ImmutableList.toImmutableList());

    if (inputPaths.isEmpty()) {
      input.setText("");
      run.setDisable(true);
    } else if (inputPaths.size() == 1) {
      input.setText(inputPaths.get(0).toString());
      run.setDisable(false);
    } else {
      input.setText(String.format("%s files selected", files.size()));
      run.setDisable(false);
    }
  }
}