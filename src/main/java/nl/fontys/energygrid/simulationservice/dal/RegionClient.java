package nl.fontys.energygrid.simulationservice.dal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nl.fontys.energygrid.simulationservice.dto.DTOWrapper;
import nl.fontys.energygrid.simulationservice.dto.external.ExternalRegion;
import nl.fontys.energygrid.simulationservice.dto.intermediates.ProductionDetail;
import nl.fontys.energygrid.simulationservice.dto.intermediates.Region;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class RegionClient {

    private static final String REGIONS_URL = "http://localhost:28080/api/regions/";
    private static final String REGION_BY_ID_URL = "http://localhost:28080/api/regions/{id}";

    private final RestTemplate template;
    private final UrlUtils utils;

    private final ObjectMapper mapper;

    public List<Region> getRegions() {
        DTOWrapper wrapper = template.getForObject(REGIONS_URL, DTOWrapper.class);
        if(wrapper == null)
            return new ArrayList<>();
        List<ExternalRegion> exRegions = mapper.convertValue(wrapper.getData(), new TypeReference<>(){});
        return externalToLocalRegion(exRegions);
    }

    public Region getRegionById(String id) {
        DTOWrapper wrapper = template.getForObject(utils.formatUrl(REGION_BY_ID_URL, "id", id), DTOWrapper.class);
        if(wrapper == null)
            return null;
        List<ExternalRegion> exRegions = mapper.convertValue(wrapper.getData(), new TypeReference<>(){});
        return externalToLocalRegion(exRegions).get(0);
    }

    private Region externalToLocalRegion(ExternalRegion exRegion) {
        List<ProductionDetail> prodDetails = new ArrayList<>();
        if(exRegion.getEnergy() != null && !exRegion.getEnergy().isEmpty()) {
            exRegion.getEnergy().forEach(e -> {
                ProductionDetail pd = ProductionDetail.builder()
                        .amount(e.getValue())
                        .type(e.getEnergyType())
                        .doesProduce(e.getDoesProduce())
                        .build();
                prodDetails.add(pd);
            });
        }
        return Region.builder()
                .id(exRegion.getId().toString())
                .name(exRegion.getName())
                .productionDetails(prodDetails)
                .build();
    }

    private List<Region> externalToLocalRegion(List<ExternalRegion> exRegions) {
        List<Region> regions = new ArrayList<>();
        exRegions.forEach(er -> regions.add(externalToLocalRegion(er)));
        return regions;
    }

}
