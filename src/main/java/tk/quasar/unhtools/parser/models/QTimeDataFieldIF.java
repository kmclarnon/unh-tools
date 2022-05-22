package tk.quasar.unhtools.parser.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

import java.util.List;

@HubSpotStyle
@Value.Immutable
public abstract class QTimeDataFieldIF {
  @JsonProperty("qtimedatafield")
  public abstract List<QField> getFields();
}
