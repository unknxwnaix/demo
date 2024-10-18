package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Получение списка всех книг
    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "bookList";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam("keyword") String keyword, Model model) {
        List<Book> books = bookRepository.findByTitleContaining(keyword);
        model.addAttribute("books", books);
        return "bookList";
    }

    // Переход на форму создания книги
    @GetMapping("/new")
    public String createBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "bookForm";
    }

    // Создание новой книги
    @PostMapping
    public String createBook(@ModelAttribute Book book) {
        bookRepository.save(book);
        return "redirect:/books";
    }

    // Переход на форму редактирования книги
    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable long id, Model model) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            return "redirect:/books";
        }
        model.addAttribute("book", book.get());
        return "bookEdit";
    }

    // Обновление книги
    @PostMapping("/{id}")
    public String updateBook(@PathVariable long id, @ModelAttribute Book book) {
        if (!bookRepository.existsById(id)) {
            return "redirect:/books";
        }
        book.setId(id);
        bookRepository.save(book);
        return "redirect:/books";
    }

    // Удаление книги
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        }
        return "redirect:/books";
    }

    // Просмотр деталей книги
    @GetMapping("/{id}")
    public String viewBookDetails(@PathVariable long id, Model model) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
        }
        return "bookDetails";
    }
}
