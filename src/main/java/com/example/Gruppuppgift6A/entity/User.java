package com.example.Gruppuppgift6A.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "users")
@NoArgsConstructor
public class User {

    @Id
    private final UUID id =  UUID.randomUUID();

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @OneToMany
    private List<Book> books = new ArrayList<>();
}
