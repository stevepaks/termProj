package com.craft.termproj.bo;

import com.craft.termproj.entity.Employee;
import com.craft.termproj.repository.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeBo {

    @Autowired
    private EmployeeDao employeeDao;

    @Transactional(readOnly = true)
    public Employee getEmployee(String employeeId) {

        return employeeDao.findByEmployeeId(employeeId);
    }

    @Transactional(readOnly = true)
    public List<Employee> getEmployees() {

        return employeeDao.findAll();
    }

}
