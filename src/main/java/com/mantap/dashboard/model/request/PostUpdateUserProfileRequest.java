package com.mantap.dashboard.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostUpdateUserProfileRequest {
    @Size(max = 32)
    private String name;
    @Email
    @Size(max = 50)
    private String email;
    @Size(max = 100)
    private String position;
}