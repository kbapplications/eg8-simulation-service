package nl.fontys.energygrid.simulationservice.dto;

import lombok.Builder;
import lombok.Data;
import nl.fontys.energygrid.simulationservice.dto.intermediates.DateFilter;
import nl.fontys.energygrid.simulationservice.dto.intermediates.Timeslot;

import java.util.List;

@Builder
@Data
public class SimulationDTO {
    private DateFilter filter;
    private List<Timeslot> timeslots;
}
