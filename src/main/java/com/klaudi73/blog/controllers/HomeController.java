package com.klaudi73.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {

    @GetMapping("/index")
    public String indexPage(Model model) {
        System.out.println("---HomeController---");
        System.out.println("---metoda indexPage(Model model)---");
        model.addAttribute("pageTitle", "Index Page");
        return "index";
    }

    @GetMapping("/defaultView")
    public String defaultPage() {
        System.out.println("---HomeController---");
        System.out.println("---metoda defaultPage()---");
        return "defaultView";
    }

    @GetMapping("/error-view")
    public String errorViewPage(@ModelAttribute Error error, Model model) {
        System.out.println("---HomeController---");
        System.out.println("---metoda errorViewPage(@ModelAttribute Error error, Model model)---");
        model.addAttribute("error", error.toString());
        return "errorView";
    }
}
