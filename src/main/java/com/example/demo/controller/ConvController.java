package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConvController {
    @PostMapping("/conv")
    public String calculate(Model model,
                            @RequestParam(value = "money", required = false, defaultValue = "0") String moneyStr,
                            @RequestParam(value = "fromCurrency", required = false, defaultValue = "USD") String fromCurrency,
                            @RequestParam(value = "toCurrency", required = false, defaultValue = "USD") String toCurrency) {
        double money = Double.parseDouble(moneyStr);
        double result = 0.0;

        if (fromCurrency.equals("GBP")) {
            result = money / 0.75;
        } else if (fromCurrency.equals("PHP")) {
            result = money / 50.0;
        } else if (fromCurrency.equals("CNY")) {
            result = money / 6.5;
        } else if (fromCurrency.equals("EUR")) {
            result = money / 0.85;
        } else {
            result = money;
        }

        if (toCurrency.equals("GBP")) {
            result = result * 0.75;
        } else if (toCurrency.equals("PHP")) {
            result = result * 50.0;
        } else if (toCurrency.equals("CNY")) {
            result = result * 6.5;
        } else if (toCurrency.equals("EUR")) {
            result = result * 0.85;
        }

        String resultMessage = "Результат: " + result;

        model.addAttribute("result", resultMessage);

        return "conv";
    }
}
