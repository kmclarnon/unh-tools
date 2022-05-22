package tk.quasar.unhtools.parser.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
@JsonPropertyOrder({"name", "type", "text"})
public abstract class OutputRowIF {
  public abstract String getName();
  public abstract QuestionType getType();
  public abstract String getText();
}
