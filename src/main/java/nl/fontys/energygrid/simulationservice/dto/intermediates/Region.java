package nl.fontys.energygrid.simulationservice.dto.intermediates;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private float sustainability;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ProductionDetail> productionDetails;
}
