package com.klaudi73.blog.services;

import com.klaudi73.blog.exceptions.EmailExistsException;
import com.klaudi73.blog.exceptions.LoginExistsException;
import com.klaudi73.blog.exceptions.PasswordNotMatchException;
import com.klaudi73.blog.models.RegisterUser;
import com.klaudi73.blog.models.UserEntity;
import com.klaudi73.blog.models.VerificationToken;
import com.klaudi73.blog.repositories.RoleRepo;
import com.klaudi73.blog.repositories.UserRepo;
import com.klaudi73.blog.repositories.VerificationTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Calendar;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private VerificationTokenRepo verificationTokenRepo;

    @Autowired
    private UserService service;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MessageSource messageSource;

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";


    @Transactional
    @Override
    public UserEntity registerNewUserAccount(RegisterUser registerUser)
            throws EmailExistsException, LoginExistsException, PasswordNotMatchException {

        if (emailExist(registerUser.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email adress: "
                            + registerUser.getEmail());
        }
        if (loginExist(registerUser.getLogin())) {
            throw new LoginExistsException(
                    "There is an account with that login: "
                            + registerUser.getLogin());
        }
        if (!registerUser.getPassword().equals(registerUser.getPassword2())) {
            throw new PasswordNotMatchException(
                    "Passwords not match");
        } else {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = bCryptPasswordEncoder.encode(registerUser.getPassword());
            registerUser.setPassword(encodedPassword);
            registerUser.setPassword2(registerUser.getPassword());
            UserEntity userEntity = new UserEntity();
            userEntity.setLogin(registerUser.getLogin());
            userEntity.setName(registerUser.getName());
            userEntity.setLastName(registerUser.getLastName());
            userEntity.setEmail(registerUser.getEmail());
            userEntity.setPassword(registerUser.getPassword());
            userEntity.setRoles(Arrays.asList(roleRepo.findByName("ROLE_USER")));
            userEntity.setEnabled(false);
            userRepo.save(userEntity);
            //final String token = UUID.randomUUID().toString();
            //service.createVerificationTokenForUser(userEntity, token);
            //final SimpleMailMessage email = RegistrationListener.constructEmailMessage(event, userEntity, token);
            //mailSender.send(email);
            return userEntity;
        }
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

    public void createVerificationTokenForUser(final UserEntity userEntity, final String token) {
        final VerificationToken myToken = new VerificationToken(token, userEntity);
        verificationTokenRepo.save(myToken);
    }

    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = verificationTokenRepo.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final UserEntity userEntity = verificationToken.getUserEntity();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            verificationTokenRepo.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        userEntity.setEnabled(true);
        // verificationTokenRepo.delete(verificationToken);
        userRepo.save(userEntity);
        return TOKEN_VALID;
    }

    public UserEntity getUser(String verificationToken) {
        final VerificationToken token = verificationTokenRepo.findByToken(verificationToken);
        if (token != null) {
            return token.getUserEntity();
        }
        return null;
    }

    public UserEntity getUserByLogin(String login) {
        final UserEntity userEntity = userRepo.getByLogin(login);
        return userEntity;
    }
}
