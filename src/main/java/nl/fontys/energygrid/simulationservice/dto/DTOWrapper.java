package nl.fontys.energygrid.simulationservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DTOWrapper {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> filter;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> orderBy;
}
