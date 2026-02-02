package com.example.Gruppuppgift6A.repo;

import com.example.Gruppuppgift6A.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserRepo extends JpaRepository<User, UUID> {
    boolean existsByUsername(String username);
    User findByUsername(String username);
}
