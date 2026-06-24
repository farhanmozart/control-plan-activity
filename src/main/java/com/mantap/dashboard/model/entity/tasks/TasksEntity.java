package com.mantap.dashboard.model.entity.tasks;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "TASKS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TasksEntity {
    @Id
    @Column(name = "TASK_ID")
    private String taskId;

    @Column(name = "PROJECT_ID")
    private String projectId;

    @Column(name = "PARENT_TASK_ID")
    private String parentTaskId;

    @Column(name = "TASK_CODE")
    private String taskCode;

    @Column(name = "TASK_NAME")
    private String taskName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PROGRESS")
    private Integer progress;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PRIORITY")
    private String priority;

    @Column(name = "ASSIGNED_TO")
    private String assignedTo;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "DUE_DATE")
    private LocalDate dueDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_AT")
    private Timestamp createdAt;
}
