package com.backend.exams.repositories;

import com.backend.exams.model.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {
    Users findByUsername(String username);
    Boolean existsByUsername(String username);
}
