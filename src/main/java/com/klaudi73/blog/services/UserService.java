package com.klaudi73.blog.services;

import com.klaudi73.blog.models.RegisterUser;
import com.klaudi73.blog.models.UserEntity;
import com.klaudi73.blog.repositories.UserRepo;
import com.klaudi73.blog.validators.EmailExistsException;
import com.klaudi73.blog.validators.LoginExistsException;
import com.klaudi73.blog.validators.PasswordNotMatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepo userRepo;

    @Transactional
    @Override
    public UserEntity registerNewUserAccount(RegisterUser registerUser)
            throws EmailExistsException, LoginExistsException, PasswordNotMatchException {

        if (emailExist(registerUser.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email adress: "
                            +  registerUser.getEmail());
            //return null;
        }
        if (loginExist(registerUser.getLogin())) {
            throw new LoginExistsException(
                    "There is an account with that login: "
                            + registerUser.getLogin());
            //return null;
        }
        if (!registerUser.getPassword().equals(registerUser.getPassword2())) {
            throw new PasswordNotMatchException(
                    "Passwords not match");
        } else {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = bCryptPasswordEncoder.encode(registerUser.getPassword());
            registerUser.setPassword(encodedPassword);
            registerUser.setPassword2(registerUser.getPassword());
            UserEntity userEntity = new UserEntity(registerUser.getLogin(),
                        registerUser.getName(),
                        registerUser.getEmail(),
                        registerUser.getPassword(),
                        registerUser.getRole());
            userRepo.save(userEntity);
            return userEntity;
        }
        //return null;
    }

    private boolean emailExist(String email) {
        UserEntity userEntity = userRepo.getByEmail(email);
        System.out.println("emailExist: " + userEntity);
        if (userEntity != null) {
            System.out.println("emailExist: return true");
            return true;
        }
        System.out.println("emailExist: return false");
        return false;
    }

    private boolean loginExist(String login) {
        UserEntity userEntity = userRepo.getByLogin(login);
        System.out.println("loginExist: " + userEntity);
        if (userEntity != null) {
            System.out.println("loginExist: return true");
            return true;
        }
        System.out.println("loginExist: return false");
        return false;
    }
}