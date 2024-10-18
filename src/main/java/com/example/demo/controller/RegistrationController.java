package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import com.example.demo.model.roleEnum;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @GetMapping("/registration")
    public String regView() {
        return "registration";
    }

    @PostMapping("/registration")
    public String reg(User user, Model model) {
        if (user.getUsername() == null || user.getPassword() == null) {
            model.addAttribute("message", "Имя пользователя и пароль не могут быть пустыми");
            return "registration";
        }

        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(roleEnum.USER));

        userRepository.save(user);

        return "redirect:/login";
    }

}
