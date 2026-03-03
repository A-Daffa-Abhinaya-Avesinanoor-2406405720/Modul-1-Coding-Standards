package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryImplTest {

    private CarRepositoryImpl carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepositoryImpl();
    }

    @Test
    void create_whenCarIdNull_assignsId() {
        Car car = new Car();
        car.setCarName("Hatchback");
        car.setCarColor("Red");
        car.setCarQuantity(2);

        Car created = carRepository.create(car);

        assertNotNull(created.getCarId());
    }

    @Test
    void create_whenCarIdSet_keepsId() {
        Car car = new Car();
        car.setCarId("c-10");
        car.setCarName("SUV");
        car.setCarColor("Black");
        car.setCarQuantity(1);

        Car created = carRepository.create(car);

        assertEquals("c-10", created.getCarId());
    }

    @Test
    void findAll_whenEmpty_returnsEmptyIterator() {
        Iterator<Car> iterator = carRepository.findAll();

        assertFalse(iterator.hasNext());
    }

    @Test
    void findAll_whenMultiple_returnsAll() {
        Car car1 = new Car();
        car1.setCarId("c-1");
        carRepository.create(car1);
        Car car2 = new Car();
        car2.setCarId("c-2");
        carRepository.create(car2);

        Iterator<Car> iterator = carRepository.findAll();

        assertTrue(iterator.hasNext());
        assertEquals("c-1", iterator.next().getCarId());
        assertEquals("c-2", iterator.next().getCarId());
        assertFalse(iterator.hasNext());
    }

    @Test
    void findByID_whenMissing_returnsNull() {
        assertNull(carRepository.findByID("missing"));
    }

    @Test
    void findByID_whenNoMatchInNonEmptyList_returnsNull() {
        Car car = new Car();
        car.setCarId("c-1");
        carRepository.create(car);

        assertNull(carRepository.findByID("c-2"));
    }

    @Test
    void findByID_whenExists_returnsCar() {
        Car car = new Car();
        car.setCarId("c-1");
        carRepository.create(car);

        Car found = carRepository.findByID("c-1");

        assertNotNull(found);
        assertEquals("c-1", found.getCarId());
    }

    @Test
    void update_whenExists_updatesFields() {
        Car car = new Car();
        car.setCarId("c-1");
        car.setCarName("Old");
        car.setCarColor("White");
        car.setCarQuantity(1);
        carRepository.create(car);

        Car updated = new Car();
        updated.setCarId("c-1");
        updated.setCarName("New");
        updated.setCarColor("Black");
        updated.setCarQuantity(5);

        Car result = carRepository.update("c-1", updated);

        assertNotNull(result);
        assertEquals("New", result.getCarName());
        assertEquals("Black", result.getCarColor());
        assertEquals(5, result.getCarQuantity());
    }

    @Test
    void update_whenMissing_returnsNull() {
        Car updated = new Car();
        updated.setCarId("c-2");
        updated.setCarName("New");
        updated.setCarColor("Black");
        updated.setCarQuantity(5);

        assertNull(carRepository.update("c-2", updated));
    }

    @Test
    void update_whenNoMatchInNonEmptyList_returnsNull() {
        Car car = new Car();
        car.setCarId("c-1");
        carRepository.create(car);

        Car updated = new Car();
        updated.setCarId("c-2");
        updated.setCarName("New");
        updated.setCarColor("Black");
        updated.setCarQuantity(5);

        assertNull(carRepository.update("c-2", updated));
    }

    @Test
    void delete_whenExists_removesCar() {
        Car car = new Car();
        car.setCarId("c-3");
        carRepository.create(car);

        carRepository.delete("c-3");

        assertNull(carRepository.findByID("c-3"));
    }

    @Test
    void delete_whenMissing_noChange() {
        carRepository.delete("missing");

        assertNull(carRepository.findByID("missing"));
    }
}
