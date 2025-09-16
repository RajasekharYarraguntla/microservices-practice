package com.raja.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "users")
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // Constructors, getters, setters
    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

}
