package nl.fontys.energygrid.simulationservice.services;

import lombok.RequiredArgsConstructor;
import nl.fontys.energygrid.simulationservice.api.SimulationController;
import nl.fontys.energygrid.simulationservice.dal.RegionClient;
import nl.fontys.energygrid.simulationservice.dto.SimulationDTO;
import nl.fontys.energygrid.simulationservice.dto.intermediates.DateFilter;
import nl.fontys.energygrid.simulationservice.dto.intermediates.ProductionDetail;
import nl.fontys.energygrid.simulationservice.dto.intermediates.Region;
import nl.fontys.energygrid.simulationservice.dto.intermediates.Timeslot;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SimulationService implements SimulationController.SimulateDelegate {
    private static final int MINUTES_TILL_NEXT_PERIOD = 60;

    private final RegionClient regionClient;

    @Override
    public SimulationDTO simulate(LocalDateTime from, LocalDateTime to, String regionId) {
        var result = SimulationDTO.builder();
        result.filter(DateFilter.builder().start(from).end(to).build());

        List<Timeslot> timeslots = new ArrayList<>();
        var loops = 0;
        while(from.isBefore(to) && loops < 2500) {
            loops++;

            timeslots.add(calculate(from, regionId));
            from = nextPeriod(from);
        }

        result.timeslots(timeslots);
        return result.build();
    }

    private LocalDateTime nextPeriod(LocalDateTime from) {
        return from.plusMinutes(MINUTES_TILL_NEXT_PERIOD);
    }

    private Timeslot calculate(LocalDateTime from, String regionId) {
        var timeslotBuilder = Timeslot.builder();
        timeslotBuilder.regions(new ArrayList<>());
        timeslotBuilder.datetime(from.toString());
        timeslotBuilder.date(from.toLocalDate().toString());
        timeslotBuilder.time(from.toLocalTime().toString());
        var timeslot = timeslotBuilder.build();

        List<Region> regions = new ArrayList<>();
        if(regionId == null || regionId.isBlank()) {
            regions = regionClient.getRegions();
        } else {
            var region = regionClient.getRegionById(regionId);
            if(region != null)
                regions.add(region);
        }

        if(regions == null)
            regions = new ArrayList<>();

        for(Region region : regions) {
            region.setProduction(region.getProductionDetails().stream().filter(ProductionDetail::isDoesProduce).mapToInt(ProductionDetail::getAmount).sum());
            region.setConsumption(region.getProductionDetails().stream().filter(p -> !p.isDoesProduce()).mapToInt(ProductionDetail::getAmount).sum());
            region.setSustainability(((float) region.getProduction() / (float) region.getConsumption()) * 100);
            timeslot.getRegions().add(region);
        }

        return timeslot;
    }
}
