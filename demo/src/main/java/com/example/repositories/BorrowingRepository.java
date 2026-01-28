package com.example.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.Borrowing;

public interface BorrowingRepository extends JpaRepository<Borrowing, UUID> {
    List<Borrowing> findByUser_IdAndReturnedAtIsNull(UUID userId);
    List<Borrowing> findByBook_IdAndReturnedAtIsNull(UUID bookId);
}