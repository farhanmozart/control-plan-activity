package com.mantap.dashboard.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "ACTIVITY_LOGS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityLogsEntity {
    @Id
    @Column(name = "ACTIVITY_LOGS_ID")
    private String activityLogsId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "MODULE")
    private String module;

    @Column(name = "ACTION")
    private String action;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREATED_AT")
    private Timestamp createdAt;
}
