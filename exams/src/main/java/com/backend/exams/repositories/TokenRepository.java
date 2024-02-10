package com.backend.exams.repositories;

import com.backend.exams.model.entities.Token;
import com.backend.exams.model.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
    List<Token> findByUserAndActive(Users user, boolean enabled);
}
