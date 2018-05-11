package com.klaudi73.blog.controllers;

import com.klaudi73.blog.models.UserEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AboutController {

    /*@GetMapping("/about")
    public String about(@ModelAttribute("user") UserEntity user, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("user", user);
        return "about";
    }
    */

    @GetMapping("/about")
    public String about(){
        return "about";
    }

}
