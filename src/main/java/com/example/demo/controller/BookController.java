package com.example.demo.controller;

import com.example.demo.dao.BookDAO;
import com.example.demo.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDao;

    @Autowired
    public BookController(BookDAO bookDao) {
        this.bookDao = bookDao;
    }

    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = bookDao.findAll();
        model.addAttribute("books", books);
        return "bookList";
    }

    @GetMapping("/new")
    public String createBookForm(Model model) {
        return "bookForm";
    }

    @PostMapping
    public String createBook(@ModelAttribute Book book) {
        bookDao.save(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable int id, Model model) {
        Book book = bookDao.findById(id);
        if (book == null) {
            return "redirect:/books";
        }
        model.addAttribute("book", book);
        return "bookEdit";
    }

    @PostMapping("/{id}")
    public String updateBook(@PathVariable int id, @ModelAttribute Book book) {
        bookDao.update(id, book);
        return "redirect:/books";
    }

    // Удаление книги с использованием метода DELETE
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {
        bookDao.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String viewBookDetails(@PathVariable int id, Model model) {
        Book book = bookDao.findById(id);
        model.addAttribute("book", book);
        return "bookDetails";
    }
}
