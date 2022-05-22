package tk.quasar.unhtools.parser.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
public abstract class ChoiceIF {
  @JsonProperty("ident")
  public abstract String getIdentifier();

  public abstract Material getMaterial();
}
