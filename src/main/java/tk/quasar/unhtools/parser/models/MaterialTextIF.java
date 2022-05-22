package tk.quasar.unhtools.parser.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
public abstract class MaterialTextIF {
  @JsonProperty("texttype")
  public abstract String getType();

  @JacksonXmlText
  public abstract String getContent();
}
