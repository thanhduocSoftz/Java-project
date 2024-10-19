package com.softz.identity.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RolesValidator implements ConstraintValidator<RolesContraint, List<Integer>> {
    @Override
    public boolean isValid(List<Integer> arg0, ConstraintValidatorContext arg1) {
        if(arg0 == null || arg0.isEmpty())
        {
            return false;
        }
        Set<Integer> set = new HashSet<>(arg0);
        return set.size() == arg0.size();
    }}
