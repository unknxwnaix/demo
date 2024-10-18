package com.example.demo.controller;

import com.example.demo.model.Author;
import com.example.demo.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public String listAuthors(Model model) {
        List<Author> authors = (List<Author>) authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "authorList";
    }

    @GetMapping("/search")
    public String searchAuthors(@RequestParam("keyword") String keyword, Model model) {
        List<Author> authors = authorRepository.findByNameContaining(keyword);
        model.addAttribute("authors", authors);
        return "authorList";
    }

    @GetMapping("/new")
    public String createAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "authorForm";
    }

    @PostMapping
    public String createAuthor(@ModelAttribute Author author) {
        authorRepository.save(author);
        return "redirect:/authors";
    }

    @GetMapping("/edit/{id}")
    public String editAuthorForm(@PathVariable long id, Model model) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) {
            return "redirect:/authors";
        }
        model.addAttribute("author", author.get());
        return "authorEditForm";
    }

    @PostMapping("/{id}")
    public String updateAuthor(@PathVariable long id, @ModelAttribute Author author) {
        if (!authorRepository.existsById(id)) {
            return "redirect:/authors";
        }
        author.setId(id);
        authorRepository.save(author);
        return "redirect:/authors";
    }

    @DeleteMapping("/{id}")
    public String deleteAuthor(@PathVariable long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
        }
        return "redirect:/authors";
    }

    @GetMapping("/{id}")
    public String viewAuthorDetails(@PathVariable long id, Model model) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            model.addAttribute("author", author.get());
        }
        return "authorDetails";
    }
}