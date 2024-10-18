package com.example.demo.repositories;

import com.example.demo.model.Car;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends BaseRepository<Car, Integer> {
    List<Car> findByMakeContaining(String keyword);
}
