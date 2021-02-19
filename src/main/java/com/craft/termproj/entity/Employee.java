package com.craft.termproj.entity;

import com.craft.termproj.entity.id.EmployeeId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
@IdClass(EmployeeId.class)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Id
    @Column(name = "employee_id")
    private String employeeId;

    private String name;

    private String department;

    private String role;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                employeeId == employee.employeeId &&
                Objects.equals(name, employee.name) &&
                Objects.equals(department, employee.department) &&
                Objects.equals(role, employee.role) &&
                Objects.equals(joinDate, employee.joinDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeId, name, department, role, joinDate);
    }

}
