package com.example.Gruppuppgift6A.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "books")
public class Book {

    public enum Genre {
        SCIFI, HORROR, ROMANCE, FANTASY, THRILLER
    }

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    boolean available;

    @ManyToMany
    Set<Author> authors = new HashSet<>();

    String borrower;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Genre genre;

}
