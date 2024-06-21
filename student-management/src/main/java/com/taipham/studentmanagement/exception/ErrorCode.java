package com.taipham.studentmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"UNCATEGORIZED EXCEPTION",HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(9998,"Invalid message key",HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(9997,"Unauthenticated",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(9996,"You do not have permission",HttpStatus.FORBIDDEN),
    USERNAME_SIZE_ERROR(9995,"Username must be between 5 and 12 characters in length",HttpStatus.BAD_REQUEST),
    PASSWORD_SIZE_ERROR(9994,"Password must be at least 8 characters",HttpStatus.BAD_REQUEST),
    PASSWORD_PATTERN_ERROR(9993,"The password must be at least one uppercase character, one lowercase character, one numeric character, and one special character.",HttpStatus.BAD_REQUEST),
    CONSTAIN_NAME_EXCEPTION(9992,"Only uppercase characters and \"_\" are acceptable.",HttpStatus.BAD_REQUEST),
    ID_DOES_NOT_EXIST(9991,"This id doesn't exist!",HttpStatus.NOT_FOUND),
    USER_NOT_EXIST(9990,"This user doesn't exist!",HttpStatus.NOT_FOUND);

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
