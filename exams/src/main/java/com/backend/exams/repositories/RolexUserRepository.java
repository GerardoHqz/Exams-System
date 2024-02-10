package com.backend.exams.repositories;

import com.backend.exams.model.entities.RolexUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RolexUserRepository extends JpaRepository<RolexUser, UUID> {

}
