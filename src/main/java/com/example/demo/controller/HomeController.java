package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @GetMapping("/")
    String home() {
        return "home";
    }

    @PostMapping("/home")
    String toPrintText(Model model, @RequestParam(value = "NamePrint", required = false, defaultValue = "") String name) {
        if (name == null || name.isEmpty()) {
            name = "Пустая строка";
        }
        model.addAttribute("name", name);
        return "/home";
    }

    @GetMapping ("/calc")
    String calc() {
        return "calc";
    }

    @GetMapping ("/conv")
    String conv() {
        return "conv";
    }
}
