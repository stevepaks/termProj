package com.craft.termproj.bo;

import com.craft.termproj.constant.LeaveStatus;
import com.craft.termproj.constant.LeaveType;
import com.craft.termproj.dto.LeaveDto;
import com.craft.termproj.entity.Employee;
import com.craft.termproj.entity.Leave;
import com.craft.termproj.exception.InvalidArgumentException;
import com.craft.termproj.exception.message.ErrorCode;
import com.craft.termproj.repository.LeaveDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

;

@Service
public class LeaveBo {

    @Autowired
    private LeaveDao leaveDao;

    @Transactional(readOnly = true)
    public List<LeaveDto> getLeaves(int year, int month, List<Employee> employees, List<LeaveStatus> leaveStatuses) {

        final LocalDate from = LocalDate.of(year, month, 1);
        final LocalDate to = YearMonth.of(year, month).atEndOfMonth();
        final Map<String, Employee> employeeMap = employees.stream()
                .collect(Collectors.toMap(Employee::getEmployeeId, Function.identity()));

        if (CollectionUtils.isEmpty(leaveStatuses)) {

            return leaveDao.findByEndGreaterThanEqualAndStartLessThanEqual(from, to).stream()
                    .map(LeaveDto.from(employeeMap))
                    .collect(Collectors.toList());
        } else {

            return leaveDao.findByEndGreaterThanEqualAndStartLessThanEqualAndLeaveStatusIn(from, to, leaveStatuses).stream()
                    .map(LeaveDto.from(employeeMap))
                    .collect(Collectors.toList());
        }

    }

    @Transactional(readOnly = true)
    public List<LeaveDto> getLeaves(int year, List<Employee> employees, List<LeaveStatus> leaveStatuses) {

        final LocalDate from = LocalDate.of(year, 1, 1);
        final LocalDate to = YearMonth.of(year, 12).atEndOfMonth();
        final Map<String, Employee> employeeMap = employees.stream()
                .collect(Collectors.toMap(Employee::getEmployeeId, Function.identity()));

        if (CollectionUtils.isEmpty(leaveStatuses)) {

            return leaveDao.findByEndGreaterThanEqualAndStartLessThanEqual(from, to).stream()
                    .map(LeaveDto.from(employeeMap))
                    .collect(Collectors.toList());
        } else {

            return leaveDao.findByEndGreaterThanEqualAndStartLessThanEqualAndLeaveStatusIn(from, to, leaveStatuses).stream()
                    .map(LeaveDto.from(employeeMap))
                    .collect(Collectors.toList());
        }
    }

    @Transactional
    public LeaveDto addLeave(Employee employee, LeaveDto leaveDto, LeaveType leaveType, LocalDateTime createdAt) {

        final LocalDate from = leaveDto.getStart();
        final LocalDate to = leaveDto.getEnd();
        Leave.LeaveBuilder leaveBuilder = Leave.builder()
                .employeeId(employee.getEmployeeId())
                .start(from)
                .end(to)
                .leaveStatus(LeaveStatus.PENDING)
                .leaveType(leaveType)
                .createdAt(createdAt);

        if (leaveDto.getIsHalfDay()) {

            leaveBuilder.days(0.5D);
        } else {

            final long days = ChronoUnit.DAYS.between(from, to)+1;
            leaveBuilder.days(days);
        }

        final Leave leave = leaveBuilder.build();

        leaveDao.save(leave);

        return LeaveDto.from(employee).apply(leave);
    }

    @Transactional(readOnly = true)
    public Leave getLeave(long leaveId) {

        final Optional<Leave> leaveOptional = leaveDao.findById(leaveId);

        if (leaveOptional.isPresent()) {

            return leaveOptional.get();
        } else {

            throw new InvalidArgumentException(ErrorCode.NOT_FOUND_LEAVE);
        }
    }

    @Transactional
    public void putLeave(Leave leave, LeaveStatus leaveStatus) {

        leave.setLeaveStatus(leaveStatus);

        leaveDao.save(leave);
    }

    @Transactional(readOnly = true)
    public List<LeaveDto> getLeaves(Employee employee, int year, List<LeaveStatus> leaveStatuses) {

        final LocalDate from = LocalDate.of(year, 1, 1);
        final LocalDate to = YearMonth.of(year, 12).atEndOfMonth();
        final String employeeId = employee.getEmployeeId();

        if (CollectionUtils.isEmpty(leaveStatuses)) {

            return leaveDao.findByEmployeeIdAndEndGreaterThanEqualAndStartLessThanEqual(employeeId, from, to).stream()
                    .map(LeaveDto.from(employee))
                    .collect(Collectors.toList());
        } else {

            return leaveDao.findByEmployeeIdAndEndGreaterThanEqualAndStartLessThanEqualAndLeaveStatusIn(employeeId, from, to, leaveStatuses).stream()
                    .map(LeaveDto.from(employee))
                    .collect(Collectors.toList());
        }
    }

}
