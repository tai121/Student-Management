package com.taipham.studentmanagement.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.taipham.studentmanagement.dto.response.ApiResponse;


@ControllerAdvice
public class GlobalExceptionHandler {

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception){
        // log.error(exception.getMessage(), exception);
        ApiResponse apiResponse = ApiResponse.builder()
                                            .code(ErrorCode.UNAUTHORIZED.getCode())
                                            .message(ErrorCode.UNAUTHORIZED.getMessage())
                                            .build();
        return ResponseEntity.status(ErrorCode.UNAUTHORIZED.getStatusCode())
                            .body(apiResponse);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse> handlingAppException(AppException exception){
        ApiResponse apiResponse = ApiResponse.builder()
                                            .code(exception.getErrorCode().getCode())
                                            .message(exception.getErrorCode().getMessage())
                                            .build(); 
        return ResponseEntity.status(exception.getErrorCode().getStatusCode())
                            .body(apiResponse);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value =  MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception){
        @SuppressWarnings("null")
        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try {
            errorCode =ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException e) {
            
        }
        ApiResponse apiResponse = ApiResponse.builder()
                                            .code(errorCode.getCode())
                                            .message(errorCode.getMessage())
                                            .build(); 
        return ResponseEntity.status(errorCode.getStatusCode())
                            .body(apiResponse);
    }

    

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse> handlingRuntimeException(Exception exception){
        ApiResponse apiResponse = ApiResponse.builder()
                                            .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                                            .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                                            .build(); 
        return ResponseEntity.status(ErrorCode.UNCATEGORIZED_EXCEPTION.getStatusCode()).body(apiResponse);
    }
}
