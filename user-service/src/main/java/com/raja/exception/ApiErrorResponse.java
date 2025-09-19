package com.raja.exception;

import lombok.Data;

@Data
public class ApiErrorResponse {

    private String message;
    private int statusCode;
    private long timestamp;
    private String path;
}
