package com.backend.exams.repositories;

import com.backend.exams.model.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Roles, UUID> {
    Roles findByName(String name);
    boolean existsByName(String name);

}
