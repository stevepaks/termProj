package com.craft.termproj.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum LeaveStatus {

    PENDING, APPROVED, CANCELED;

    public static List<LeaveStatus> validChangingLeaveStatuses() {

        return Collections.unmodifiableList(Arrays.asList(APPROVED, CANCELED));
    }

}
