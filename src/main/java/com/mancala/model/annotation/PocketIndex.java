package com.mancala.model.annotation;

import com.mancala.validation.PocketIndexValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PocketIndexValidator.class)
public @interface PocketIndex {
    String message() default "pocketIndex can be between 0 to 12 but not {notAllowedValue}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    byte notAllowedValue();
}
