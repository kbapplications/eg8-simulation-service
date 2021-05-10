package nl.fontys.energygrid.simulationservice.api;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class AbstractController {
    public <T> T call(String operation, Supplier<T> supplier) {
        log.info(">> {}", operation);
        final long start = System.currentTimeMillis();
        long duration = 0L;
        try {
            T retVal = supplier.get();
            duration = System.currentTimeMillis() - start;
            log.info("<< {} [{} ms]", operation, duration);
            return retVal;
        } catch(Throwable t) {
            duration = System.currentTimeMillis() - start;
            log.warn("<< {} (FAULT) [{} ms]", operation, duration);
            throw t;
        }
    }
}
