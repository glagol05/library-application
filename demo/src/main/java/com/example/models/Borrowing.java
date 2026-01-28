package com.example.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "borrowings")
public class Borrowing {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime borrowedAt;
    private LocalDateTime dueAt;
    private LocalDateTime returnedAt;

    protected Borrowing() {}

    public Borrowing(Book book, User user, LocalDateTime borrowedAt, LocalDateTime dueAt, LocalDateTime returnedAt) {
        this.book = book;
        this.user = user;
        this.borrowedAt = borrowedAt;
        this.dueAt = dueAt;
        this.returnedAt = returnedAt;
    }
}
