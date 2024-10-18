package com.example.demo.repository;

import com.example.demo.model.Sizes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "sizes")
public interface SizesRepository extends JpaRepository<Sizes, Long> {
    List<Sizes> findByTypeId(Long typeId);
}
