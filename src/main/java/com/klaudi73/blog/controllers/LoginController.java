package com.klaudi73.blog.controllers;

import com.klaudi73.blog.models.UserEntity;
import com.klaudi73.blog.repositories.UserRepo;
import com.klaudi73.blog.services.MyAppUserDetailsService;
import com.klaudi73.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        System.out.println("---LoginController---");
        System.out.println("---metoda loginForm(Model model)---");
        String login = new String();
        String password = new String();
        model.addAttribute("login", login);
        model.addAttribute("password", password);
        return "loginFormView";
    }

    @PostMapping("/badLoginOrPassword")
    public String badLoginOrPassword() {
        System.out.println("---LoginController---");
        System.out.println("---metoda badLoginOrPassword()---");
        return "badLoginOrPassword";
    }

    //@Secured("ROLE_ADMIN, ROLE_USER")
    @GetMapping("/logout")
    public String logoutForm(Model model) {
        System.out.println("---LoginController---");
        System.out.println("---metoda logoutForm(Model model)---");
        //model.addAttribute("login", new UserEntity());
        return "logoutView";
    }

    //@Secured("ROLE_ADMIN, ROLE_USER")
    @PostMapping("/logout")
    public String logoutForm(@ModelAttribute UserEntity userEntity) {
        System.out.println("---LoginController---");
        System.out.println("---metoda logoutForm(@ModelAttribute UserEntity userEntity)---");
        return "logoutView";
    }

    /*
    @PostMapping("/logout")
    public String logoutForm() {
        System.out.println("---LoginController---");
        System.out.println("---metoda logoutForm()---");
        return "logoutView";
    }
    */
}
