package nl.fontys.energygrid.simulationservice.controller;

import nl.fontys.energygrid.simulationservice.api.AbstractController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class AbstractControllerTests {

    private AbstractController abstractController;

    private boolean testMethod() {
        return true;
    }

    @Before
    public void init() {
        abstractController = new AbstractController();
    }


    @Test
    public void callSuccessTest() {
        //ARRANGE
        boolean expectedBoolean = true;

        //ACT
        boolean actualBoolean = abstractController.call("testMethod", this::testMethod);

        //ASSERT
        assertEquals(expectedBoolean, actualBoolean);
    }

    @Test(expected = NullPointerException.class)
    public void callIsNullTest() {
        //ACT
        abstractController.call("", null);
    }
}
