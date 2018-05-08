package com.klaudi73.blog.controllers;


import com.klaudi73.blog.models.ArticleEntity;
import com.klaudi73.blog.repositories.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ArticleController {

    @Autowired
    ArticleRepo articleRepo;
    private ArticleEntity article;
    private Model model;

    @GetMapping("/addArticle")
    public String addArticle(Model model) {
        model.addAttribute("article", new ArticleEntity());
        return "articleFormView";
    }

    @PostMapping("/addArticle")
    public String addArticle(@ModelAttribute ArticleEntity articleEntity, Model model, RedirectAttributes redirectAttributes) {
        articleRepo.save(articleEntity);
        //model.addAttribute("timeCol", timeCol);
        Iterable<ArticleEntity> articlesCol = articleRepo.findAllByOrderByTitleAsc();
        redirectAttributes.addFlashAttribute("articlesCol", articlesCol);
        return "redirect:/articlesView";
    }


    @GetMapping("/viewArticles")
    public String viewArticles(@ModelAttribute("articlesCol") Iterable<ArticleEntity> articlesCol, Model model) {
        model.addAttribute("articlesCol", articlesCol);
        return "articlesView";
    }

    @GetMapping("/viewArticles")
    public String viewArticles(Model model, RedirectAttributes redirectAttributes) {
        Iterable<ArticleEntity> articlesCol = articleRepo.findAllByOrderByTitleAsc();
        redirectAttributes.addFlashAttribute("articlesCol", articlesCol);
        return "articlesView";
    }

    @GetMapping("/viewArticle")
    public String viewArticle(@ModelAttribute("article") ArticleEntity article, Model model) {
        model.addAttribute("article", article);
        return "articleView";
    }

    @PostMapping("/viewArticle")
    public String viewArticle(@ModelAttribute("article") ArticleEntity article, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("article", article);
        return "articleView";
    }
}
