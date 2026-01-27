package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import models.Book;
import models.User;

public class PostgresLibraryRepository implements ILibraryRepository {

    private final DataSource dataSource;

    public PostgresLibraryRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Book findById(UUID bookId) throws Exception {
        String sql = """
            SELECT id, name, author, publish_date, is_available, genres, tags
            FROM books
            WHERE id = ?
        """;

        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, bookId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        }
    }

    @Override
    public List<Book> findAll() throws Exception {
        String sql = "SELECT * FROM books";
        List<Book> results = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                results.add(mapRow(rs));
            }
        }
        return results;
    }

    @Override
    public List<Book> findAllByTag(String tag) throws Exception {
        String sql = "SELECT * FROM books WHERE tags ILIKE ?";
        List<Book> results = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + tag + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRow(rs));
                }
            }
        }
        return results;
    }

    @Override
    public void borrowBook(UUID bookId, UUID userId) throws Exception {
        String insertBorrowing = """
            INSERT INTO borrowings (id, book_id, user_id)
            VALUES (?, ?, ?)
        """;

        String updateBook = """
            UPDATE books
            SET is_available = false
            WHERE id = ? AND is_available = true
        """;

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(insertBorrowing);
                PreparedStatement ps2 = conn.prepareStatement(updateBook)) {

                ps1.setObject(1, UUID.randomUUID());
                ps1.setObject(2, bookId);
                ps1.setObject(3, userId);
                ps1.executeUpdate();

                if (ps2.executeUpdate() == 0) {
                    throw new IllegalStateException("Book already borrowed");
                }

                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        }
    }

    @Override
    public void returnBook(Book book, User user) throws Exception {
        String updateBorrowing = """
            UPDATE borrowings
            SET returned_at = now()
            WHERE book_id = ? AND user_id = ? AND returned_at IS NULL
        """;

        String updateBook = """
            UPDATE books
            SET is_available = true
            WHERE id = ?
        """;

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(updateBorrowing);
                PreparedStatement ps2 = conn.prepareStatement(updateBook)) {

                ps1.setObject(1, book.getId());
                ps1.setObject(2, user.getId());
                ps1.executeUpdate();

                ps2.setObject(1, book.getId());
                ps2.executeUpdate();

                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        }
    }

    private Book mapRow(ResultSet rs) throws Exception {
        return new Book(
            rs.getObject("id", UUID.class),
            rs.getString("name"),
            rs.getString("author"),
            rs.getDate("publish_date").toLocalDate(),
            rs.getBoolean("is_available"),
            List.of(rs.getString("genres").split(",")),
            List.of(rs.getString("tags").split(","))
        );
    }
}