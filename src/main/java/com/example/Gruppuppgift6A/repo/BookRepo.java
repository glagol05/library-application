package com.example.Gruppuppgift6A.repo;

import com.example.Gruppuppgift6A.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
    List<Book> findAllByNameContainingIgnoreCase(String name);
    List<Book> findAllByGenreContaining(Book.Genre genre);
    List<Book> findAllByAuthors_NameContainingIgnoreCase(String author);
}
