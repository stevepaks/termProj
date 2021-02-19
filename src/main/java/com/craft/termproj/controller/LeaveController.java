package com.craft.termproj.controller;

import com.craft.termproj.bo.EmployeeBo;
import com.craft.termproj.bo.LeaveBo;
import com.craft.termproj.bo.LeaveStatisticBo;
import com.craft.termproj.constant.LeaveStatus;
import com.craft.termproj.constant.LeaveType;
import com.craft.termproj.dto.LeaveDto;
import com.craft.termproj.entity.Employee;
import com.craft.termproj.entity.Leave;
import com.craft.termproj.exception.InvalidArgumentException;
import com.craft.termproj.exception.message.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    @Autowired
    private LeaveBo leaveBo;

    @Autowired
    private LeaveStatisticBo leaveStatisticBo;

    @Autowired
    private EmployeeBo employeeBo;

    @GetMapping("{year}/{month}")
    public ResponseEntity<List<LeaveDto>> getLeavesByMonth(
            @PathVariable("year") Integer year,
            @PathVariable("month") Integer month) {

        final List<Employee> employees = employeeBo.getEmployees();

        final List<LeaveDto> leavesByMonth = leaveBo.getLeaves(year, month, employees, Arrays.asList());

        return new ResponseEntity<>(leavesByMonth, HttpStatus.OK);
    }

    @GetMapping("{year}")
    public ResponseEntity<List<LeaveDto>> getLeavesByYear(
            @PathVariable("year") Integer year,
            @RequestParam(value = "leaveStatuses", required = false) List<LeaveStatus> leaveStatuses) {

        final List<Employee> employees = employeeBo.getEmployees();

        final List<LeaveDto> leavesByYear = leaveBo.getLeaves(year, employees, leaveStatuses);

        return new ResponseEntity<>(leavesByYear, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity addLeave(@Valid @RequestBody LeaveDto leaveDto) {

        final Employee employee = employeeBo.getEmployee(leaveDto.getEmployeeId());

        leaveStatisticBo.checkIsVaildRequest(employee, leaveDto);

        leaveBo.addLeave(employee, leaveDto, LeaveType.ANNUAL, LocalDateTime.now());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{leaveId}")
    public ResponseEntity putLeave(
            @PathVariable("leaveId") Long leaveId,
            @RequestParam("leaveStatus") LeaveStatus leaveStatus) {

        if (LeaveStatus.validChangingLeaveStatuses().stream().anyMatch(vaildLeaveStatus -> vaildLeaveStatus == leaveStatus)) {

            final Leave leave = leaveBo.getLeave(leaveId);

            if (leaveStatus == LeaveStatus.APPROVED) {

                final Employee employee = employeeBo.getEmployee(leave.getEmployeeId());

                leaveStatisticBo.checkIsVaildRequest(employee, LeaveDto.from(employee).apply(leave));
            }

            leaveBo.putLeave(leave, leaveStatus);

            return new ResponseEntity<>(HttpStatus.OK);
        } else {

            throw new InvalidArgumentException(ErrorCode.INVALID_LEAVE_STATUS);
        }
    }

}
