package com.example.demo.dao;

import com.example.demo.model.Person;
import java.util.List;

public interface IPersonDAO {
    Person findById(int id);
    List<Person> findAll();
    void save(Person person);
    void update(int id, Person person);
    void delete(int id);
}
