package com.example.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {
}
