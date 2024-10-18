package com.example.demo.model;


import jakarta.persistence.*;

@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private roleEnum role;

    // Конструкторы, геттеры и сеттеры
    public UserRole() {}

    public UserRole(Long userId, roleEnum role) {
        this.userId = userId;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public roleEnum getRole() {
        return role;
    }

    public void setRole(roleEnum role) {
        this.role = role;
    }
}
