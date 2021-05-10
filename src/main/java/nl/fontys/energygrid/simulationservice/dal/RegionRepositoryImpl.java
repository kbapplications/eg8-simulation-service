package nl.fontys.energygrid.simulationservice.dal;

import lombok.Data;
import nl.fontys.energygrid.simulationservice.dto.intermediates.Region;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Repository
public class RegionRepositoryImpl implements RegionRepository {
    private List<Region> regions = new ArrayList<>();

    public RegionRepositoryImpl() {
        regions.add(Region.builder()
                .id("1")
                .name("Breda")
                .build());
        regions.add(Region.builder()
                .id("2")
                .name("Tilburg")
                .build());
        regions.add(Region.builder()
                .id("3")
                .name("Roosendaal")
                .build());
        regions.add(Region.builder()
                .id("4")
                .name("'s-Hertogenbosch")
                .build());
    }

    @Override
    public Optional<Region> getRegionById(Long id) {
        return getRegions().stream().filter(r -> r.getId().equals(id.toString())).findFirst();
    }
}
