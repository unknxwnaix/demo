package com.example.demo.repositories;

import com.example.demo.model.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends BaseRepository<Book, Long> {
    List<Book> findByTitleContaining(String keyword);
}
