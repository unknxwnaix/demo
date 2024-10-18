package com.example.demo.repositories;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends BaseRepository<Person, Integer> {
    List<Person> findByNameContaining(String keyword);
}
