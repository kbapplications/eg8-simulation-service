package nl.fontys.energygrid.simulationservice.dal;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UrlUtils {
    public String formatUrl(String url, Map<String, String> values) {
        String newUrl = url;
        values.forEach((k, v) -> {
            newUrl.replace("{" + k + "}", v);
        });
        return newUrl;
    }
    public String formatUrl(String url, String key, String value) {
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        return formatUrl(url, map);
    }
}
