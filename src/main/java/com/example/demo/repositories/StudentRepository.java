package com.example.demo.repositories;

import com.example.demo.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends BaseRepository<Student, Integer> {
    List<Student> findByNameContaining(String keyword);
}
