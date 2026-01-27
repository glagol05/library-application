package models;

import java.util.Objects;
import java.util.UUID;

public final class User {

    private final UUID id;
    private final String name;
    private final String email;

    public User(UUID id, String name, String email) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(email);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
