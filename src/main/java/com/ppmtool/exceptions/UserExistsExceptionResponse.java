package com.ppmtool.exceptions;

public class UserExistsExceptionResponse {
    private String response;

    public UserExistsExceptionResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
