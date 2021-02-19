package com.craft.termproj.constant;

import lombok.Getter;

@Getter
public enum LeaveType {

    ANNUAL(15D);

    private final double entitlement;

    LeaveType(double entitlement) {
        this.entitlement = entitlement;
    }

}
