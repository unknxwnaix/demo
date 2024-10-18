package com.example.demo.repository;

import com.example.demo.model.Orders;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "order")
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUsername(String username);
    default List<Orders> findAllSortedById() {
        return findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}
