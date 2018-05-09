package com.klaudi73.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/error-view")
    public String errorViewPage() {
        return "errorView";
    }
}
