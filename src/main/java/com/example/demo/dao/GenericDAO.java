package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;

public class GenericDAO<T> implements IGenericDAO<T> {
    protected List<T> entities = new ArrayList<>();
    private int currentId = 1;

    @Override
    public void save(T entity) {
        try {
            var method = entity.getClass().getMethod("setId", int.class);
            method.invoke(entity, currentId++);
        } catch (Exception e) {
            e.printStackTrace();
        }
        entities.add(entity);
    }

    @Override
    public void update(int id, T entity) {
        int index = findIndexById(id);
        if (index != -1) {
            entities.set(index, entity);
        }
    }

    @Override
    public void delete(int id) {
        entities.removeIf(entities -> id == findIndexById(id));
    }

    @Override
    public T findById(int id) {
        int index = findIndexById(id);
        return (index != -1) ? entities.get(index) : null;
    }

    @Override
    public List<T> findAll() {
        return entities;
    }

    private int findIndexById(int id) {
        for (int i = 0; i < entities.size(); i++) {
            try {
                var method = entities.get(i).getClass().getMethod("getId");
                int entityId = (int) method.invoke(entities.get(i));
                if (entityId == id) {
                    return i;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1; // Если не найден
    }
}
