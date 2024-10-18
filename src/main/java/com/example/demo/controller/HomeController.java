package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class HomeController {

//    @GetMapping("/")
//    String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
//        String username = (userDetails != null) ? userDetails.getUsername() : "Гость";
//        model.addAttribute("username", username);
//        return "home";
//    }
}
