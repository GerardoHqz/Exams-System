package com.backend.exams.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "ROLEXUSER")
@Data
@NoArgsConstructor
public class RolexUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID RoleUserId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = true)
    private Users user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id",nullable = true)
    private Roles role;

    public RolexUser(Users user, Roles role) {
        super();
        this.user = user;
        this.role = role;
    }
}

