package com.klaudi73.blog.controllers;

import com.klaudi73.blog.models.RegisterUser;
import com.klaudi73.blog.models.UserEntity;
import com.klaudi73.blog.repositories.UserRepo;
import com.klaudi73.blog.services.IUserService;
import com.klaudi73.blog.services.UserService;
import com.klaudi73.blog.validators.EmailExistsException;
import com.klaudi73.blog.validators.LoginExistsException;
import com.klaudi73.blog.validators.PasswordNotMatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
public class RegisterController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserService service;

    @GetMapping("/register")
    public String register(Model model) {
        RegisterUser registerUser = new RegisterUser();
        model.addAttribute("registerUser", registerUser);
        return "registerFormView";
    }

    @PostMapping("/register")
    //public String register(@Valid @ModelAttribute UserEntity userEntity, BindingResult bindingResult) {
    public String register(@Valid @ModelAttribute RegisterUser registerUser, BindingResult bindingResult) {

        UserEntity registered = new UserEntity();

        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError err: errorList) {
                System.out.println(err.getDefaultMessage());
            }
            return "registerFormView";
        } else {
            registered = createUserAccount(registerUser, bindingResult);
            System.out.println("register: createUserAccount = " + registered);
        }
        if (registered == null) {
            return "registerFormView";
        } else if (registerUser.getPassword().equals(registerUser.getPassword2())) {
                return "redirect:/login";
            } else {
                return "registerFormView";
                //return "redirect:error-view";
            }
    }

    private UserEntity createUserAccount(RegisterUser registerUser, BindingResult result) {
        UserEntity registered = null;
        try {
            registered = service.registerNewUserAccount(registerUser);
        } catch (EmailExistsException e) {
            result.rejectValue("email", "messages.regError", "Podany email istnieje");
            return null;
        } catch (LoginExistsException e) {
            result.rejectValue("login", "messages.regError", "Podany login istnieje");
            return null;
        } catch (PasswordNotMatchException e) {
            result.rejectValue("password", "messages.regError", "Hasła muszą być identyczne");
            result.rejectValue("password2", "messages.regError", "Hasła muszą być identyczne");
            return null;
        }
        if (Objects.isNull(registered)) {
            return null;
        }
        return registered;
    }

    private Boolean checkNotEmpty(RegisterUser registerUser) {
        if (registerUser.getLogin() == "") {
            return false;
        }
        if (registerUser.getName() == "") {
            return false;
        }
        if (registerUser.getPassword() == "") {
            return false;
        }
        if (registerUser.getPassword2() == "") {
            return false;
        }
        if (registerUser.getRole() == "") {
            return false;
        }
        return true;
    }

}
