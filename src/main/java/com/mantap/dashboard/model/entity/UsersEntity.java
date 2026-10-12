package com.mantap.dashboard.model.entity;

import com.mantap.dashboard.util.enumeration.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersEntity {
    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "NIP")
    private String nip;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "DIVISION_ID")
    private String divisionId;

    @Column(name = "DEPARTMENT_ID")
    private String departmentId;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Column(name = "TOKEN_VERSION")
    private Long tokenVersion;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private UserStatus status;

    @Column(name = "FAILED_LOGIN_ATTEMPTS")
    private Integer failedLoginAttempts;

    @Column(name = "LOCKED_UNTIL")
    private LocalDateTime lockedUntil;

    @Column(name = "LAST_LOGIN_AT")
    private LocalDateTime lastLoginAt;

}
