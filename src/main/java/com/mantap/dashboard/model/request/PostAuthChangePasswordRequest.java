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
public class PostAuthChangePasswordRequest {
    @NotBlank
    private String oldPassword;
    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,128}$",
            message = "Password must contain at least 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character")
    private String newPassword;
    @NotBlank
    private String confirmPassword;
}