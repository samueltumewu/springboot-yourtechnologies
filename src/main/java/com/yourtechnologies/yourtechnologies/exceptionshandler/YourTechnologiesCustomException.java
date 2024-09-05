package com.yourtechnologies.yourtechnologies.exceptionshandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class YourTechnologiesCustomException extends RuntimeException {

    @Getter
    private final Map<String, String> details;

    @Getter
    private final HttpStatus httpStatus;

    public YourTechnologiesCustomException(String message, Map<String, String> details, HttpStatus httpStatus) {
        super(message);
        this.details = details;
        this.httpStatus = httpStatus;
    }

}
