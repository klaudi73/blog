package com.klaudi73.blog.controllers;

import com.klaudi73.blog.events.OnRegistrationCompleteEvent;
import com.klaudi73.blog.models.RegisterUser;
import com.klaudi73.blog.models.UserEntity;
import com.klaudi73.blog.models.VerificationToken;
import com.klaudi73.blog.repositories.UserRepo;
import com.klaudi73.blog.repositories.VerificationTokenRepo;
import com.klaudi73.blog.services.UserService;
import com.klaudi73.blog.exceptions.EmailExistsException;
import com.klaudi73.blog.exceptions.LoginExistsException;
import com.klaudi73.blog.exceptions.PasswordNotMatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Controller
public class RegisterController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserService service;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/register")
    public String register(Model model) {
        System.out.println("---RegisterController---");
        System.out.println("---metoda register(Model model)---");
        RegisterUser registerUser = new RegisterUser();
        model.addAttribute("registerUser", registerUser);
        return "registerFormView";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterUser registerUser, BindingResult bindingResult,
                           final HttpServletRequest request, final Errors errors) {
        System.out.println("---RegisterController---");
        System.out.println("---metoda register(@Valid @ModelAttribute RegisterUser registerUser, \n" +
                        "BindingResult bindingResult, \n" +
                        "final HttpServletRequest request, final Errors errors)---");
        UserEntity registered;

        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError err: errorList) {
                System.out.println(err.getDefaultMessage());
                System.out.println("err: " + err);
            }
            return "registerFormView";
        } else {
            //System.out.println("registered: " + registered);
            registered = createUserAccount(registerUser, bindingResult);
            System.out.println("register: createUserAccount = " + registered);
        }
        try {
            final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            System.out.println("appUrl: " + appUrl);
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
            System.out.println("registered: " + registered);
        } catch (final Exception ex) {
            System.out.println("LOGGER.warn(\"Unable to register user\", ex = " + ex);
            System.out.println(ex.getCause());
            ex.printStackTrace();
            //return "emailError";
            return "registerFormView";
        }
        return "successRegister";

        /*
        if (registered == null) {
            return "registerFormView";
        } else if (registerUser.getPassword().equals(registerUser.getPassword2())) {
                return "redirect:/login";
        } else {
                return "registerFormView";
        }
        */
    }

    @PostMapping("/successRegister")
    public String successRegister() {
        System.out.println("---RegisterController---");
        System.out.println("---metoda successRegister()---");
        return "successRegister";
    }

    @GetMapping("/registrationConfirm")
    public String registrationConfirm(final HttpServletRequest request,
                                      final Model model,
                                      @RequestParam("token") final String token) {
        System.out.println("---RegisterController---");
        System.out.println("---metoda registrationConfirm(final HttpServletRequest request, \n" +
                "final Model model, \n" +
                "@RequestParam(\"token\") final String token)---");
        //VerificationTokenRepo verificationTokenRepo;
        final Locale locale = request.getLocale();
        final String result = service.validateVerificationToken(token);
        if (result.equals("valid")) {
            final UserEntity userEntity = service.getUser(token);
            model.addAttribute("message", messageSource.getMessage("message.accountVerified", null, locale));
            return "redirect:/registrationConfirmed" /*?lang=" + locale.getLanguage()*/;  //TODO - change url
        }
        model.addAttribute("message", messageSource.getMessage("auth.message." + result, null, locale));
        model.addAttribute("expired", "expired".equals(result));
        model.addAttribute("token", token);
        return "redirect:/badUser" /*.html?lang=" + locale.getLanguage()*/;   //TODO - change url
    }

    @GetMapping("/registrationConfirmed")
    public String registrationConfirmed(final HttpServletRequest request,
                                      final Model model) {
        return "registrationConfirmed";
    }

    private UserEntity createUserAccount(RegisterUser registerUser, BindingResult result) {
        System.out.println("---RegisterController---");
        System.out.println("---metoda createUserAccount(RegisterUser registerUser, BindingResult result)---");
        UserEntity registered;
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


}
