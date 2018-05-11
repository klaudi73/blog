package com.klaudi73.blog.controllers;


import ch.qos.logback.core.pattern.color.RedCompositeConverter;
import com.klaudi73.blog.models.UserEntity;
import com.klaudi73.blog.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    @Autowired
    UserRepo userRepo;

    @GetMapping("/register")
    public String register(Model model, RedirectAttributes redirectAttributes) {
        UserEntity user = new UserEntity();
        model.addAttribute("user", user);
        redirectAttributes.addFlashAttribute("user", user);
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
