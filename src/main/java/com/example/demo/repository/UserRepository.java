package com.example.demo.repository;

import com.example.demo.model.User;
import com.example.demo.model.roleEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "users")
public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String username);
    List<User> findByRolesNotContains(roleEnum role);
}