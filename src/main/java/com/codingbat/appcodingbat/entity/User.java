package com.codingbat.appcodingbat.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany
    private Set<Solution> solutions;
}
