//package nl.fontys.energygrid.simulationservice.controller;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import nl.fontys.energygrid.productionservice.dto.DtoWrapper;
//import nl.fontys.energygrid.productionservice.dto.request.production.CreateProductionDto;
//import nl.fontys.energygrid.productionservice.dto.response.production.ProductionDto;
//import nl.fontys.energygrid.productionservice.dto.response.production.ProductionListDto;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.mockito.ArgumentMatchers.isNull;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//
//@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
//public class SimulationControllerTests
//{
//    @Mock
//    private ProductionController.ProductionDelegate productionDelegate;
//    @Mock
//    private ProductionController.CreateProductionDelegate createProductionDelegate;
//
//
//    private ProductionController productionController;
//
//    private ProductionController emptyProductionController;
//
//    @Before
//    public void init() {
//        productionDelegate = mock(ProductionController.ProductionDelegate.class);
//        createProductionDelegate = mock(ProductionController.CreateProductionDelegate.class);
//
//        productionController = new ProductionController(Optional.of(productionDelegate), Optional.of(createProductionDelegate));
//        emptyProductionController = new ProductionController(Optional.empty(), Optional.empty());
//    }
//
//    @Test
//    public void getProductionTest() throws JsonProcessingException {
//        //ARRANGE
//        ProductionDto expectedProductionDto = ProductionDto.builder()
//                .regionId("1")
//                .regionName("EnergyGrid")
//                .energy(720)
//                .build();
//
//        List<ProductionDto> actualProductionDto;
//        when(productionDelegate.retrieveProduction(isNull(),isNull())).thenReturn(Collections.singletonList(expectedProductionDto));
//
//        HttpStatus expectedHttpStatus = HttpStatus.OK;
//        HttpStatus actualHttpStatus;
//
//        //ACT
//        ResponseEntity<DtoWrapper> response = productionController.getProduction(null, null);
//
//        //ASSERT
//        assertNotNull(response.getBody());
//
//        actualProductionDto = ((ProductionListDto)response.getBody().getData()).getProductions();
//        actualHttpStatus = response.getStatusCode();
//
//        assertEquals(Collections.singletonList(expectedProductionDto), actualProductionDto);
//        assertEquals(expectedHttpStatus, actualHttpStatus);
//    }
//
//    @Test
//    public void getProductionNoResultTest() throws JsonProcessingException {
//        //ARRANGE
//        when(productionDelegate.retrieveProduction(isNull(),isNull())).thenReturn(null);
//
//        HttpStatus expectedHttpStatus = HttpStatus.NOT_FOUND;
//        HttpStatus actualHttpStatus;
//
//        //ACT
//        ResponseEntity<DtoWrapper> response = productionController.getProduction(null,null);
//
//        //ASSERT
//        actualHttpStatus = response.getStatusCode();
//
//        assertEquals(expectedHttpStatus, actualHttpStatus);
//    }
//
//    @Test
//    public void getProductionNoDelegateTest()
//    {
//
//        //ARRANGE
//        HttpStatus expectedStatus = HttpStatus.NOT_IMPLEMENTED;
//
//        String actualMessage;
//        HttpStatus actualStatus;
//
//        //ACT
//        ResponseEntity<DtoWrapper> response = emptyProductionController.getProduction(1L,"coal");
//
//        //ASSERT
//        actualStatus = response.getStatusCode();
//
//        assertEquals(expectedStatus, actualStatus);
//    }
//
//    @Test
//    public void postProductionNoDelegateTest()
//    {
//
//        //ARRANGE
//        HttpStatus expectedStatus = HttpStatus.NOT_IMPLEMENTED;
//
//        CreateProductionDto createProductionDto = CreateProductionDto.builder()
//                .regionId("0L")
//                .regionName("Nederland")
//                .energy(720)
//                .energyType("COAL")
//                .build();
//
//        String actualMessage;
//        HttpStatus actualStatus;
//
//        //ACT
//        ResponseEntity<DtoWrapper> response = emptyProductionController.postProduction(createProductionDto);
//
//        //ASSERT
//        actualStatus = response.getStatusCode();
//
//        assertEquals(expectedStatus, actualStatus);
//    }
//}
