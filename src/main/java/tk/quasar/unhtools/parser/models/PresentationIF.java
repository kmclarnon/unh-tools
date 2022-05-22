package tk.quasar.unhtools.parser.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.immutables.validation.ImmutableConditions;
import org.immutables.value.Value;

import java.util.Optional;

@HubSpotStyle
@Value.Immutable
public abstract class PresentationIF {
  public abstract Material getMaterial();

  @JsonProperty("response_lid")
  public abstract Optional<MultipleChoiceResponse> getMultipleChoiceResponse();

  @JsonProperty("response_str")
  public abstract Optional<SingleResponse> getSingleResponse();

  @Value.Check
  public void validate() {
    ImmutableConditions.checkValid(
        getMultipleChoiceResponse().isPresent() || getSingleResponse().isPresent(),
        "Must have at least one response type");
  }
}
