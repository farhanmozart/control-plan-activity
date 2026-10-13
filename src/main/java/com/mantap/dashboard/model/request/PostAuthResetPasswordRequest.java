package com.mantap.dashboard.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostAuthResetPasswordRequest {
    @NotBlank
    private String resetToken;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d\\s]).{8,128}$",
            message = "Password must be 12-128 characters and contain uppercase, lowercase, number, and special character")
    private String newPassword;
    @NotBlank
    private String confirmPassword;
}