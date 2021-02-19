package com.craft.termproj.bo;

import com.craft.termproj.constant.LeaveStatus;
import com.craft.termproj.constant.LeaveType;
import com.craft.termproj.dto.LeaveDto;
import com.craft.termproj.entity.Employee;
import com.craft.termproj.entity.Leave;
import com.craft.termproj.exception.InvalidArgumentException;
import com.craft.termproj.exception.message.ErrorCode;
import com.craft.termproj.repository.LeaveDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = LeaveBoTestContext.class)
public class LeaveBoTest {

    @Autowired
    private LeaveBo leaveBo;

    @MockBean
    private LeaveDao leaveDao;

    private Employee mockEmployee;

    @Before
    public void setUp() {
        final String employeeId = "K0001";
        this.mockEmployee = Employee.builder()
                .employeeId(employeeId)
                .name("idibros")
                .department("platform")
                .build();
    }

    @Test
    public void putLeave__cancel() {

        final long leaveId = 1L;
        final Leave leave = Leave.builder()
                .id(leaveId)
                .build();

        final LeaveStatus leaveStatus = LeaveStatus.CANCELED;
        doAnswer(invocationOnMock -> {
            Leave leaveArg = invocationOnMock.getArgument(0, Leave.class);
            leaveArg.setLeaveStatus(leaveStatus);

            return null;
        }).when(leaveDao).save(leave);

        leaveBo.putLeave(leave, leaveStatus);

        verify(leaveDao, times(1)).save(Leave.builder()
                .id(leaveId)
                .leaveStatus(leaveStatus)
                .build()
        );
    }

    @Test
    public void putLeave__approve() {

        final long leaveId = 1L;
        final Leave leave = Leave.builder()
                .id(leaveId)
                .build();

        final LeaveStatus leaveStatus = LeaveStatus.APPROVED;
        doAnswer(invocationOnMock -> {
            Leave leaveArg = invocationOnMock.getArgument(0, Leave.class);
            leaveArg.setLeaveStatus(leaveStatus);

            return null;
        }).when(leaveDao).save(leave);

        leaveBo.putLeave(leave, leaveStatus);

        verify(leaveDao, times(1)).save(Leave.builder()
                .id(leaveId)
                .leaveStatus(leaveStatus)
                .build()
        );
    }

    @Test
    public void getLeave__foundLeave() {

        final long leaveId = 1L;

        when(leaveDao.findById(anyLong())).thenReturn(Optional.ofNullable(Leave.builder()
                .id(leaveId)
                .employeeId(mockEmployee.getEmployeeId())
                .build()));

        final Leave actualLeave = leaveBo.getLeave(leaveId);

        assertThat(actualLeave, is(Leave.builder()
                .id(leaveId)
                .employeeId(mockEmployee.getEmployeeId())
                .build()));
    }

    @Test
    public void getLeave__notFoundLeave() {

        when(leaveDao.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        final InvalidArgumentException invalidArgumentException = assertThrows(InvalidArgumentException.class, () -> leaveBo.getLeave(1L));

        assertThat(invalidArgumentException.getErrorCode().getCode(), is(ErrorCode.NOT_FOUND_LEAVE.getCode()));
        assertThat(invalidArgumentException.getErrorCode().getMessage(), is(ErrorCode.NOT_FOUND_LEAVE.getMessage()));
    }

    @Test
    public void addLeave__request_twoDays() {

        final LocalDate from = LocalDate.of(2021, 2, 16);
        final LocalDate to = LocalDate.of(2021, 2, 17);
        final boolean isHalfDay = false;
        final LocalDateTime createdAt = LocalDateTime.of(2021, 2, 16, 10, 55, 00);

        doAnswer(invocationOnMock -> {

            Leave leave = invocationOnMock.getArgument(0, Leave.class);
            leave.setId(1L);

            return null;
        }).when(leaveDao).save(any(Leave.class));

        final LeaveDto leaveDto = LeaveDto.builder()
                .start(from)
                .end(to)
                .isHalfDay(isHalfDay)
                .build();

        final LeaveDto actualLeaveDto = leaveBo.addLeave(mockEmployee, leaveDto, LeaveType.ANNUAL, createdAt);

        final LeaveDto expectedLeave = LeaveDto.builder()
                .employeeId(mockEmployee.getEmployeeId())
                .employeeName(mockEmployee.getName())
                .employeeDepartment(mockEmployee.getDepartment())
                .start(from)
                .end(to)
                .leaveType(LeaveType.ANNUAL)
                .days(2D)
                .leaveStatus(LeaveStatus.PENDING)
                .createdAt(createdAt)
                .isHalfDay(false)
                .build();

        assertThat(actualLeaveDto, is(expectedLeave));
    }

    @Test
    public void addLeave__request_halfDay() {

        final LocalDate from = LocalDate.of(2021, 2, 16);
        final LocalDate to = LocalDate.of(2021, 2, 16);
        final boolean isHalfDay = true;
        final LocalDateTime createdAt = LocalDateTime.of(2021, 2, 16, 10, 55, 00);

        doAnswer(invocationOnMock -> {

            final Leave leave = invocationOnMock.getArgument(0);
            leave.setId(1L);

            return null;
        }).when(leaveDao).save(any(Leave.class));

        final LeaveDto leaveDto = LeaveDto.builder()
                .start(from)
                .end(to)
                .isHalfDay(isHalfDay)
                .build();

        final LeaveDto actualLeaveDto = leaveBo.addLeave(mockEmployee, leaveDto, LeaveType.ANNUAL, createdAt);

        final LeaveDto expectedLeave = LeaveDto.builder()
                .employeeId(mockEmployee.getEmployeeId())
                .employeeName(mockEmployee.getName())
                .employeeDepartment(mockEmployee.getDepartment())
                .start(from)
                .end(to)
                .leaveType(LeaveType.ANNUAL)
                .days(0.5D)
                .leaveStatus(LeaveStatus.PENDING)
                .createdAt(createdAt)
                .isHalfDay(true)
                .build();

        assertThat(actualLeaveDto, is(expectedLeave));
    }

    @Test
    public void addLeave__request_oneDay() {

        final LocalDate from = LocalDate.of(2021, 2, 16);
        final LocalDate to = LocalDate.of(2021, 2, 16);
        final boolean isHalfDay = false;
        final LocalDateTime createdAt = LocalDateTime.of(2021, 2, 16, 10, 55, 00);

        doAnswer(invocationOnMock -> {

            final Leave leave = invocationOnMock.getArgument(0);
            leave.setId(1L);

            return null;
        }).when(leaveDao).save(any(Leave.class));

        final LeaveDto leaveDto = LeaveDto.builder()
                .start(from)
                .end(to)
                .isHalfDay(isHalfDay)
                .build();

        final LeaveDto actualLeaveDto = leaveBo.addLeave(mockEmployee, leaveDto, LeaveType.ANNUAL, createdAt);

        final LeaveDto expectedLeave = LeaveDto.builder()
                .employeeId(mockEmployee.getEmployeeId())
                .employeeName(mockEmployee.getName())
                .employeeDepartment(mockEmployee.getDepartment())
                .start(from)
                .end(to)
                .leaveType(LeaveType.ANNUAL)
                .days(1D)
                .leaveStatus(LeaveStatus.PENDING)
                .createdAt(createdAt)
                .isHalfDay(false)
                .build();

        assertThat(actualLeaveDto, is(expectedLeave));
    }

    @Test
    public void getLeavesByMonth__leaves_havingTwo__statuses_notEmpty() {

        final String employeeId = mockEmployee.getEmployeeId();
        final Leave leave1 = Leave.builder()
                .employeeId(employeeId)
                .start(LocalDate.of(2021, 1, 3))
                .end(LocalDate.of(2021, 1, 3))
                .build();
        final Leave leave2 = Leave.builder()
                .employeeId(employeeId)
                .start(LocalDate.of(2021, 1, 4))
                .end(LocalDate.of(2021, 1, 4))
                .build();
        final List<Leave> leaves = Arrays.asList(leave1, leave2);
        when(leaveDao.findByEndGreaterThanEqualAndStartLessThanEqualAndLeaveStatusIn(any(LocalDate.class), any(LocalDate.class), anyList()))
                .thenReturn(leaves);

        final List<LeaveDto> actualLeaveDtos = leaveBo.getLeaves(2021, 1, Arrays.asList(mockEmployee), Arrays.asList(LeaveStatus.APPROVED));

        assertThat(actualLeaveDtos.size(), is(2));

        verify(leaveDao, times(1)).findByEndGreaterThanEqualAndStartLessThanEqualAndLeaveStatusIn(any(LocalDate.class), any(LocalDate.class), anyList());
    }

    @Test
    public void getLeavesByMonth__leaves_havingOne__statuses_notEmpty() {

        final Leave leave1 = Leave.builder()
                .employeeId(mockEmployee.getEmployeeId())
                .start(LocalDate.of(2021, 1, 3))
                .end(LocalDate.of(2021, 1, 3))
                .build();
        final List<Leave> leaves = Arrays.asList(leave1);
        when(leaveDao.findByEndGreaterThanEqualAndStartLessThanEqualAndLeaveStatusIn(any(LocalDate.class), any(LocalDate.class), anyList()))
                .thenReturn(leaves);

        final List<LeaveDto> actualLeaveDtos = leaveBo.getLeaves(2021, 1, Arrays.asList(mockEmployee), Arrays.asList(LeaveStatus.APPROVED));

        assertThat(actualLeaveDtos.size(), is(1));

        verify(leaveDao, times(1)).findByEndGreaterThanEqualAndStartLessThanEqualAndLeaveStatusIn(any(LocalDate.class), any(LocalDate.class), anyList());
    }

    @Test
    public void getLeavesByMonth__leaves_empty__statuses_notEmpty() {

        when(leaveDao.findByEndGreaterThanEqualAndStartLessThanEqualAndLeaveStatusIn(any(LocalDate.class), any(LocalDate.class), anyList()))
                .thenReturn(Arrays.asList());

        final List<LeaveDto> actualLeaveDtos = leaveBo.getLeaves(2021, 1, Arrays.asList(mockEmployee), Arrays.asList(LeaveStatus.APPROVED));

        assertThat(actualLeaveDtos.size(), is(0));

        verify(leaveDao, times(1)).findByEndGreaterThanEqualAndStartLessThanEqualAndLeaveStatusIn(any(LocalDate.class), any(LocalDate.class), anyList());
    }

    @Test
    public void getLeavesByMonth__leaves_havingTwo__statuses_empty() {

        final String employeeId = mockEmployee.getEmployeeId();
        final Leave leave1 = Leave.builder()
                .employeeId(employeeId)
                .start(LocalDate.of(2021, 1, 3))
                .end(LocalDate.of(2021, 1, 3))
                .build();
        final Leave leave2 = Leave.builder()
                .employeeId(employeeId)
                .start(LocalDate.of(2021, 1, 4))
                .end(LocalDate.of(2021, 1, 4))
                .build();
        final List<Leave> leaves = Arrays.asList(leave1, leave2);
        when(leaveDao.findByEndGreaterThanEqualAndStartLessThanEqual(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(leaves);

        final List<LeaveDto> actualLeaveDtos = leaveBo.getLeaves(2021, 1, Arrays.asList(mockEmployee), Arrays.asList());

        assertThat(actualLeaveDtos.size(), is(2));

        verify(leaveDao, times(1)).findByEndGreaterThanEqualAndStartLessThanEqual(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    public void getLeavesByMonth__leaves_havingOne__statuses_empty() {

        final Leave leave1 = Leave.builder()
                .employeeId(mockEmployee.getEmployeeId())
                .start(LocalDate.of(2021, 1, 3))
                .end(LocalDate.of(2021, 1, 3))
                .build();
        final List<Leave> leaves = Arrays.asList(leave1);
        when(leaveDao.findByEndGreaterThanEqualAndStartLessThanEqual(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(leaves);

        final List<LeaveDto> actualLeaveDtos = leaveBo.getLeaves(2021, 1, Arrays.asList(mockEmployee), Arrays.asList());

        assertThat(actualLeaveDtos.size(), is(1));

        verify(leaveDao, times(1)).findByEndGreaterThanEqualAndStartLessThanEqual(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    public void getLeavesByMonth__leaves_empty__statuses_empty() {

        when(leaveDao.findByEndGreaterThanEqualAndStartLessThanEqual(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Arrays.asList());

        final List<LeaveDto> actualLeaveDtos = leaveBo.getLeaves(2021, 1, Arrays.asList(mockEmployee), Arrays.asList());

        assertThat(actualLeaveDtos.size(), is(0));

        verify(leaveDao, times(1)).findByEndGreaterThanEqualAndStartLessThanEqual(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    public void getLeavesByStatuses__statuses_notEmpty() {

        leaveBo.getLeaves(2021, Arrays.asList(mockEmployee), Arrays.asList(LeaveStatus.APPROVED));

        verify(leaveDao, times(1)).findByEndGreaterThanEqualAndStartLessThanEqualAndLeaveStatusIn(any(LocalDate.class), any(LocalDate.class), anyList());
    }

    @Test
    public void getLeavesByStatuses__statuses_empty() {

        leaveBo.getLeaves(2021, Arrays.asList(mockEmployee), Arrays.asList());

        verify(leaveDao, times(1)).findByEndGreaterThanEqualAndStartLessThanEqual(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    public void getLeavesByEmployeeAndYearAndStatuses__statuses_notEmpty() {

        leaveBo.getLeaves(mockEmployee, 2021, Arrays.asList(LeaveStatus.APPROVED));

        verify(leaveDao, times(1)).findByEmployeeIdAndEndGreaterThanEqualAndStartLessThanEqualAndLeaveStatusIn(anyString(), any(LocalDate.class), any(LocalDate.class), anyList());
    }

    @Test
    public void getLeavesByEmployeeAndYearAndStatuses__statuses_empty() {

        leaveBo.getLeaves(mockEmployee, 2021, Arrays.asList());

        verify(leaveDao, times(1)).findByEmployeeIdAndEndGreaterThanEqualAndStartLessThanEqual(anyString(), any(LocalDate.class), any(LocalDate.class));
    }

}