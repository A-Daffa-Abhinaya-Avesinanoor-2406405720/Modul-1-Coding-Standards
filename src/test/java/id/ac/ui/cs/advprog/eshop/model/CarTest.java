package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarTest {
    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("c-1");
        car.setCarName("Sedan");
        car.setCarColor("Blue");
        car.setCarQuantity(5);
    }

    @Test
    void getCarId_returnsValue() {
        assertEquals("c-1", car.getCarId());
    }

    @Test
    void getCarName_returnsValue() {
        assertEquals("Sedan", car.getCarName());
    }

    @Test
    void getCarColor_returnsValue() {
        assertEquals("Blue", car.getCarColor());
    }

    @Test
    void getCarQuantity_returnsValue() {
        assertEquals(5, car.getCarQuantity());
    }
}
