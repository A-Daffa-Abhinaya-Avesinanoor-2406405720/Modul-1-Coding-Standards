package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    void create_delegatesToRepository() {
        Car car = new Car();
        when(carRepository.create(car)).thenReturn(car);

        Car result = carService.create(car);

        assertSame(car, result);
        verify(carRepository).create(car);
    }

    @Test
    void findAll_delegatesToRepository() {
        Iterator<Car> iterator = Collections.<Car>emptyList().iterator();
        when(carRepository.findAll()).thenReturn(iterator);

        carService.findAll();

        verify(carRepository).findAll();
    }

    @Test
    void findById_delegatesToRepository() {
        Car car = new Car();
        when(carRepository.findByID("c-1")).thenReturn(car);

        Car result = carService.findById("c-1");

        assertSame(car, result);
        verify(carRepository).findByID("c-1");
    }

    @Test
    void update_delegatesToRepository() {
        Car car = new Car();

        carService.update("c-1", car);

        verify(carRepository).update("c-1", car);
    }

    @Test
    void delete_delegatesToRepository() {
        carService.deleteCarById("c-1");

        verify(carRepository).delete("c-1");
    }
}
