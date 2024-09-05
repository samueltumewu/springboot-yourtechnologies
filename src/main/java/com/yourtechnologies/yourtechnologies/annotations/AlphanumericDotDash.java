package com.yourtechnologies.yourtechnologies.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AlphanumericDotDashValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AlphanumericDotDash {
    String message() default "Only alphanumeric characters, dashes, and dots are allowed, with no spaces.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
