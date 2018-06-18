package com.klaudi73.blog.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralController {

    @GetMapping("/")
    public String home(){
        System.out.println("---GeneralController---");
        System.out.println("---metoda home()---");
        return "index";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/secret")
    public String secret() {
        System.out.println("---GeneralController---");
        System.out.println("---metoda secret()---");
        return "secretPage";
    }
}
