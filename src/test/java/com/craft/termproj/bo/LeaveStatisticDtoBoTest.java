package com.craft.termproj.bo;

import com.craft.termproj.constant.LeaveStatus;
import com.craft.termproj.dto.LeaveDto;
import com.craft.termproj.dto.LeaveStatisticDto;
import com.craft.termproj.entity.Employee;
import com.craft.termproj.exception.InvalidArgumentException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.craft.termproj.constant.LeaveType.ANNUAL;
import static com.craft.termproj.exception.message.ErrorCode.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = LeaveStatisticBoTestContext.class)
public class LeaveStatisticDtoBoTest {

    @Autowired
    private LeaveStatisticBo leaveStatisticBo;

    @MockBean
    private LeaveBo leaveBo;

    private Employee mockEmployee;

    @Before
    public void setUp() {

        final String employeeId = "K0001";

        mockEmployee = Employee.builder()
                .employeeId(employeeId)
                .name("idibros")
                .department("platform")
                .build();
    }

    @Test
    public void checkIsValidRequest__duplicatedPeriod_type6() {

        final LeaveDto leaveDto1 = LeaveDto.builder()
                .start(LocalDate.of(2021, 1, 15))
                .end(LocalDate.of(2021, 1, 18))
                .build();
        final List<LeaveDto> leaveDtos = Arrays.asList(leaveDto1);
        when(leaveBo.getLeaves(any(Employee.class), anyInt(), anyList())).thenReturn(leaveDtos);

        final LocalDate from = LocalDate.of(2021, 1, 16);
        final LocalDate to = LocalDate.of(2021, 1, 17);
        final LeaveDto leaveDto = LeaveDto.builder()
                .start(from)
                .end(to)
                .isHalfDay(false)
                .build();

        final InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> leaveStatisticBo.checkIsVaildRequest(mockEmployee, leaveDto));
        assertThat(exception.getErrorCode().getMessage(), is(DUPLICATED_PERIOD.getMessage()));
    }

    @Test
    public void checkIsValidRequest__duplicatedPeriod_type5() {

        final LeaveDto leaveDto1 = LeaveDto.builder()
                .start(LocalDate.of(2021, 1, 15))
                .end(LocalDate.of(2021, 1, 18))
                .build();
        final List<LeaveDto> leaveDtos = Arrays.asList(leaveDto1);
        when(leaveBo.getLeaves(any(Employee.class), anyInt(), anyList())).thenReturn(leaveDtos);

        final LocalDate from = LocalDate.of(2021, 1, 18);
        final LocalDate to = LocalDate.of(2021, 1, 19);
        final LeaveDto leaveDto = LeaveDto.builder()
                .start(from)
                .end(to)
                .isHalfDay(false)
                .build();

        final InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> leaveStatisticBo.checkIsVaildRequest(mockEmployee, leaveDto));
        assertThat(exception.getErrorCode().getMessage(), is(DUPLICATED_PERIOD.getMessage()));
    }

    @Test
    public void checkIsValidRequest__duplicatedPeriod_type4() {

        final LeaveDto leaveDto1 = LeaveDto.builder()
                .start(LocalDate.of(2021, 1, 15))
                .end(LocalDate.of(2021, 1, 18))
                .build();
        final List<LeaveDto> leaveDtos = Arrays.asList(leaveDto1);
        when(leaveBo.getLeaves(any(Employee.class), anyInt(), anyList())).thenReturn(leaveDtos);

        final LocalDate from = LocalDate.of(2021, 1, 14);
        final LocalDate to = LocalDate.of(2021, 1, 15);
        final LeaveDto leaveDto = LeaveDto.builder()
                .start(from)
                .end(to)
                .isHalfDay(false)
                .build();

        final InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> leaveStatisticBo.checkIsVaildRequest(mockEmployee, leaveDto));
        assertThat(exception.getErrorCode().getMessage(), is(DUPLICATED_PERIOD.getMessage()));
    }

    @Test
    public void checkIsValidRequest__duplicatedPeriod_type3() {

        final LeaveDto leaveDto1 = LeaveDto.builder()
                .start(LocalDate.of(2021, 1, 15))
                .end(LocalDate.of(2021, 1, 15))
                .build();
        final List<LeaveDto> leaveDtos = Arrays.asList(leaveDto1);
        when(leaveBo.getLeaves(any(Employee.class), anyInt(), anyList())).thenReturn(leaveDtos);

        final LocalDate from = LocalDate.of(2021, 1, 14);
        final LocalDate to = LocalDate.of(2021, 1, 16);
        final LeaveDto leaveDto = LeaveDto.builder()
                .start(from)
                .end(to)
                .isHalfDay(false)
                .build();

        final InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> leaveStatisticBo.checkIsVaildRequest(mockEmployee, leaveDto));
        assertThat(exception.getErrorCode().getMessage(), is(DUPLICATED_PERIOD.getMessage()));
    }

    @Test
    public void checkIsValidRequest__duplicatedPeriod_type2() {

        final LeaveDto leaveDto1 = LeaveDto.builder()
                .start(LocalDate.of(2021, 1, 15))
                .end(LocalDate.of(2021, 1, 15))
                .build();
        final List<LeaveDto> leaveDtos = Arrays.asList(leaveDto1);
        when(leaveBo.getLeaves(any(Employee.class), anyInt(), anyList())).thenReturn(leaveDtos);

        final LocalDate from = LocalDate.of(2021, 1, 14);
        final LocalDate to = LocalDate.of(2021, 1, 15);
        final LeaveDto leaveDto = LeaveDto.builder()
                .start(from)
                .end(to)
                .isHalfDay(false)
                .build();

        final InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> leaveStatisticBo.checkIsVaildRequest(mockEmployee, leaveDto));
        assertThat(exception.getErrorCode().getMessage(), is(DUPLICATED_PERIOD.getMessage()));
    }

    @Test
    public void checkIsValidRequest__duplicatedPeriod_type1() {

        final LeaveDto leaveDto1 = LeaveDto.builder()
                .start(LocalDate.of(2021, 1, 15))
                .end(LocalDate.of(2021, 1, 15))
                .build();
        final List<LeaveDto> leaveDtos = Arrays.asList(leaveDto1);
        when(leaveBo.getLeaves(any(Employee.class), anyInt(), anyList())).thenReturn(leaveDtos);

        final LocalDate from = LocalDate.of(2021, 1, 15);
        final LocalDate to = LocalDate.of(2021, 1, 16);
        final LeaveDto leaveDto = LeaveDto.builder()
                .start(from)
                .end(to)
                .isHalfDay(false)
                .build();

        final InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> leaveStatisticBo.checkIsVaildRequest(mockEmployee, leaveDto));
        assertThat(exception.getErrorCode().getMessage(), is(DUPLICATED_PERIOD.getMessage()));
    }

    @Test
    public void checkIsVaildRequest__invalidPeriod() {

        final LocalDate from = LocalDate.of(2021, 1, 15);
        final LocalDate to = LocalDate.of(2021, 1, 16);
        final LeaveDto leaveDto = LeaveDto.builder()
                .start(from)
                .end(to)
                .isHalfDay(true)
                .build();

        final InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> leaveStatisticBo.checkIsVaildRequest(mockEmployee, leaveDto));
        assertThat(exception.getErrorCode().getMessage(), is(INVALID_LEAVE_PERIOD.getMessage()));
    }

    @Test
    public void checkIsVaildRequest__insufficient_afterConsume_isNotHalf() {


        final String employeeId = mockEmployee.getEmployeeId();
        final LeaveDto leaveDto1 = LeaveDto.builder()
                .employeeId(employeeId)
                .start(LocalDate.of(2021, 1, 1))
                .end(LocalDate.of(2021, 1, 14))
                .days(14.5D)
                .leaveStatus(LeaveStatus.APPROVED)
                .build();
        final List<LeaveDto> leaves = Arrays.asList(leaveDto1);
        when(leaveBo.getLeaves(any(Employee.class), anyInt(), anyList())).thenReturn(leaves);

        final LocalDate from = LocalDate.of(2021, 1, 15);
        final LocalDate to = LocalDate.of(2021, 1, 15);
        final LeaveDto leaveDto = LeaveDto.builder()
                .start(from)
                .end(to)
                .isHalfDay(false)
                .build();

        final InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> leaveStatisticBo.checkIsVaildRequest(mockEmployee, leaveDto));
        assertThat(exception.getErrorCode().getMessage(), is(INSUFFICIENT_REMAINING_LEAVE.getMessage()));
    }

    @Test
    public void checkIsVaildRequest__insufficientCurrent() {

        final String employeeId = mockEmployee.getEmployeeId();
        final LeaveDto leave1 = LeaveDto.builder()
                .employeeId(employeeId)
                .start(LocalDate.of(2021, 1, 1))
                .end(LocalDate.of(2021, 1, 14))
                .days(15D)
                .leaveStatus(LeaveStatus.APPROVED)
                .build();
        final List<LeaveDto> leaveDtos = Arrays.asList(leave1);
        when(leaveBo.getLeaves(any(Employee.class), anyInt(), anyList())).thenReturn(leaveDtos);

        final LocalDate from = LocalDate.of(2021, 1, 15);
        final LocalDate to = LocalDate.of(2021, 1, 15);
        final LeaveDto leaveDto = LeaveDto.builder()
                .start(from)
                .end(to)
                .isHalfDay(false)
                .build();

        final InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> leaveStatisticBo.checkIsVaildRequest(mockEmployee, leaveDto));
        assertThat(exception.getErrorCode().getMessage(), is(INSUFFICIENT_REMAINING_LEAVE.getMessage()));
    }

    @Test
    public void checkIsVaildRequest__sufficientCurrent() {

        final List<LeaveDto> leaves = Arrays.asList();
        when(leaveBo.getLeaves(any(Employee.class), anyInt(), anyList())).thenReturn(leaves);

        final LocalDate from = LocalDate.of(2021, 1, 3);
        final LocalDate to = LocalDate.of(2021, 1, 3);
        final LeaveDto leaveDto = LeaveDto.builder()
                .start(from)
                .end(to)
                .isHalfDay(false)
                .build();

        leaveStatisticBo.checkIsVaildRequest(mockEmployee, leaveDto);
    }

    @Test
    public void getLeaveStatistics__leaves_havingOne_multiDay_approvedStatus_duringTwoYears2021_2022() {

        final String employeeId1 = mockEmployee.getEmployeeId();
        final LeaveDto leave1 = LeaveDto.builder()
                .employeeId(employeeId1)
                .start(LocalDate.of(2021, 12, 28))
                .end(LocalDate.of(2022, 1, 2))
                .days(5D)
                .leaveStatus(LeaveStatus.APPROVED)
                .build();
        final List<LeaveDto> leaves = Arrays.asList(leave1);
        when(leaveBo.getLeaves(anyInt(), anyList(), anyList())).thenReturn(leaves);

        final List<Employee> employees = Arrays.asList(mockEmployee);
        final List<LeaveStatisticDto> actualLeaveStatisticDtos = leaveStatisticBo.getLeaveStatistics(employees, 2021);

        assertThat(actualLeaveStatisticDtos.size(), is(1));

        final double entitlement = ANNUAL.getEntitlement();
        assertThat(actualLeaveStatisticDtos.get(0).getEntitlement(), is(entitlement));
        assertThat(actualLeaveStatisticDtos.get(0).getUsedDays(), is(4D));
        assertThat(actualLeaveStatisticDtos.get(0).getRemainDays(), is(BigDecimal.valueOf(entitlement).subtract(BigDecimal.valueOf(4D)).doubleValue()));
    }

    @Test
    public void getLeaveStatistics__leaves_havingOne_multiDay_approvedStatus_duringTwoYears2020_2021() {

        final String employeeId1 = mockEmployee.getEmployeeId();
        final LeaveDto leave1 = LeaveDto.builder()
                .employeeId(employeeId1)
                .start(LocalDate.of(2020, 12, 28))
                .end(LocalDate.of(2021, 1, 2))
                .days(5D)
                .leaveStatus(LeaveStatus.APPROVED)
                .build();
        final List<LeaveDto> leaves = Arrays.asList(leave1);
        when(leaveBo.getLeaves(anyInt(), anyList(), anyList())).thenReturn(leaves);

        final List<Employee> employees = Arrays.asList(mockEmployee);
        final List<LeaveStatisticDto> actualLeaveStatisticDtos = leaveStatisticBo.getLeaveStatistics(employees, 2021);

        assertThat(actualLeaveStatisticDtos.size(), is(1));

        final double entitlement = ANNUAL.getEntitlement();
        assertThat(actualLeaveStatisticDtos.get(0).getEntitlement(), is(entitlement));
        assertThat(actualLeaveStatisticDtos.get(0).getUsedDays(), is(2D));
        assertThat(actualLeaveStatisticDtos.get(0).getRemainDays(), is(BigDecimal.valueOf(entitlement).subtract(BigDecimal.valueOf(2D)).doubleValue()));
    }

    @Test
    public void getLeaveStatistics__leaves_havingTwo_multiDay_approvedStatus() {

        final String employeeId1 = mockEmployee.getEmployeeId();
        final LeaveDto leave1 = LeaveDto.builder()
                .employeeId(employeeId1)
                .start(LocalDate.of(2021, 1, 4))
                .end(LocalDate.of(2021, 1, 6))
                .days(3D)
                .leaveStatus(LeaveStatus.APPROVED)
                .build();
        final LeaveDto leave2 = LeaveDto.builder()
                .employeeId(employeeId1)
                .start(LocalDate.of(2021, 1, 7))
                .end(LocalDate.of(2021, 1, 8))
                .days(2D)
                .leaveStatus(LeaveStatus.APPROVED)
                .build();
        final List<LeaveDto> leaves = Arrays.asList(leave1, leave2);
        when(leaveBo.getLeaves(anyInt(), anyList(), anyList())).thenReturn(leaves);

        final List<Employee> employees = Arrays.asList(mockEmployee);
        final List<LeaveStatisticDto> actualLeaveStatisticDtos = leaveStatisticBo.getLeaveStatistics(employees, 2021);

        assertThat(actualLeaveStatisticDtos.size(), is(1));

        final double entitlement = ANNUAL.getEntitlement();
        assertThat(actualLeaveStatisticDtos.get(0).getEntitlement(), is(entitlement));
        assertThat(actualLeaveStatisticDtos.get(0).getUsedDays(), is(5D));
        assertThat(actualLeaveStatisticDtos.get(0).getRemainDays(), is(BigDecimal.valueOf(entitlement).subtract(BigDecimal.valueOf(5D)).doubleValue()));
    }

    @Test
    public void getLeaveStatistics__leaves_havingOne_multiDay_approvedStatus() {

        final String employeeId1 = mockEmployee.getEmployeeId();
        final LeaveDto leave1 = LeaveDto.builder()
                .employeeId(employeeId1)
                .start(LocalDate.of(2021, 1, 4))
                .end(LocalDate.of(2021, 1, 6))
                .days(3D)
                .leaveStatus(LeaveStatus.APPROVED)
                .build();
        final List<LeaveDto> leaves = Arrays.asList(leave1);
        when(leaveBo.getLeaves(anyInt(), anyList(), anyList())).thenReturn(leaves);

        final List<Employee> employees = Arrays.asList(mockEmployee);
        final List<LeaveStatisticDto> actualLeaveStatisticDtos = leaveStatisticBo.getLeaveStatistics(employees, 2021);

        assertThat(actualLeaveStatisticDtos.size(), is(1));

        final double entitlement = ANNUAL.getEntitlement();
        assertThat(actualLeaveStatisticDtos.get(0).getEntitlement(), is(entitlement));
        assertThat(actualLeaveStatisticDtos.get(0).getUsedDays(), is(3D));
        assertThat(actualLeaveStatisticDtos.get(0).getRemainDays(), is(BigDecimal.valueOf(entitlement).subtract(BigDecimal.valueOf(3D)).doubleValue()));
    }

    @Test
    public void getLeaveStatistics__leaves_havingOne_halfDay_approvedStatus() {

        final String employeeId1 = mockEmployee.getEmployeeId();
        final LeaveDto leaveDto1 = LeaveDto.builder()
                .employeeId(employeeId1)
                .start(LocalDate.of(2021, 1, 4))
                .end(LocalDate.of(2021, 1, 4))
                .days(0.5D)
                .leaveStatus(LeaveStatus.APPROVED)
                .build();
        final List<LeaveDto> leaves = Arrays.asList(leaveDto1);
        when(leaveBo.getLeaves(anyInt(), anyList(), anyList())).thenReturn(leaves);

        final List<Employee> employees = Arrays.asList(mockEmployee);
        final List<LeaveStatisticDto> actualLeaveStatisticDtos = leaveStatisticBo.getLeaveStatistics(employees, 2021);

        assertThat(actualLeaveStatisticDtos.size(), is(1));

        final double entitlement = ANNUAL.getEntitlement();
        assertThat(actualLeaveStatisticDtos.get(0).getEntitlement(), is(entitlement));
        assertThat(actualLeaveStatisticDtos.get(0).getUsedDays(), is(0.5D));
        assertThat(actualLeaveStatisticDtos.get(0).getRemainDays(), is(BigDecimal.valueOf(entitlement).subtract(BigDecimal.valueOf(0.5D)).doubleValue()));
    }

    @Test
    public void getLeaveStatistics__leaves_havingOne_oneDay_approvedStatus() {

        final String employeeId1 = mockEmployee.getEmployeeId();
        final LeaveDto leave1 = LeaveDto.builder()
                .employeeId(employeeId1)
                .start(LocalDate.of(2021, 1, 4))
                .end(LocalDate.of(2021, 1, 4))
                .days(1D)
                .leaveStatus(LeaveStatus.APPROVED)
                .build();
        final List<LeaveDto> leaves = Arrays.asList(leave1);
        when(leaveBo.getLeaves(anyInt(), anyList(), anyList())).thenReturn(leaves);

        final List<Employee> employees = Arrays.asList(mockEmployee);
        final List<LeaveStatisticDto> actualLeaveStatisticDtos = leaveStatisticBo.getLeaveStatistics(employees, 2021);

        assertThat(actualLeaveStatisticDtos.size(), is(1));

        final double entitlement = ANNUAL.getEntitlement();
        assertThat(actualLeaveStatisticDtos.get(0).getEntitlement(), is(entitlement));
        assertThat(actualLeaveStatisticDtos.get(0).getUsedDays(), is(1D));
        assertThat(actualLeaveStatisticDtos.get(0).getRemainDays(), is(BigDecimal.valueOf(entitlement).subtract(BigDecimal.valueOf(1D)).doubleValue()));
    }

    @Test
    public void getLeaveStatistics__leaves_empty() {

        final List<LeaveDto> leaves = Arrays.asList();
        when(leaveBo.getLeaves(anyInt(), anyList(), anyList())).thenReturn(leaves);

        final List<Employee> employees = Arrays.asList(mockEmployee);
        final List<LeaveStatisticDto> actualLeaveStatisticDtos = leaveStatisticBo.getLeaveStatistics(employees, 2021);

        assertThat(actualLeaveStatisticDtos.size(), is(1));
        assertThat(actualLeaveStatisticDtos, is(Arrays.asList(LeaveStatisticDto.builder()
                .employeeId("K0001")
                .employeeName("idibros")
                .entitlement(15D)
                .usedDays(0D)
                .remainDays(15D)
                .build()))
        );
    }

}