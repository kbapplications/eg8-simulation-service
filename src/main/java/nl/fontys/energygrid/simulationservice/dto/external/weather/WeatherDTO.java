package nl.fontys.energygrid.simulationservice.dto.external.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDTO {
    private List<WeatherRegion> regions;
}
