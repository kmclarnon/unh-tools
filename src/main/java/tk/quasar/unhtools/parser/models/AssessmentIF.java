package tk.quasar.unhtools.parser.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

import java.util.List;

@HubSpotStyle
@Value.Immutable
public abstract class AssessmentIF {
  @JsonProperty("ident")
  public abstract String getIdentifier();

  public abstract String getTitle();

  @JsonProperty("section")
  @JacksonXmlElementWrapper(useWrapping = false)
  public abstract List<Section> getSections();
}
