package repositories;

import java.util.List;
import java.util.UUID;
import models.Book;
import models.User;

public interface ILibraryRepository {

    Book findById(UUID bookId) throws Exception;

    List<Book> findAll() throws Exception;

    List<Book> findAllByTag(String tag) throws Exception;

    void borrowBook(UUID bookId, UUID userId) throws Exception;

    void returnBook(Book book, User user) throws Exception;
    
}
