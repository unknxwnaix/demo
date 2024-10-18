package com.example.demo.repository;

import com.example.demo.model.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "carts")
public interface CartsRepository extends JpaRepository<Carts, Long> {
    Optional<Carts> findByUsername(String username);
}
