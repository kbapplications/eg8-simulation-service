package nl.fontys.energygrid.simulationservice.dto.external.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TempObj {
    private float current;
    private float min;
    private float max;
}
