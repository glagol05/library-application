package com.example.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.Book;

public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByTagsContainingIgnoreCase(String tag);
}
