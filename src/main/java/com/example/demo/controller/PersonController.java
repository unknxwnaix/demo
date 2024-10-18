package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/persons")
public class PersonController {
    private final PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public String listPersons(Model model) {
        List<Person> persons = personRepository.findAll();
        model.addAttribute("persons", persons);
        return "personList";
    }

    @GetMapping("/search")
    public String searchPersons(@RequestParam("keyword") String keyword, Model model) {
        List<Person> persons = personRepository.findByNameContaining(keyword);
        model.addAttribute("persons", persons);
        return "personList";
    }

    @GetMapping("/new")
    public String createPersonForm(Model model) {
        model.addAttribute("person", new Person());
        return "personForm";
    }

    @PostMapping
    public String createPerson(@ModelAttribute Person person) {
        personRepository.save(person);
        return "redirect:/persons";
    }

    @GetMapping("/edit/{id}")
    public String editPersonForm(@PathVariable int id, Model model) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isEmpty()) {
            return "redirect:/persons";
        }
        model.addAttribute("person", person.get());
        return "personEdit";
    }

    @PostMapping("/{id}")
    public String updatePerson(@PathVariable int id, @ModelAttribute Person person) {
        if (!personRepository.existsById(id)) {
            return "redirect:/persons";
        }
        person.setId(id);
        personRepository.save(person);
        return "redirect:/persons";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable int id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
        }
        return "redirect:/persons";
    }

    @GetMapping("/{id}")
    public String viewPersonDetails(@PathVariable int id, Model model) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            model.addAttribute("person", person.get());
        }
        return "personDetails";
    }
}
