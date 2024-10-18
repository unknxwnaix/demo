package com.example.demo.controller;

import com.example.demo.model.Library;
import com.example.demo.repositories.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/libraries")
public class LibraryController {
    private final LibraryRepository libraryRepository;

    @Autowired
    public LibraryController(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    @GetMapping
    public String listLibraries(Model model) {
        List<Library> libraries = (List<Library>) libraryRepository.findAll();
        model.addAttribute("libraries", libraries);
        return "libraryList";
    }

    @GetMapping("/search")
    public String searchLibraries(@RequestParam("keyword") String keyword, Model model) {
        List<Library> libraries = libraryRepository.findByNameContaining(keyword);
        model.addAttribute("libraries", libraries);
        return "libraryList";
    }

    @GetMapping("/new")
    public String createLibraryForm(Model model) {
        model.addAttribute("library", new Library());
        return "libraryForm";
    }

    @PostMapping
    public String createLibrary(@ModelAttribute Library library) {
        libraryRepository.save(library);
        return "redirect:/libraries";
    }

    @GetMapping("/edit/{id}")
    public String editLibraryForm(@PathVariable long id, Model model) {
        Optional<Library> library = libraryRepository.findById(id);
        if (library.isEmpty()) {
            return "redirect:/libraries";
        }
        model.addAttribute("library", library.get());
        return "libraryEditForm";
    }

    @PostMapping("/{id}")
    public String updateLibrary(@PathVariable long id, @ModelAttribute Library library) {
        if (!libraryRepository.existsById(id)) {
            return "redirect:/libraries";
        }
        library.setId(id);
        libraryRepository.save(library);
        return "redirect:/libraries";
    }

    @DeleteMapping("/{id}")
    public String deleteLibrary(@PathVariable long id) {
        if (libraryRepository.existsById(id)) {
            libraryRepository.deleteById(id);
        }
        return "redirect:/libraries";
    }

    @GetMapping("/{id}")
    public String viewLibraryDetails(@PathVariable long id, Model model) {
        Optional<Library> library = libraryRepository.findById(id);
        if (library.isPresent()) {
            model.addAttribute("library", library.get());
        }
        return "libraryDetails";
    }
}