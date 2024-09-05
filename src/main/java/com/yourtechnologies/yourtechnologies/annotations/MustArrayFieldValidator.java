package com.yourtechnologies.yourtechnologies.annotations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class MustArrayFieldValidator implements ConstraintValidator<MustArrayField, List<String>> {

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
