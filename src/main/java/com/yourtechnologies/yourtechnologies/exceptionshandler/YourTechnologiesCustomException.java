package com.yourtechnologies.yourtechnologies.exceptionshandler;

import java.util.Map;

public class YourTechnologiesCustomException extends RuntimeException {

    private final Map<String, String> details;

    public YourTechnologiesCustomException(String message, Map<String, String> details) {
        super(message);
        this.details = details;
    }

    public Map<String, String> getDetails() {
        return details;
    }
}
