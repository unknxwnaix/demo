package com.example.demo.controller;

import com.example.demo.model.Car;
import com.example.demo.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cars")
public class CarController {
    private final CarRepository carRepository;

    @Autowired
    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping
    public String listCars(Model model) {
        List<Car> cars = carRepository.findAll();
        model.addAttribute("cars", cars);
        return "carList";
    }

    @GetMapping("/search")
    public String searchCars(@RequestParam("keyword") String keyword, Model model) {
        List<Car> cars = carRepository.findByMakeContaining(keyword);
        model.addAttribute("cars", cars);
        return "carList";
    }

    @GetMapping("/new")
    public String createCarForm(Model model) {
        model.addAttribute("car", new Car());
        return "carForm";
    }

    @PostMapping
    public String createCar(@ModelAttribute Car car) {
        carRepository.save(car);
        return "redirect:/cars";
    }

    @GetMapping("/edit/{id}")
    public String editCarForm(@PathVariable int id, Model model) {
        Optional<Car> car = carRepository.findById(id);
        if (car.isEmpty()) {
            return "redirect:/cars";
        }
        model.addAttribute("car", car.get());
        return "carEdit";
    }

    @PostMapping("/{id}")
    public String updateCar(@PathVariable int id, @ModelAttribute Car car) {
        if (!carRepository.existsById(id)) {
            return "redirect:/cars";
        }
        car.setId(id);
        carRepository.save(car);
        return "redirect:/cars";
    }

    @DeleteMapping("/{id}")
    public String deleteCar(@PathVariable int id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
        }
        return "redirect:/cars";
    }

    @GetMapping("/{id}")
    public String viewCarDetails(@PathVariable int id, Model model) {
        Optional<Car> car = carRepository.findById(id);
        if (car.isPresent()) {
            model.addAttribute("car", car.get());
        }
        return "carDetails";
    }
}
