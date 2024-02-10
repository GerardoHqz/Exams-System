package com.backend.exams.model.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesxUserDTO {

    @NotNull(message = "User id cannot be null")
    private String user_id;

    @NotNull(message = "Role id cannot be null")
    private String role_id;
}
