package com.klaudi73.blog.services;

import com.klaudi73.blog.models.RegisterUser;
import com.klaudi73.blog.models.UserEntity;
import com.klaudi73.blog.exceptions.EmailExistsException;
import com.klaudi73.blog.exceptions.LoginExistsException;
import com.klaudi73.blog.exceptions.PasswordNotMatchException;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    UserEntity registerNewUserAccount(RegisterUser registerUser)
            throws EmailExistsException, LoginExistsException, PasswordNotMatchException;
}