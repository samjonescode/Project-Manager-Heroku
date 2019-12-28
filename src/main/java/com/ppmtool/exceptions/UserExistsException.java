package com.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserExistsException extends RuntimeException {

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public UserExistsException(String message) {
        super(message);
    }
}
