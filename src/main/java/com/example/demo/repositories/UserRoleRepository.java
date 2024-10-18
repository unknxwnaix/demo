package com.example.demo.repositories;

import com.example.demo.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    // Здесь можно добавить методы, если это необходимо
}
