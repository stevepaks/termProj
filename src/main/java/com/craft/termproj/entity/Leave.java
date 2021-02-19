package com.craft.termproj.entity;

import com.craft.termproj.constant.LeaveStatus;
import com.craft.termproj.constant.LeaveType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "leave")
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "employee_id")
    private String employeeId;

    private LocalDate start;

    private LocalDate end;

    private double days;

    @Enumerated(EnumType.STRING)
    @Column(name = "leave_type")
    private LeaveType leaveType;

    @Enumerated(EnumType.STRING)
    @Column(name = "leave_status")
    private LeaveStatus leaveStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Leave leave = (Leave) o;
        return id == leave.id &&
                employeeId == leave.employeeId &&
                Double.compare(leave.days, days) == 0 &&
                Objects.equals(start, leave.start) &&
                Objects.equals(end, leave.end) &&
                leaveType == leave.leaveType &&
                leaveStatus == leave.leaveStatus &&
                Objects.equals(createdAt, leave.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeId, start, end, days, leaveType, leaveStatus, createdAt);
    }

}
