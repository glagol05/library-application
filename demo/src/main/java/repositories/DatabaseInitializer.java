package repositories;

import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

public class DatabaseInitializer {

    private final DataSource dataSource;

    public DatabaseInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void initSchema() throws Exception {
        try (Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id UUID PRIMARY KEY,
                    name TEXT NOT NULL,
                    email TEXT NOT NULL UNIQUE
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS books (
                    id UUID PRIMARY KEY,
                    name TEXT NOT NULL,
                    publish_date DATE NOT NULL,
                    is_available BOOLEAN NOT NULL DEFAULT true,
                    genres TEXT NOT NULL,
                    tags TEXT NOT NULL
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS borrowings (
                    id UUID PRIMARY KEY,
                    book_id UUID NOT NULL,
                    user_id UUID NOT NULL,
                    borrowed_at TIMESTAMP NOT NULL DEFAULT now(),
                    due_at TIMESTAMP,
                    returned_at TIMESTAMP,

                    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                )
            """);

            stmt.execute("""
                CREATE UNIQUE INDEX IF NOT EXISTS one_active_borrowing_per_book
                ON borrowings(book_id)
                WHERE returned_at IS NULL
            """);
        }
    }
}
