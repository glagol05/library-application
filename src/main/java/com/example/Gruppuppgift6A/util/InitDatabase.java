package com.example.Gruppuppgift6A.util;

import com.example.Gruppuppgift6A.entity.Author;
import com.example.Gruppuppgift6A.entity.Book;
import com.example.Gruppuppgift6A.repo.AuthorRepo;
import com.example.Gruppuppgift6A.repo.BookRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.*;

@Configuration
public class InitDatabase {

    @Bean
    CommandLineRunner init(BookRepo bookRepo, AuthorRepo authorRepo) {
        return args -> {
            // 1. Check if database is empty
            if (bookRepo.count() > 0) {
                System.out.println("Database already contains data. Skipping seed.");
                return;
            }

            System.out.println("Seeding database with 50 books and authors...");

            // 2. Create some reusable authors
            List<String> authorNames = List.of(
                    "H.P. Lovecraft", "Isaac Asimov", "Jane Austen", "J.R.R. Tolkien", "Stephen King",
                    "Arthur C. Clarke", "Mary Shelley", "George Orwell", "Frank Herbert", "Ursula K. Le Guin"
            );

            List<Author> authors = new ArrayList<>();
            for (String name : authorNames) {
                Author author = new Author();
                author.setName(name);
                authors.add(authorRepo.save(author)); // Save authors first to generate IDs
            }

            // 3. Create 50 Books
            Random random = new Random();
            Book.Genre[] genres = Book.Genre.values();

            for (int i = 1; i <= 50; i++) {
                Book book = new Book();
                book.setName("Great Work #" + i);
                book.setGenre(genres[random.nextInt(genres.length)]);
                book.setAvailable(true);

                // Assign 1 to 2 random authors to each book
                int authorCount = random.nextInt(2) + 1;
                for (int j = 0; j < authorCount; j++) {
                    Author randomAuthor = authors.get(random.nextInt(authors.size()));
                    book.getAuthors().add(randomAuthor);
                }

                bookRepo.save(book);
            }

            System.out.println("Seeding complete! 50 books added.");
        };
    }
}