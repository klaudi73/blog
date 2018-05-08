package com.klaudi73.blog.controllers;


import com.klaudi73.blog.models.UserEntity;
import com.klaudi73.blog.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    UserRepo userRepo;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserEntity());
        return "registerFormView";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserEntity userEntity) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodedPassword);
        userRepo.save(userEntity);
        return "redirect:/login";
    }
}
