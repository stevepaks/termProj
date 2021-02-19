package com.craft.termproj.dto;

import com.craft.termproj.constant.LeaveStatus;
import com.craft.termproj.constant.LeaveType;
import com.craft.termproj.entity.Employee;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeaveStatisticDto {

    private String employeeId;

    private String employeeName;

    private Double entitlement;

    private Double usedDays;

    private Double remainDays;

    public static Function<Employee, LeaveStatisticDto> from(int year, List<LeaveDto> leaveDtos) {

        return employee -> {

            final double entitlement = LeaveType.ANNUAL.getEntitlement();

            final LeaveStatisticDto.LeaveStatisticDtoBuilder leaveStatisticDtoBuilder = LeaveStatisticDto.builder()
                    .employeeId(employee.getEmployeeId())
                    .employeeName(employee.getName())
                    .entitlement(entitlement)
                    .usedDays(0D)
                    .remainDays(entitlement);

            if (CollectionUtils.isEmpty(leaveDtos)) {

                return leaveStatisticDtoBuilder.build();
            } else {

                final double usedDays = leaveDtos.stream()
                        .map(LeaveDto.getUsedDays(year))
                        .mapToDouble(Double::valueOf)
                        .sum();

                return leaveStatisticDtoBuilder
                        .usedDays(usedDays)
                        .remainDays(BigDecimal.valueOf(entitlement).subtract(BigDecimal.valueOf(usedDays)).doubleValue())
                        .build();
            }
        };
    }

    public static Function<Employee, LeaveStatisticDto> from(int year, Map<String, List<LeaveDto>> leaveDtoMap) {

        return employee -> {

            final double entitlement = LeaveType.ANNUAL.getEntitlement();

            final LeaveStatisticDto.LeaveStatisticDtoBuilder leaveStatisticDtoBuilder = LeaveStatisticDto.builder()
                    .employeeId(employee.getEmployeeId())
                    .employeeName(employee.getName())
                    .entitlement(entitlement)
                    .usedDays(0D)
                    .remainDays(entitlement);

            final List<LeaveDto> leaveDtos = leaveDtoMap.get(employee.getEmployeeId());

            if (CollectionUtils.isEmpty(leaveDtos)) {

                return leaveStatisticDtoBuilder.build();
            } else {

                final double usedDays = leaveDtos.stream()
                        .filter(leaveDto -> leaveDto.getLeaveStatus() == LeaveStatus.APPROVED || leaveDto.getLeaveStatus() == LeaveStatus.PENDING)
                        .map(LeaveDto.getUsedDays(year))
                        .mapToDouble(Double::valueOf)
                        .sum();

                return leaveStatisticDtoBuilder
                        .usedDays(usedDays)
                        .remainDays(BigDecimal.valueOf(entitlement).subtract(BigDecimal.valueOf(usedDays)).doubleValue())
                        .build();
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaveStatisticDto that = (LeaveStatisticDto) o;
        return employeeId == that.employeeId &&
                Objects.equals(employeeName, that.employeeName) &&
                Objects.equals(entitlement, that.entitlement) &&
                Objects.equals(usedDays, that.usedDays) &&
                Objects.equals(remainDays, that.remainDays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, employeeName, entitlement, usedDays, remainDays);
    }

}
