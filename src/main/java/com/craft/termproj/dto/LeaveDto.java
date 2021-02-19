package com.craft.termproj.dto;

import com.craft.termproj.constant.LeaveStatus;
import com.craft.termproj.constant.LeaveType;
import com.craft.termproj.entity.Employee;
import com.craft.termproj.entity.Leave;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeaveDto {

    private Long leaveId;

    @NotNull
    private String employeeId;

    private String employeeName;

    private String employeeDepartment;

    @NotNull
    private LocalDate start;

    @NotNull
    private LocalDate end;

    private Double days;

    private LeaveType leaveType;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private LeaveStatus leaveStatus;

    @NotNull
    private Boolean isHalfDay;

    public static Function<Leave, LeaveDto> from(Map<String, Employee> employeeMap) {

        return leave -> {

            final Employee employee = employeeMap.get(leave.getEmployeeId());

            return LeaveDto.builder()
                    .leaveId(leave.getId())
                    .employeeId(employee.getEmployeeId())
                    .employeeName(employee.getName())
                    .employeeDepartment(employee.getDepartment())
                    .leaveType(leave.getLeaveType())
                    .start(leave.getStart())
                    .end(leave.getEnd())
                    .days(leave.getDays())
                    .createdAt(leave.getCreatedAt())
                    .leaveStatus(leave.getLeaveStatus())
                    .isHalfDay(leave.getDays() == 0.5D)
                    .build();
        };
    }

    public static Function<Leave, LeaveDto> from(Employee employee) {

        return leave -> LeaveDto.builder()
                .leaveId(leave.getId())
                .employeeId(employee.getEmployeeId())
                .employeeName(employee.getName())
                .employeeDepartment(employee.getDepartment())
                .start(leave.getStart())
                .end(leave.getEnd())
                .leaveType(leave.getLeaveType())
                .days(leave.getDays())
                .createdAt(leave.getCreatedAt())
                .leaveStatus(leave.getLeaveStatus())
                .isHalfDay(leave.getDays() == 0.5D)
                .build();
    }

    public static Function<LeaveDto, Double> getUsedDays(int year) {

        return leaveDto -> {

            if (leaveDto.getStart().getYear() != year) {

                return ChronoUnit.DAYS.between(LocalDate.of(year, 1, 1), leaveDto.getEnd()) + 1D;
            } else if (leaveDto.getEnd().getYear() != year) {

                return ChronoUnit.DAYS.between(leaveDto.getStart(), LocalDate.of(year, 12, 31)) + 1D;
            } else {

                return leaveDto.getDays();
            }
        };
    }

    public static Predicate<LeaveDto> isDuplicatedPeriod(LocalDate startRequest, LocalDate endRequest) {

        return currentLeaveDto -> {

            final LocalDate currentStart = currentLeaveDto.getStart();
            final LocalDate currentEnd = currentLeaveDto.getEnd();

            return (currentStart.compareTo(startRequest) <= 0 && startRequest.compareTo(currentEnd) <= 0)
                    || (currentStart.compareTo(endRequest) <= 0 && endRequest.compareTo(currentEnd) <= 0)
                    || (startRequest.compareTo(currentStart) <= 0 && currentStart.compareTo(endRequest) <= 0)
                    || (startRequest.compareTo(currentEnd) <= 0 && currentEnd.compareTo(endRequest) <= 0);
        };
    }

    public static Predicate<LeaveDto> isAnotherLeave(LeaveDto leaveDto) {

        return currLeaveDto -> {

            if (Objects.nonNull(leaveDto.getLeaveId())) {

                return currLeaveDto.getLeaveId().compareTo(leaveDto.getLeaveId()) != 0;
            } else {

                return true;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaveDto leaveDto = (LeaveDto) o;
        return Objects.equals(employeeId, leaveDto.employeeId) &&
                Objects.equals(employeeName, leaveDto.employeeName) &&
                Objects.equals(employeeDepartment, leaveDto.employeeDepartment) &&
                Objects.equals(start, leaveDto.start) &&
                Objects.equals(end, leaveDto.end) &&
                Objects.equals(days, leaveDto.days) &&
                leaveType == leaveDto.leaveType &&
                Objects.equals(createdAt, leaveDto.createdAt) &&
                leaveStatus == leaveDto.leaveStatus &&
                Objects.equals(isHalfDay, leaveDto.isHalfDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, employeeName, employeeDepartment, start, end, days, leaveType, createdAt, leaveStatus, isHalfDay);
    }

}
