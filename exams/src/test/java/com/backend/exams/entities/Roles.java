package com.backend.exams.entities;

import jakarta.persistence.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "ROLES")
@Data
@NoArgsConstructor
public class Roles {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID role_id;

    @Column(name="name")
    private String name;

    //Cascade = cuando se elimina un rol se eliminan ese rol a todos los usuarios que lo tengan
    //Fetch = LAZY = cuando se recupera un rol no se recupera los usuarios asociados a ese rol
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    @JsonIgnore
    private Set<RolexUser> rolesUser = new HashSet<>();

    public Roles(UUID role_id, String name) {
        super();
        this.role_id = role_id;
        this.name = name;
    }

}
