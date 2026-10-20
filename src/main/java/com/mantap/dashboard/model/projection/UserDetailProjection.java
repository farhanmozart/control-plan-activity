package com.mantap.dashboard.model.projection;

import java.time.LocalDateTime;

public interface UserDetailProjection {
    String getUserId();
    String getNip();
    String getName();
    String getEmail();
    String getRole();
    String getPosition();
    String getDivisionId();
    String getDivision();
    String getDepartmentId();
    String getDepartment();
    Integer getIsActive();
    String getStatus();
    LocalDateTime getLastLoginAt();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
}