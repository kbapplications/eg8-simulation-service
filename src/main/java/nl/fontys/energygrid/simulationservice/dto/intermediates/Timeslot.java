package nl.fontys.energygrid.simulationservice.dto.intermediates;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Timeslot {
    private String datetime;
    private String date;
    private String time;
    private boolean isNight;

    private List<Region> regions;
}
