package com.klaudi73.blog.services;

import com.klaudi73.blog.models.RegisterUser;
import com.klaudi73.blog.models.UserEntity;
import com.klaudi73.blog.validators.EmailExistsException;
import com.klaudi73.blog.validators.LoginExistsException;
import com.klaudi73.blog.validators.PasswordNotMatchException;

public interface IUserService {
    UserEntity registerNewUserAccount(RegisterUser registerUser)
            throws EmailExistsException, LoginExistsException, PasswordNotMatchException;
}