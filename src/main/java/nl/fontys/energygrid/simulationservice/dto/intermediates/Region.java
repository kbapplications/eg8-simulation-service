package nl.fontys.energygrid.simulationservice.dto.intermediates;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Region {
    private String id;
    private String name;
    private int consumption;
    private int production;
    private List<ProductionDetail> productionDetails;
}
