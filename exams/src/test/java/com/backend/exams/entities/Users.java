package com.backend.exams.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "USERS")
public class Users {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID user_id;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="name")
    private String name;
    private String lastname;
    @Column(name="email")
    private String email;
    @Column(name="telephone")
    private String telephone;
    @Column(name="enabled")
    private boolean enabled = true;
    @Column(name="profile")
    private String profile;

    //Cascade = cuando se elimina un usuario se eliminan todos los roles asociados a ese usuario
    //Fetch = EAGER = cuando se recupera un usuario se recupera tambien los roles asociados a ese usuario
    //MappedBy = "user" = se mapea con el atributo user de la clase RoleUser
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private Set<RolexUser> rolesUser = new HashSet<>();

    public Users(UUID user_id, String username, String password, String name, String lastname, String email,
                 String telephone, boolean enabled, String profile) {
        super();
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.telephone = telephone;
        this.enabled = enabled;
        this.profile = profile;
    }
}

