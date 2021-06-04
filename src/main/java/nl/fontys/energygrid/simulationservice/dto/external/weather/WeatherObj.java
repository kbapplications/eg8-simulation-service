package nl.fontys.energygrid.simulationservice.dto.external.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherObj {
    private float visibility;
    private float windSpeed;
    private int clouds;
    private Long sunrise;
    private Long sunset;
    private TempObj temp;
}
