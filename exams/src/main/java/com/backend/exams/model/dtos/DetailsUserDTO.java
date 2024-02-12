package com.backend.exams.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailsUserDTO {
    private String username;
    private String name;
    private String lastname;
    private String email;
    private String telephone;
    private String enable;
    private String profile;
    private Set<String> rolesUser = new HashSet<>();
}
