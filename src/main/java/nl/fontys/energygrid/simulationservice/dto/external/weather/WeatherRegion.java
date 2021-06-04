package nl.fontys.energygrid.simulationservice.dto.external.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherRegion {
    private WeatherObj weather;
    private String original;
    private String region;
    private String country;
}
