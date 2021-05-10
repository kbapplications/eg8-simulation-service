package nl.fontys.energygrid.simulationservice.dto.intermediates;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class DateFilter {
    private LocalDateTime start;
    private LocalDateTime end;
}
