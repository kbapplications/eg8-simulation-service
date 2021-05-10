package nl.fontys.energygrid.simulationservice.controller;

import nl.fontys.energygrid.simulationservice.api.SimulationController;
import nl.fontys.energygrid.simulationservice.dto.SimulationDTO;
import nl.fontys.energygrid.simulationservice.dto.intermediates.DateFilter;
import nl.fontys.energygrid.simulationservice.services.SimulationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SimulationControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SimulationController.SimulateDelegate simulateDelegate;

    @Test
    public void postSimulationIsNotFoundTest() throws Exception {
        this.mockMvc.perform(post("/simulation2/from/y/to/x"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void postSimulationSuccessTest() throws Exception {
        // ARRANGE
        SimulationDTO dto = SimulationDTO.builder()
                .filter(DateFilter.builder().start(LocalDateTime.MIN).end(LocalDateTime.MAX).build())
                .timeslots(new ArrayList<>())
                .build();
        when(simulateDelegate.simulate(any(), any(), any())).thenReturn(dto);

        // ACT
        this.mockMvc.perform(post("/simulation/from/2019-09-07T00:00/to/2019-09-14T00:00"))
                .andExpect(status().isOk()); // ASSERT
    }

    @Test
    public void postSimulationIsBadRequestTest() throws Exception {
        this.mockMvc.perform(post("/simulation/from/y/to/x"))
                .andExpect(status().isBadRequest());
    }
}
