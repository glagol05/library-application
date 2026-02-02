package com.example.models;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String name;
    private String author;
    private LocalDate publishDate;
    private boolean available;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection
    @CollectionTable(name = "book_genres", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "genre")
    private List<String> genres;

    @ElementCollection
    @CollectionTable(name = "book_tags", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "tag")
    private List<String> tags;

    protected Book() {}

    public Book(String name, String author, LocalDate publishDate,
                boolean available, User user,
                List<String> genres, List<String> tags) {
        this.name = name;
        this.author = author;
        this.publishDate = publishDate;
        this.available = available;
        this.user = user;
        this.genres = genres;
        this.tags = tags;
    }

    public UUID getId() { return id; }
}
