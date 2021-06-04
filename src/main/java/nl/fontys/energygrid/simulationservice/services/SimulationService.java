package nl.fontys.energygrid.simulationservice.services;

import lombok.RequiredArgsConstructor;
import nl.fontys.energygrid.simulationservice.api.SimulationController;
import nl.fontys.energygrid.simulationservice.dal.RegionClient;
import nl.fontys.energygrid.simulationservice.dal.WeatherClient;
import nl.fontys.energygrid.simulationservice.dto.SimulationDTO;
import nl.fontys.energygrid.simulationservice.dto.external.weather.WeatherDTO;
import nl.fontys.energygrid.simulationservice.dto.external.weather.WeatherObj;
import nl.fontys.energygrid.simulationservice.dto.external.weather.WeatherRegion;
import nl.fontys.energygrid.simulationservice.dto.intermediates.DateFilter;
import nl.fontys.energygrid.simulationservice.dto.intermediates.ProductionDetail;
import nl.fontys.energygrid.simulationservice.dto.intermediates.Region;
import nl.fontys.energygrid.simulationservice.dto.intermediates.Timeslot;
import nl.fontys.energygrid.simulationservice.model.EnergyType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SimulationService implements SimulationController.SimulateDelegate {
    private static final int MINUTES_TILL_NEXT_PERIOD = 60;
    private static final ZoneId ZONE = ZoneId.of("Europe/Berlin");

    private static final float MAX_SUSTAINABILITY_PERCENTAGE = 300f;
    private static final float DEFAULT_SUSTAINABILITY_PERCENTAGE = 100f;

    private final RegionClient regionClient;
    private final WeatherClient weatherClient;

    @Override
    public SimulationDTO simulate(LocalDateTime from, LocalDateTime to, String regionId) {
        var result = SimulationDTO.builder();
        result.filter(DateFilter.builder().start(from).end(to).build());

        List<Region> regions = new ArrayList<>();
        if(regionId == null || regionId.isBlank()) {
            regions = regionClient.getRegions();
        } else {
            var region = regionClient.getRegionById(regionId);
            if(region != null)
                regions.add(region);
        }

        List<Timeslot> timeslots = new ArrayList<>();
        if(!regions.isEmpty()) {
            var loops = 0;
            while (from.isBefore(to) && loops < 2500) {
                loops++;

                timeslots.add(calculate(from, regions));
                from = nextPeriod(from);
            }
        }
        result.timeslots(timeslots);
        return result.build();
    }

    private LocalDateTime nextPeriod(LocalDateTime from) {
        return from.plusMinutes(MINUTES_TILL_NEXT_PERIOD);
    }

    private Timeslot calculate(LocalDateTime from, List<Region> regions) {

        var timeslotBuilder = Timeslot.builder();
        timeslotBuilder.regions(new ArrayList<>());
        timeslotBuilder.datetime(from.toString());
        timeslotBuilder.date(from.toLocalDate().toString());
        timeslotBuilder.time(from.toLocalTime().toString());
        var timeslot = timeslotBuilder.build();

        ZoneOffset zoneOffSet = ZONE.getRules().getOffset(from);
        Long timestamp = from.toEpochSecond(zoneOffSet);
        WeatherDTO weatherData = weatherClient.getWeatherForRegions(regions.stream().map(region -> region.getName()).collect(Collectors.toList()), timestamp);

        for(Region region : regions) {
            List<ProductionDetail> productionDetails = region.getProductionDetails();

            Optional<WeatherRegion> weather = weatherData.getRegions().stream().filter(r -> r.getRegion().equals(region.getName())).findFirst();

            weather.ifPresent(weatherRegion -> productionDetails.forEach((productionDetail) -> {
                productionDetail.setAmount(Math.round(
                        getProduction(
                                productionDetail.getAmount(),
                                productionDetail.getType(),
                                weatherRegion.getWeather(),
                                timestamp)));
            }));

            region.setProductionDetails(productionDetails);

            region.setProduction(region.getProductionDetails().stream().filter(ProductionDetail::isDoesProduce).mapToInt(ProductionDetail::getAmount).sum());
            region.setConsumption(region.getProductionDetails().stream().filter(p -> !p.isDoesProduce()).mapToInt(ProductionDetail::getAmount).sum());

            if(region.getConsumption() <= 0 && region.getProduction() <= 0) {
                region.setSustainability(DEFAULT_SUSTAINABILITY_PERCENTAGE);
            }
            else if(region.getConsumption() <= 0) {
                region.setSustainability(MAX_SUSTAINABILITY_PERCENTAGE);
            }
            else {
                float sustainability = ((float) region.getProduction() / (float) region.getConsumption()) * 100;

                if(sustainability > MAX_SUSTAINABILITY_PERCENTAGE) {
                    sustainability = MAX_SUSTAINABILITY_PERCENTAGE;
                }

                region.setSustainability(sustainability);
            }

            timeslot.getRegions().add(region);
        }

        return timeslot;
    }

    private float getProduction(float amount, EnergyType type, WeatherObj weather, Long timestamp) {
        return amount * getModifier(type, weather, timestamp);
    }

    private float getModifier(EnergyType type, WeatherObj weather, Long timestamp) {
        switch (type) {
            case WIND_PARK:
                return weather.getWindSpeed();
            case SOLAR_HOME:
            case SOLAR_PARK:
                long secondsWithSun = weather.getSunset() - weather.getSunrise();
                long current = secondsWithSun - ((weather.getSunset() - timestamp) / 1000);

                return (Math.abs((secondsWithSun / 2f) - current) / (secondsWithSun / 2f));
            default:
                float min = 0.9f;
                float max = 1.1f;

                return min + new Random().nextFloat() * (max - min);
        }
    }
}
