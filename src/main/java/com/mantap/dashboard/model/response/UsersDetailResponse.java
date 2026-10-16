package com.mantap.dashboard.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersDetailResponse {
    private String userId;
    private String nip;
    private String name;
    private String email;
    private String role;
    private String position;
    private String divisionId;
    private String division;
    private String departmentId;
    private String department;
    private Boolean isActive;
    private String status;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}