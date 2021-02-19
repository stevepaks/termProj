package com.craft.termproj.repository;

import com.craft.termproj.constant.LeaveStatus;
import com.craft.termproj.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveDao extends JpaRepository<Leave, Long> {

    List<Leave> findByEndGreaterThanEqualAndStartLessThanEqualAndLeaveStatusIn(LocalDate from, LocalDate to, List<LeaveStatus> leaveStatuses);

    List<Leave> findByEndGreaterThanEqualAndStartLessThanEqual(LocalDate from, LocalDate to);

    List<Leave> findByEmployeeIdAndEndGreaterThanEqualAndStartLessThanEqual(String employeeId, LocalDate from, LocalDate to);

    List<Leave> findByEmployeeIdAndEndGreaterThanEqualAndStartLessThanEqualAndLeaveStatusIn(String employeeId, LocalDate from, LocalDate to, List<LeaveStatus> leaveStatuses);

}
