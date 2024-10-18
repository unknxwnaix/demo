package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CalcController {

    @PostMapping("/calc")
    public String calculate(RedirectAttributes redirectAttributes,
                            @RequestParam(value = "FirstValue", required = false, defaultValue = "0") String firstValue,
                            @RequestParam(value = "SecondValue", required = false, defaultValue = "0") String secondValue,
                            @RequestParam(value = "operation", required = false, defaultValue = "add") String operation) {

        double a = Double.parseDouble(firstValue);
        double b = Double.parseDouble(secondValue);
        double result = 0.0;

        switch (operation) {
            case "add":
                result = a + b;
                break;
            case "subtract":
                result = a - b;
                break;
            case "multiply":
                result = a * b;
                break;
            case "divide":
                result = a / b;
                break;
        }

        redirectAttributes.addFlashAttribute("result", "Результат: " + result);
        return "redirect:/result";
    }

    @GetMapping("/result")
    public String showResult(Model model) {
        return "result"; // Возвращаем страницу для отображения результата
    }
}
