package models;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class Book {

    private final UUID id;
    private final String name;
    private final String author;
    private final LocalDate publishDate;
    private final boolean available;
    private final List<String> genres;
    private final List<String> tags;

    public Book(
            UUID id,
            String name,
            String author,
            LocalDate publishDate,
            boolean available,
            List<String> genres,
            List<String> tags
    ) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.author = Objects.requireNonNull(author);
        this.publishDate = Objects.requireNonNull(publishDate);
        this.available = available;
        this.genres = List.copyOf(genres);
        this.tags = List.copyOf(tags);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public boolean isAvailable() {
        return available;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<String> getTags() {
        return tags;
    }
}
