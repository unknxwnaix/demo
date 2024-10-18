package com.example.demo.controller;

import com.example.demo.model.Article;
import com.example.demo.model.Author;
import com.example.demo.model.Library;
import com.example.demo.model.Publisher;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;
    private final LibraryRepository libraryRepository;
    private final PublisherRepository publisherRepository;
    private final TagRepository tagRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository,
                             AuthorRepository authorRepository,
                             LibraryRepository libraryRepository,
                             PublisherRepository publisherRepository, TagRepository tagRepository) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.libraryRepository = libraryRepository;
        this.publisherRepository = publisherRepository;
        this.tagRepository = tagRepository;
    }

    // Отображение всех статей
    @GetMapping
    public String listArticles(Model model) {
        List<Article> articles = (List<Article>) articleRepository.findAll();
        model.addAttribute("articles", articles);
        return "articleList";
    }

    // Поиск статей по ключевому слову
    @GetMapping("/search")
    public String searchArticles(@RequestParam("keyword") String keyword, Model model) {
        List<Article> articles = articleRepository.findByTitleContaining(keyword);
        model.addAttribute("articles", articles);
        return "articleList";
    }

    // Форма создания новой статьи
    @GetMapping("/new")
    public String createArticleForm(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("authors", authorRepository.findAll());  // Добавляем список авторов
        model.addAttribute("libraries", libraryRepository.findAll()); // Добавляем список библиотек
        model.addAttribute("publishers", publisherRepository.findAll()); // Добавляем список издателей
        model.addAttribute("tags", tagRepository.findAll()); // Добавляем список издателей
        return "articleForm";
    }

    // Создание новой статьи
    @PostMapping
    public String createArticle(@ModelAttribute Article article) {
        articleRepository.save(article);
        return "redirect:/articles";
    }

    // Форма редактирования статьи
    @GetMapping("/edit/{id}")
    public String editArticleForm(@PathVariable long id, Model model) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isEmpty()) {
            return "redirect:/articles";
        }
        model.addAttribute("article", article.get());
        model.addAttribute("authors", authorRepository.findAll());  // Добавляем список авторов
        model.addAttribute("libraries", libraryRepository.findAll()); // Добавляем список библиотек
        model.addAttribute("publishers", publisherRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());
        return "articleEditForm";
    }

    // Обновление статьи
    @PostMapping("/{id}")
    public String updateArticle(@PathVariable long id, @ModelAttribute Article article) {
        if (!articleRepository.existsById(id)) {
            return "redirect:/articles";
        }
        article.setId(id);
        articleRepository.save(article);
        return "redirect:/articles";
    }

    // Удаление статьи
    @DeleteMapping("/{id}")
    public String deleteArticle(@PathVariable long id) {
        if (articleRepository.existsById(id)) {
            articleRepository.deleteById(id);
        }
        return "redirect:/articles";
    }

    // Просмотр деталей статьи
    @GetMapping("/{id}")
    public String viewArticleDetails(@PathVariable long id, Model model) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            model.addAttribute("article", article.get());
        }
        return "articleDetails";
    }
}
