package nl.fontys.energygrid.simulationservice.dal;

import lombok.RequiredArgsConstructor;
import nl.fontys.energygrid.simulationservice.dto.external.weather.WeatherDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RequiredArgsConstructor
@Component
public class WeatherClient {
    private static final String WEATHER_URL = "https://europe-west1-steam-bee-310608.cloudfunctions.net/weather?regions={regions}&date={date}";

    private final RestTemplate template;
    private final UrlUtils utils;

    public WeatherDTO getWeatherForRegion(String region, Long datetime) {
        return getWeatherForRegions(Collections.singletonList(region), datetime);
    }
    public WeatherDTO getWeatherForRegions(List<String> regions, Long datetime) {
        Map<String, String> params = new HashMap<>();
        params.put("regions", String.join(",", regions));
        params.put("date", datetime.toString());
        String url = utils.formatUrl(WEATHER_URL, params);
        return template.getForObject(url, WeatherDTO.class);
    }
}
