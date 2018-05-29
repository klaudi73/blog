package com.klaudi73.blog.services;

import com.klaudi73.blog.models.RegisterUser;
import com.klaudi73.blog.repositories.UserRepo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

public class RegisterUserValidateService implements ConstraintValidator<RegisterUserValidatorService, String> {

    UserRepo userRepo;

    public void initialize(RegisterUserValidatorService registerUserValidatorService) {

    }

    public boolean isValid(String login, ConstraintValidatorContext ctx) {
        if (login == null) {
            return false;
        }


        if (userRepo.getByLogin(login).equals(null)) {
            return true;
        } else {
            return false;
        }
    }
}
