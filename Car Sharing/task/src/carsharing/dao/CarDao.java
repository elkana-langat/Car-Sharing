package carsharing.dao;

import carsharing.model.Car;

import java.util.List;

public interface CarDao {

    List<Car> findAll(int id);

    Car findById(int id);

    void add(String name, int company_id);

    void deleteById(int id);

    Car findByName(String carName);

    List<Car> findCarsNotRented(int id);
}
