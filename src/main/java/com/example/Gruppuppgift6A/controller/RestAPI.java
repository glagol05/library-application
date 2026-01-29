package com.example.Gruppuppgift6A.controller;

import com.example.Gruppuppgift6A.entity.Book;
import com.example.Gruppuppgift6A.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/service")
@AllArgsConstructor
public class RestAPI {

    private final BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<BookService.BookResponse> getBook (@PathVariable Long id) {
        BookService.BookResponse book = bookService.getBook(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping("/{id}/loan")
    public ResponseEntity<BookService.BookResponse> loanBook(@PathVariable Long id, @RequestParam String user) {
        BookService.BookResponse loaned = bookService.loanBook(id, user);

        return ResponseEntity.ok(loaned);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<BookService.BookResponse> returnBook(@PathVariable Long id) {
        BookService.BookResponse returned = bookService.returnBook(id);

        return ResponseEntity.ok(returned);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookService.BookResponse>> search(@RequestParam(required = false) String title,
                                                                 @RequestParam(required = false) String author,
                                                                 @RequestParam(required = false)Book.Genre genre) {
        List<BookService.BookResponse> books = bookService.searchBooks(title, author, genre);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/root")
    public ResponseEntity<List<BookService.BookResponse>> root(){
        return ResponseEntity.ok(bookService.allBooks());
    }

}
