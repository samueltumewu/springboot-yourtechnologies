package com.yourtechnologies.yourtechnologies.annotations;

import com.yourtechnologies.yourtechnologies.repository.FormRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueSlugValidator implements ConstraintValidator<UniqueSlug, String> {
    @Autowired
    FormRepository formRepository;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !formRepository.existsBySlug(value);
    }
}
