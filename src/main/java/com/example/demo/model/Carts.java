package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Carts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    private String username;

    @NotNull(message = "Creation date is required")
    private LocalDateTime createdAt;

    @NotBlank(message = "Status is required")
    private String status;

    @OneToMany(mappedBy = "cart")
    private List<Orders> orders;

    public Carts(String username, LocalDateTime createdAt, String status) {
        this.username = username;
        this.createdAt = createdAt;
        this.status = status;
    }
}