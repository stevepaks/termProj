package com.craft.termproj.bo;

import com.craft.termproj.dto.LeaveDto;
import com.craft.termproj.dto.LeaveStatisticDto;
import com.craft.termproj.entity.Employee;
import com.craft.termproj.exception.InvalidArgumentException;
import com.craft.termproj.exception.message.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.craft.termproj.constant.LeaveStatus.APPROVED;
import static com.craft.termproj.constant.LeaveStatus.PENDING;

@Service
public class LeaveStatisticBo {

    @Autowired
    private LeaveBo leaveBo;

    @Transactional(readOnly = true)
    public void checkIsVaildRequest(Employee employee, LeaveDto leaveDto) {

        final LocalDate startRequest = leaveDto.getStart();
        final LocalDate endRequest = leaveDto.getEnd();
        final int year = startRequest.getYear();

        if (leaveDto.getIsHalfDay() && (startRequest.compareTo(endRequest) != 0)) {

            throw new InvalidArgumentException(ErrorCode.INVALID_LEAVE_PERIOD);
        }

        final List<LeaveDto> leaveDtos = leaveBo.getLeaves(employee, year, Arrays.asList(PENDING, APPROVED));

        if (leaveDtos.stream().anyMatch(LeaveDto.isDuplicatedPeriod(startRequest, endRequest))) {

            throw new InvalidArgumentException(ErrorCode.DUPLICATED_PERIOD);
        }

        final LeaveStatisticDto leaveStatisticDto = LeaveStatisticDto.from(year, leaveDtos).apply(employee);

        final Double remainDays = leaveStatisticDto.getRemainDays();

        if (remainDays.compareTo(0D) <= 0) {

            throw new InvalidArgumentException(ErrorCode.INSUFFICIENT_REMAINING_LEAVE);
        }

        if (leaveDto.getIsHalfDay()) {

            if (BigDecimal.valueOf(remainDays).subtract(BigDecimal.valueOf(0.5D)).compareTo(BigDecimal.ZERO) < 0) {

                throw new InvalidArgumentException(ErrorCode.INSUFFICIENT_REMAINING_LEAVE);
            }
        } else {

            final double requestDays = ChronoUnit.DAYS.between(startRequest, endRequest) + 1D;

            if (BigDecimal.valueOf(remainDays).subtract(BigDecimal.valueOf(requestDays)).compareTo(BigDecimal.ZERO) < 0) {

                throw new InvalidArgumentException(ErrorCode.INSUFFICIENT_REMAINING_LEAVE);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<LeaveStatisticDto> getLeaveStatistics(List<Employee> employees, int year) {

        final Map<String, List<LeaveDto>> leaveDtoMap = leaveBo.getLeaves(year, employees, Arrays.asList()).stream()
                .collect(Collectors.groupingBy(LeaveDto::getEmployeeId));

        return employees.stream()
                .map(LeaveStatisticDto.from(year, leaveDtoMap))
                .collect(Collectors.toList());
    }

}
