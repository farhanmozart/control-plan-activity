package com.mantap.dashboard.model.entity.tasks;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "TASK_COMMENTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskCommentsEntity {
    @Id
    @Column(name = "TASK_COMMENTS_ID")
    private String taskCommentsId;

    @Column(name = "TASK_ID")
    private String taskId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "CREATED_AT")
    private Timestamp createdAt;
}
