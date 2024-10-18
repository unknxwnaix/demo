package com.example.demo.controller;

import com.example.demo.model.Publisher;
import com.example.demo.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/publishers")
public class PublisherController {
    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherController(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @GetMapping
    public String listPublishers(Model model) {
        List<Publisher> publishers = (List<Publisher>) publisherRepository.findAll();
        model.addAttribute("publishers", publishers);
        return "publisherList";
    }

    @GetMapping("/search")
    public String searchPublishers(@RequestParam("keyword") String keyword, Model model) {
        List<Publisher> publishers = publisherRepository.findByNameContaining(keyword);
        model.addAttribute("publishers", publishers);
        return "publisherList";
    }

    @GetMapping("/new")
    public String createPublisherForm(Model model) {
        model.addAttribute("publisher", new Publisher());
        return "publisherForm";
    }

    @PostMapping
    public String createPublisher(@ModelAttribute Publisher publisher) {
        publisherRepository.save(publisher);
        return "redirect:/publishers";
    }

    @GetMapping("/edit/{id}")
    public String editPublisherForm(@PathVariable long id, Model model) {
        Optional<Publisher> publisher = publisherRepository.findById(id);
        if (publisher.isEmpty()) {
            return "redirect:/publishers";
        }
        model.addAttribute("publisher", publisher.get());
        return "publisherEditForm";
    }

    @PostMapping("/{id}")
    public String updatePublisher(@PathVariable long id, @ModelAttribute Publisher publisher) {
        if (!publisherRepository.existsById(id)) {
            return "redirect:/publishers";
        }
        publisher.setId(id);
        publisherRepository.save(publisher);
        return "redirect:/publishers";
    }

    @DeleteMapping("/{id}")
    public String deletePublisher(@PathVariable long id) {
        if (publisherRepository.existsById(id)) {
            publisherRepository.deleteById(id);
        }
        return "redirect:/publishers";
    }

    @GetMapping("/{id}")
    public String viewPublisherDetails(@PathVariable long id, Model model) {
        Optional<Publisher> publisher = publisherRepository.findById(id);
        if (publisher.isPresent()) {
            model.addAttribute("publisher", publisher.get());
        }
        return "publisherDetails";
    }
}