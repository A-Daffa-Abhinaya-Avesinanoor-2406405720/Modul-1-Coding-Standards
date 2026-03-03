package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    @Test
    void createCarPage_returnsCreateView() {
        Model model = new ExtendedModelMap();

        String view = carController.createCarPage(model);

        assertEquals("createCar", view);
        assertTrue(model.containsAttribute("car"));
    }

    @Test
    void createCarPost_redirectsToList() {
        Car car = new Car();
        car.setCarId("c-1");
        car.setCarName("Sedan");

        Model model = new ExtendedModelMap();
        String view = carController.createCarPost(car, model);

        assertEquals("redirect:listCar", view);
        verify(carService).create(car);
    }

    @Test
    void carListPage_returnsListView() {
        when(carService.findAll()).thenReturn(Collections.emptyList());
        Model model = new ExtendedModelMap();

        String view = carController.carListPage(model);

        assertEquals("carList", view);
        assertTrue(model.containsAttribute("cars"));
    }

    @Test
    void editCarPage_returnsEditView() {
        Car car = new Car();
        car.setCarId("c-1");
        when(carService.findById("c-1")).thenReturn(car);
        Model model = new ExtendedModelMap();

        String view = carController.editCarPage("c-1", model);

        assertEquals("editCar", view);
        assertTrue(model.containsAttribute("car"));
    }

    @Test
    void editCarPost_redirectsToList() {
        Car car = new Car();
        car.setCarId("c-1");

        Model model = new ExtendedModelMap();
        String view = carController.editCarPost(car, model);

        assertEquals("redirect:listCar", view);
        verify(carService).update("c-1", car);
    }

    @Test
    void deleteCar_redirectsToList() {
        String view = carController.deleteCar("c-1");

        assertEquals("redirect:listCar", view);
        verify(carService).deleteCarById("c-1");
    }
}
