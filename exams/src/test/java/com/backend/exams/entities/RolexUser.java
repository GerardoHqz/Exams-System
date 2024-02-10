package com.backend.exams.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "ROLEXUSER")
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

    public RolexUser(UUID roleUserId, Users user, Roles role) {
        super();
        RoleUserId = roleUserId;
        this.user = user;
        this.role = role;
    }
}
