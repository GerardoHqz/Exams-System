package com.backend.exams.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
public class Users implements UserDetails {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID user_id;
    @Column(name="username")
    private String username;
    @Column(name="password")
    @JsonIgnore
    private String password;
    @Column(name="name")
    private String name;
    @Column(name="lastname")
    private String lastname;
    @Column(name="email")
    private String email;
    @Column(name="telephone")
    private String telephone;
    @Column(name="enabled")
    private boolean enabled;
    @Column(name="profile")
    private String profile;

    //Cascade = cuando se elimina un usuario se eliminan todos los roles asociados a ese usuario
    //Fetch = EAGER = cuando se recupera un usuario se recupera tambien los roles asociados a ese usuario
    //MappedBy = "user" = se mapea con el atributo user de la clase RoleUser
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private Set<RolexUser> rolesUser = new HashSet<>();

    public Users(String username, String password, String name, String lastname, String email,
                 String telephone, String profile) {
        super();
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.telephone = telephone;
        this.enabled = true;
        this.profile = profile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}

