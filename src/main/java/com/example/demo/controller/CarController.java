package com.example.demo.controller;

import com.example.demo.dao.CarDAO;
import com.example.demo.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {
    private final CarDAO carDao;

    @Autowired
    public CarController(CarDAO carDao) {
        this.carDao = carDao;
    }

    @GetMapping
    public String listCars(Model model) {
        List<Car> cars = carDao.findAll();
        model.addAttribute("cars", cars);
        return "carList";
    }

    @GetMapping("/new")
    public String createCarForm(Model model) {
        return "carForm";
    }

    @PostMapping
    public String createCar(@ModelAttribute Car car) {
        carDao.save(car);
        return "redirect:/cars";
    }

    @GetMapping("/edit/{id}")
    public String editCarForm(@PathVariable int id, Model model) {
        Car car = carDao.findById(id);
        if (car == null) {
            return "redirect:/cars";
        }
        model.addAttribute("car", car);
        return "carEdit";
    }

    @PostMapping("/{id}")
    public String updateCar(@PathVariable int id, @ModelAttribute Car car) {
        carDao.update(id, car);
        return "redirect:/cars";
    }

    @DeleteMapping("/{id}")
    public String deleteCar(@PathVariable int id) {
        carDao.delete(id);
        return "redirect:/cars";
    }

    @GetMapping("/{id}")
    public String viewCarDetails(@PathVariable int id, Model model) {
        Car car = carDao.findById(id);
        model.addAttribute("car", car);
        return "carDetails";
    }
}
