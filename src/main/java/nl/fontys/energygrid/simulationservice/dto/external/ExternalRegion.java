package nl.fontys.energygrid.simulationservice.dto.external;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExternalRegion {
    private Long id;
    private String name;

    private int population;
    private float surface;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ExternalEnergy> energy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
