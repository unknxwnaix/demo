package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PersonDAO implements IPersonDAO {
    private Map<Integer, Person> persons = new HashMap<>();
    private int currentId = 1;

    @Override
    public Person findById(int id) {
        return persons.get(id);
    }

    @Override
    public List<Person> findAll() {
        return new ArrayList<>(persons.values());
    }

    @Override
    public void save(Person person) {
        person.setId(currentId++);
        persons.put(person.getId(), person);
    }

    @Override
    public void update(int id, Person updatedPerson) {
        Person person = persons.get(id);
        if (person != null) {
            person.setName(updatedPerson.getName());
            person.setAge(updatedPerson.getAge());
            person.setHeight(updatedPerson.getHeight());
            persons.put(id, person);
        }
    }

    public void delete(int id) {
        persons.remove(id);
    }
}
