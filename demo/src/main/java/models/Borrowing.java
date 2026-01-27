package models;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public final class Borrowing {

    private final UUID id;
    private final UUID bookId;
    private final UUID userId;
    private final LocalDateTime borrowedAt;
    private final LocalDateTime dueAt;
    private final LocalDateTime returnedAt;

    public Borrowing(
            UUID id,
            UUID bookId,
            UUID userId,
            LocalDateTime borrowedAt,
            LocalDateTime dueAt,
            LocalDateTime returnedAt
    ) {
        this.id = Objects.requireNonNull(id);
        this.bookId = Objects.requireNonNull(bookId);
        this.userId = Objects.requireNonNull(userId);
        this.borrowedAt = Objects.requireNonNull(borrowedAt);
        this.dueAt = dueAt;
        this.returnedAt = returnedAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getBookId() {
        return bookId;
    }

    public UUID getUserId() {
        return userId;
    }

    public LocalDateTime getBorrowedAt() {
        return borrowedAt;
    }

    public LocalDateTime getDueAt() {
        return dueAt;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public boolean isReturned() {
        return returnedAt != null;
    }
}
