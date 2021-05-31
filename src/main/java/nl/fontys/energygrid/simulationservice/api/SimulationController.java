package nl.fontys.energygrid.simulationservice.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.fontys.energygrid.simulationservice.dto.DTOWrapper;
import nl.fontys.energygrid.simulationservice.dto.SimulationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/simulation")
@RequiredArgsConstructor
public class SimulationController extends AbstractController {

    public interface SimulateDelegate {
        SimulationDTO simulate(LocalDateTime from, LocalDateTime to, String regionId);
    }

    private final Optional<SimulateDelegate> simulateDelegate;

    @PostMapping("/from/{start}/to/{end}")
    public ResponseEntity<DTOWrapper> simulate(@PathVariable String start,
                                   @PathVariable String end,
                                   @RequestParam(required = false) String regionId) {
        if(simulateDelegate.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }


        LocalDateTime from;
        LocalDateTime to;
        try {
            from = LocalDateTime.parse(start, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            to = LocalDateTime.parse(end, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch(Exception ex) {
            return ResponseEntity.badRequest().build();
        }
        SimulationDTO res = call("SimulationController.simulate", () -> simulateDelegate.get().simulate(from, to, regionId));

        if(res == null) {
            return ResponseEntity.notFound().build();
        }

        DTOWrapper dto = DTOWrapper.builder()
                .data(res)
                .build();
        return ResponseEntity.ok(dto);
    }
}
