package nl.fontys.energygrid.simulationservice.dto.intermediates;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("sustainability_percentage")
    private float sustainability;
    private List<ProductionDetail> productionDetails;
}
