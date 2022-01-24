package com.codeMaster.codeMasterAPI.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Email(message = "Email Should be Valid.")
    private String email;

    @NotBlank
    private String fullName;

    @NotBlank
    @Size(min = 8,message = "Password Should have at least 8 characters.")
    private String password;
}
