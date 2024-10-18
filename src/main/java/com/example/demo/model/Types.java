package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Types {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Type name is required")
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Products> products;

    @OneToMany(mappedBy = "type")
    private List<Sizes> sizes;
}