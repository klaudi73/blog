package com.klaudi73.blog.validators;

import com.klaudi73.blog.models.UserEntity;
import com.klaudi73.blog.repositories.UserRepo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginValidator
        implements ConstraintValidator<ValidLogin, String> {

    private Pattern pattern;
    private Matcher matcher;
    UserRepo userRepo;

    private static final String LOGIN_PATTERN = "^[_A-Za-z0-9-+]$";

    @Override
    public void initialize(ValidLogin constraintAnnotation) {
    }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext context){
        return (validateLogin(login));
    }

    private boolean validateLogin(String login) {
        pattern = Pattern.compile(LOGIN_PATTERN);
        matcher = pattern.matcher(login);
        UserEntity userEntity = userRepo.getByLogin(login);
        if (userEntity.equals(null)) {
            System.out.println(userEntity);
        } else {
            System.out.println(userEntity);
        }
        return matcher.matches();
    }
}