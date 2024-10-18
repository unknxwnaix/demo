package com.example.demo.controller;

import com.example.demo.dao.IPersonDAO;
import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/persons")
public class PersonController {

    private final IPersonDAO personDao;

    @Autowired
    public PersonController(IPersonDAO personDao) {
        this.personDao = personDao;
    }

    @GetMapping
    public String listPersons(Model model) {
        List<Person> persons = personDao.findAll();
        model.addAttribute("persons", persons);
        return "personList";
    }

    @GetMapping("/new")
    public String createPersonForm(Model model) {
        return "personForm";
    }

    @PostMapping
    public String createPerson(@ModelAttribute Person person) {
        personDao.save(person);
        return "redirect:/persons";
    }

    @GetMapping("/edit/{id}")
    public String editPersonForm(@PathVariable int id, Model model) {
        Person person = personDao.findById(id);
        model.addAttribute("person", person);
        return "personEdit";
    }

    @PostMapping("/{id}")
    public String updatePerson(@PathVariable int id, @ModelAttribute Person person) {
        personDao.update(id, person);
        return "redirect:/persons";
    }

    @PostMapping("/{id}/delete")
    public String deletePerson(@PathVariable int id) {
        personDao.delete(id);
        return "redirect:/persons";
    }

    @GetMapping("/{id}")
    public String viewPersonDetails(@PathVariable int id, Model model) {
        Person person = personDao.findById(id);
        model.addAttribute("person", person);
        return "personDetails"; // Имя вашего представления
    }
}
