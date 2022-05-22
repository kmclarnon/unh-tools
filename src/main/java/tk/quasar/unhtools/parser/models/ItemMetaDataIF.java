package tk.quasar.unhtools.parser.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
public abstract class ItemMetaDataIF {
  @JsonProperty("qtimetadata")
  public abstract QTimeData getTimeData();
}
