package com.craft.termproj.exception.message;

import lombok.Getter;

@Getter
public enum ErrorCode {

    NOT_FOUND_LEAVE(1, "not found leave."),
    INSUFFICIENT_REMAINING_LEAVE(2, "insufficient remaining leaves."),
    INVALID_LEAVE_STATUS(3, "invalid leave status."),
    DUPLICATED_PERIOD(4, "already registered period."),
    INVALID_LEAVE_PERIOD(5, "invalid leave period.");

    private final int code;

    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

}
