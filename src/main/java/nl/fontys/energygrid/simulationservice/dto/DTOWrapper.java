package nl.fontys.energygrid.simulationservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DTOWrapper<T extends iDTO> {
    private T data;
}
