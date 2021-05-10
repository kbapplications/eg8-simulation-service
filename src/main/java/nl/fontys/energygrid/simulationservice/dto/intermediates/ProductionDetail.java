package nl.fontys.energygrid.simulationservice.dto.intermediates;

import lombok.Builder;
import lombok.Data;
import nl.fontys.energygrid.simulationservice.model.EnergyType;

@Data
@Builder
public class ProductionDetail {
    private EnergyType type;
    private int amount;
}
