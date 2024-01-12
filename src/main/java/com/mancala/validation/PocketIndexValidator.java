package com.mancala.validation;

/*
 * @author masoome.aghayari
 * @since 1/12/24
 */

import com.mancala.model.annotation.PocketIndex;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PocketIndexValidator implements ConstraintValidator<PocketIndex, Integer> {
    private int notAllowedValue;

    @Override
    public void initialize(PocketIndex constraintAnnotation) {
        this.notAllowedValue = constraintAnnotation.notAllowedValue();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value != null && value >= 0 && value != notAllowedValue && value < 13;

    }
}

