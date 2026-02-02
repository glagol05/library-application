package com.example.Gruppuppgift6A.service;

import com.example.Gruppuppgift6A.entity.Author;
import com.example.Gruppuppgift6A.entity.Book;
import com.example.Gruppuppgift6A.entity.User;
import com.example.Gruppuppgift6A.exceptions.BookNotAvailableException;
import com.example.Gruppuppgift6A.exceptions.BookNotFoundException;
import com.example.Gruppuppgift6A.repo.BookRepo;
import com.example.Gruppuppgift6A.repo.IUserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepo bookRepo;
    private final IUserRepo userRepo;

    public BookResponse getBook(Long id) {
        Book book = bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException("Incorrect book ID"));
        return new BookResponse(book.getId(), book.getName(), book.isAvailable(),
                book.getAuthors().stream().map(Author::getName).collect(Collectors.toSet()), book.getGenre());
    }

    @Transactional
    public BookResponse loanBook(Long id, String user) {
        User userClass = userRepo.findByUsername(user).orElseThrow(() -> new UsernameNotFoundException("Invalid username"));
        Book book = bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException("Incorrect book ID"));
        if (book.isAvailable()) {
            book.setAvailable(false);
            book.setBorrower(userClass);
            return new BookResponse(book.getId(), book.getName(), book.isAvailable(),
                    book.getAuthors().stream().map(Author::getName).collect(Collectors.toSet()), book.getGenre());
        } else throw new BookNotAvailableException("Book not available");
    }

    @Transactional
    public BookResponse returnBook(Long id) {
        Book book = bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException("Incorrect book ID"));
        book.setAvailable(true);
        book.setBorrower(null);
        return new BookResponse(book.getId(), book.getName(), book.isAvailable(),
                book.getAuthors().stream().map(Author::getName).collect(Collectors.toSet()), book.getGenre());

    }

    public List<BookResponse> searchBooks(String title, String author, Book.Genre genre) {
        Set<Book> fetchedBooks = new HashSet<>();
        if (title != null) fetchedBooks.addAll(bookRepo.findAllByNameContainingIgnoreCase(title));
        if (author != null) fetchedBooks.addAll(bookRepo.findAllByAuthors_NameContainingIgnoreCase(author));
        if (genre != null) fetchedBooks.addAll(bookRepo.findAllByGenreContaining(genre));
        return fetchedBooks.stream().map(x -> new BookResponse(
                x.getId(), x.getName(), x.isAvailable(), x.getAuthors().stream().map(Author::getName).collect(Collectors.toSet()),
                x.getGenre())).toList();
    }

    public List<BookResponse> allBooks() {
        return bookRepo.findAll().stream().map(x -> new BookResponse(
                x.getId(), x.getName(), x.isAvailable(), x.getAuthors().stream().map(Author::getName).collect(Collectors.toSet()),
                x.getGenre())).toList();
    }

    public record BookResponse(Long id, String name, Boolean available, Set<String> authorNames, Book.Genre genre) { }

}
