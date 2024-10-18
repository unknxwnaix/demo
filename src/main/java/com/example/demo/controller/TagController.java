package com.example.demo.controller;

import com.example.demo.model.Tag;
import com.example.demo.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tags")
public class TagController {
    private final TagRepository tagRepository;

    @Autowired
    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping
    public String listTags(Model model) {
        List<Tag> tags = (List<Tag>) tagRepository.findAll();
        model.addAttribute("tags", tags);
        return "tagList";
    }

    @GetMapping("/search")
    public String searchTags(@RequestParam("keyword") String keyword, Model model) {
        List<Tag> tags = tagRepository.findByNameContaining(keyword);
        model.addAttribute("tags", tags);
        return "tagList";
    }

    @GetMapping("/new")
    public String createTagForm(Model model) {
        model.addAttribute("tag", new Tag());
        return "tagForm";
    }

    @PostMapping
    public String createTag(@ModelAttribute Tag tag) {
        tagRepository.save(tag);
        return "redirect:/tags";
    }

    @GetMapping("/edit/{id}")
    public String editTagForm(@PathVariable long id, Model model) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isEmpty()) {
            return "redirect:/tags";
        }
        model.addAttribute("tag", tag.get());
        return "tagEditForm";
    }

    @PostMapping("/{id}")
    public String updateTag(@PathVariable long id, @ModelAttribute Tag tag) {
        if (!tagRepository.existsById(id)) {
            return "redirect:/tags";
        }
        tag.setId(id);
        tagRepository.save(tag);
        return "redirect:/tags";
    }

    @DeleteMapping("/{id}")
    public String deleteTag(@PathVariable long id) {
        if (tagRepository.existsById(id)) {
            tagRepository.deleteById(id);
        }
        return "redirect:/tags";
    }

    @GetMapping("/{id}")
    public String viewTagDetails(@PathVariable long id, Model model) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isPresent()) {
            model.addAttribute("tag", tag.get());
        }
        return "tagDetails";
    }
}