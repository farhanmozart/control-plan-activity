package com.mantap.dashboard.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "TIMESHEET")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimesheetEntity {
    @Id
    @Column(name = "TIMESHEET_ID")
    private String timesheetId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "TASK_ID")
    private String taskId;

    @Column(name = "WORK_DATE")
    private LocalDate workDate;

    @Column(name = "HOURS_SPENT")
    private BigDecimal hoursSpent;

    @Column(name = "ACTIVITY_DESC")
    private String activityDesc;
}
