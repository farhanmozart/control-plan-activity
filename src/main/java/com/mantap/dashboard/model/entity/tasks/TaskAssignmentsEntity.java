package com.mantap.dashboard.model.entity.tasks;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TASK_ASSIGNMENTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskAssignmentsEntity {
    @Id
    @Column(name = "TASK_ASSIGNMENTS_ID")
    private String taskAssignmentsId;

    @Column(name = "TASK_ID")
    private String taskId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ROLE_IN_TASK")
    private String roleInTask;
}