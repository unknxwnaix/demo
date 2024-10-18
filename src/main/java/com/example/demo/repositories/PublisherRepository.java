package com.example.demo.repositories;

import com.example.demo.model.Publisher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends CrudRepository<Publisher, Long> {
    List<Publisher> findByNameContaining(String keyword);
}