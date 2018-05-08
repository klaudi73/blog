package com.klaudi73.blog.controllers;

import com.klaudi73.blog.models.UserEntity;
import com.klaudi73.blog.repositories.UserRepo;
import com.klaudi73.blog.services.MyAppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String loginForm(@ModelAttribute UserEntity userEntity) {
        MyAppUserDetailsService myAppUserDetailsService = new MyAppUserDetailsService();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodedPassword);

        //myAppUserDetailsService.loadUserByUsername(userEntity.getName());
        myAppUserDetailsService.loadUserByLogin(userEntity.getLogin());
        return "redirect:/";
    }

}
