package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;

import id.ac.ui.cs.advprog.eshop.model.Car;

public interface CarService {
    Car create(Car car);
    List<Car> findAll();
    Car findById(String carId);
    void update(String carId, Car car);
    void deleteCarById(String carId);
}
