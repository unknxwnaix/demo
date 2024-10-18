package com.example.demo.repositories;

import com.example.demo.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<Product, Integer> {
    List<Product> findByNameContaining(String keyword);
}
