package com.craft.termproj.repository;

import com.craft.termproj.entity.Employee;
import com.craft.termproj.entity.id.EmployeeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, EmployeeId> {

    Employee findByEmployeeId(String employeeId);

}
