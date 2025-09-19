package com.raja.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ApiErrorResponse apiErrorResponse = buildApiErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleForbidden(AccessDeniedException ex, HttpServletRequest request) {
        ApiErrorResponse apiErrorResponse = buildApiErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN, request);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleOtherExceptions(Exception ex, HttpServletRequest request) {
        ApiErrorResponse apiErrorResponse = buildApiErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({BadCredentialsException.class, AuthenticationException.class})
    public ResponseEntity<ApiErrorResponse> handleUnAuthorizationException(Exception ex, HttpServletRequest request) {
        ApiErrorResponse apiErrorResponse = buildApiErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, request);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.UNAUTHORIZED);
    }

    private ApiErrorResponse buildApiErrorResponse(String message, HttpStatus status, HttpServletRequest request) {
        ApiErrorResponse response = new ApiErrorResponse();
        response.setMessage(message);
        response.setStatusCode(status.value());
        response.setTimestamp(System.currentTimeMillis());
        response.setPath(request.getRequestURI());
        return response;
    }

}
