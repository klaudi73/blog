package com.klaudi73.blog.controllers;

import com.klaudi73.blog.models.UserEntity;
import com.klaudi73.blog.repositories.UserRepo;
import com.klaudi73.blog.services.MyAppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    UserRepo userRepo;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("login", new UserEntity());
        return "loginFormView";
    }

    @PostMapping("/login")
    public String loginForm(@ModelAttribute UserEntity userEntity, RedirectAttributes redirectAttributes) {
        MyAppUserDetailsService myAppUserDetailsService = new MyAppUserDetailsService();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodedPassword);
        redirectAttributes.addFlashAttribute("user", userEntity);
        //myAppUserDetailsService.loadUserByUsername(userEntity.getName());
        myAppUserDetailsService.loadUserByLogin(userEntity.getLogin());

        return "redirect:/";
    }

    @Secured("ROLE_ADMIN, ROLE_USER")
    @GetMapping("/logout")
    public String logoutForm(Model model) {
        model.addAttribute("login", new UserEntity());
        return "logoutView";
    }

    @Secured("ROLE_ADMIN, ROLE_USER")
    @PostMapping("/logout")
    public String logoutForm(@ModelAttribute UserEntity userEntity) {
        return "logoutView";
    }
}
