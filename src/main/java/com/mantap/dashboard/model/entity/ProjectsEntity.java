package com.mantap.dashboard.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "PROJECTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectsEntity {
    @Id
    @Column(name = "PROJECT_ID")
    private String projectId;

    @Column(name = "PROGRAM_ID")
    private String programId;

    @Column(name = "PROJECT_CODE")
    private String projectCode;

    @Column(name = "PROJECT_NAME")
    private String projectName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "START_DATE")
    private Timestamp startDate;

    @Column(name = "END_DATE")
    private Timestamp endDate;

    @Column(name = "PROGRESS")
    private Integer progress;

    @Column(name = "STATUS")
    private String status;
}
