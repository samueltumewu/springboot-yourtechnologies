package com.yourtechnologies.yourtechnologies.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AlphanumericDotDashValidator implements ConstraintValidator<AlphanumericDotDash, String> {
    private static final String VALID_PATTERN = "^[a-zA-Z0-9.-]+$";
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() < 0) {
            return true;
        }
        return value.matches(VALID_PATTERN) && !value.contains(" ");
    }
}
