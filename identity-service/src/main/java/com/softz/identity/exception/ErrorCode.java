package com.softz.identity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", 
            HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_INPUT(9000, "Invalid input",
            HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(404100, "User not found", HttpStatus.NOT_FOUND),
    USER_ID_NOT_FOUND(404102, "User id %s not found", HttpStatus.NOT_FOUND),
    USER_EXISTED(409100, "User existed", HttpStatus.CONFLICT),
    MISSING_MESSAGE_KEY(100100, "Invalid message", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(100101, "Username's length must between {min} and {max}", HttpStatus.BAD_REQUEST),

    INVALID_DATE_OF_BIRTH(100102, "User's age must be equal or greater than {min}", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
