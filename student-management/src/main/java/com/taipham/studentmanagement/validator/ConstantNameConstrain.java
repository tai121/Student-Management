package com.taipham.studentmanagement.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ConstantNameValidator.class)
public @interface ConstantNameConstrain {
    String message() default "Invalid Role";

    Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
