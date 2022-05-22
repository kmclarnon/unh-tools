package tk.quasar.unhtools.parser.models;

import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
public abstract class QuestestinteropIF {
  public abstract String getSchemaLocation();
  public abstract Assessment getAssessment();
}
