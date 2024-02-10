package com.backend.exams.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TOKEN")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_id")
    private UUID tokenId;

    @Column(name = "content")
    private String content;

    @Column(name = "timestamp", insertable = false, updatable = false)
    private Date timestamp;

    @Column(name = "active", insertable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users user;

    public Token(String content, Users user) {
        super();
        this.content = content;
        this.user = user;
    }
}
