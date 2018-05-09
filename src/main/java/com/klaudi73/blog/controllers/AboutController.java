package com.klaudi73.blog.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @GetMapping("/about")
    public String about(){
        return "about";
    }

    /*
    @Secured("ROLE_ADMIN")
    @GetMapping("/secret")
    public String secret() {
        return "secretPage";
    }
    */
}
