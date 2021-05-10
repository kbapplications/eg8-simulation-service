package nl.fontys.energygrid.simulationservice.dal;

import nl.fontys.energygrid.simulationservice.dto.intermediates.Region;

import java.util.List;
import java.util.Optional;

public interface RegionRepository {
    List<Region> getRegions();
    Optional<Region> getRegionById(Long id);
}
