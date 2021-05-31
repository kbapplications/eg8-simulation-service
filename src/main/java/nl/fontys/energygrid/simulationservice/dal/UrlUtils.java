package nl.fontys.energygrid.simulationservice.dal;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UrlUtils {
    public String formatUrl(String url, Map<String, String> values) {
        for (Map.Entry<String, String> entry : values.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            url = url.replace("{" + k + "}", v);
        }
        return url;
    }
    public String formatUrl(String url, String key, String value) {
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        return formatUrl(url, map);
    }
}
