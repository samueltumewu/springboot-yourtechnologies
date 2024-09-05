package com.yourtechnologies.yourtechnologies.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MustArrayFieldValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MustArrayField {

    String message() default "Field must be an array";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
