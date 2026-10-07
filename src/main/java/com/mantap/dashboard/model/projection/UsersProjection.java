package com.mantap.dashboard.model.projection;

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
}