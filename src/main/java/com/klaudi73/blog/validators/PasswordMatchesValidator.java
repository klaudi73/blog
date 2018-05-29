package com.klaudi73.blog.validators;

import com.klaudi73.blog.models.RegisterUser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        RegisterUser registerUser = (RegisterUser) obj;
        return registerUser.getPassword().equals(registerUser.getPassword2());
    }
}