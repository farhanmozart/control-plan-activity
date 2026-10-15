package com.mantap.dashboard.model.projection;

import java.time.LocalDateTime;

public interface UsersProjection {
    String getUserId();
    String getNip();
    String getName();
    String getDivision();
    String getDepartment();
    String getRole();
    Integer getIsActive();
    String getPassword();
    Long getTokenVersion();
    String getEmail();
    String getPosition();
    String getStatus();
    LocalDateTime getLastLoginAt();
}