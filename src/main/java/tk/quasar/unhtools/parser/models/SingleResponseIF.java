package tk.quasar.unhtools.parser.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
public abstract class SingleResponseIF {
  @JsonProperty("ident")
  public abstract String getIdentifier();

  @JsonProperty("rcardinality")
  public abstract String getCardinality();

  @JsonProperty("render_fib")
  public abstract FreeformResponse getResponse();
}
