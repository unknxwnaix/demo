package com.example.demo.repository;

import com.example.demo.model.Brands;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "brands")
public interface BrandsRepository extends JpaRepository<Brands, Long> {
}
