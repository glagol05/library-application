package com.example.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String name;

    @Column(unique = true)
    private String email;

    protected User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UUID getId() { return id; }
}
