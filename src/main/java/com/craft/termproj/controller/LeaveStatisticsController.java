package com.craft.termproj.controller;

import com.craft.termproj.bo.EmployeeBo;
import com.craft.termproj.bo.LeaveStatisticBo;
import com.craft.termproj.dto.LeaveStatisticDto;
import com.craft.termproj.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/leaveStatistics")
public class LeaveStatisticsController {

    @Autowired
    private LeaveStatisticBo leaveStatisticBo;

    @Autowired
    private EmployeeBo employeeBo;

    @RequestMapping("{year}")
    public ResponseEntity<List<LeaveStatisticDto>> getLeaveStatistics(@PathVariable("year") Integer year) {

        List<Employee> employees = employeeBo.getEmployees();

        final List<LeaveStatisticDto> leaveStatisticDtos = leaveStatisticBo.getLeaveStatistics(employees, year);

        return new ResponseEntity<>(leaveStatisticDtos, HttpStatus.OK);
    }

}
