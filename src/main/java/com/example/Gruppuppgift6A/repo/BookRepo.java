package com.example.Gruppuppgift6A.repo;

import com.example.Gruppuppgift6A.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
    List<Book> findAllByNameContainingIgnoreCase(String name);
    List<Book> findAllByGenre(Book.Genre genre);
    List<Book> findAllByAuthors_NameContainingIgnoreCase(String author);
    List<Book> findByNameContainingIgnoreCaseAndAuthorsNameContainingIgnoreCase(String name, String authorName);
    List<Book> findByNameContainingIgnoreCaseAndGenre(String name, Book.Genre genre);
    List<Book> findByAuthorsNameContainingIgnoreCaseAndGenre(String author, Book.Genre genre);
    List<Book> findByNameContainingIgnoreCaseAndAuthorsNameContainingIgnoreCaseAndGenre(
            String name, String authorName, Book.Genre genre
    );
}
