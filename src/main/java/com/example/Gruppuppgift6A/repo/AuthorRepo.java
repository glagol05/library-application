package com.example.Gruppuppgift6A.repo;

import com.example.Gruppuppgift6A.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorRepo extends JpaRepository<Author, UUID> {

}
