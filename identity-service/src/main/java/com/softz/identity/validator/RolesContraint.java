package com.softz.identity.validator;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {RolesValidator.class})
public @interface RolesContraint {
    String message() default "Invalid Roles";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
