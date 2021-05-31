package nl.fontys.energygrid.simulationservice.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.energygrid.simulationservice.model.EnergyType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExternalEnergy {
    @JsonIgnore
    private Long regionId;
    @JsonProperty("energy_type")
    private EnergyType energyType;

    @JsonProperty("does_produce")
    private Boolean doesProduce;

    private Integer value;
    private UnitType unit;
}