package com.example.demo.repository;

import com.example.demo.model.Types;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "types")
public interface TypesRepository extends JpaRepository<Types, Long> {
    List<Types> findByNameContaining(String name);
}