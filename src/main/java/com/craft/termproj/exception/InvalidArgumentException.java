package com.craft.termproj.exception;

import com.craft.termproj.exception.message.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidArgumentException extends RuntimeException {

    private ErrorCode errorCode;

    public InvalidArgumentException(ErrorCode errorCode) {

        this.errorCode = errorCode;
    }

}
