package com.backend.exams.model.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotEmpty(message = "Username can't be empty")
    private String username;

    @NotEmpty(message = "Old password can't be empty")
    @Size(min = 6, message = "Password must have a minimum of 6 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).*$",
            message = "Password must contain at least one uppercase letter, one lowercase letter")
    private String password;
}
