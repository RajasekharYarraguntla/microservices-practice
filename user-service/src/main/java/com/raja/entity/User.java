package com.raja.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "users")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable = false)
    private String password;
    private String role;
    @Column(nullable = false, unique = true)
    private String email;

}
