package com.example.demo.dao;

import java.util.List;

public interface IGenericDAO<T> {
    void save(T entity);
    void update(int id, T entity);
    void delete(int id);
    T findById(int id);
    List<T> findAll();
}
