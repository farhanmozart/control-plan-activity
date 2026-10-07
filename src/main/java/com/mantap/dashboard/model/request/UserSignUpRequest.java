package com.mantap.dashboard.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpRequest {
    @NotBlank
    private String userId;
    @NotBlank
    private String nip;
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String departmentCode;
    @NotBlank
    private String role;
    @NotBlank
    private String position;
    @NotBlank
    private String divisionCode;
}