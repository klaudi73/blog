package com.klaudi73.blog.controllers;

import com.klaudi73.blog.BlogApplication;
import com.klaudi73.blog.models.ArticleEntity;
import com.klaudi73.blog.models.UserEntity;
import com.klaudi73.blog.repositories.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Objects;


@Controller
public class ArticleController {

    @Autowired
    ArticleRepo articleRepo;
    private ArticleEntity article;
    private Model model;

    @Secured("ROLE_ADMIN, ROLE_USER")
    @GetMapping("/addArticle")
    public String addArticle(@ModelAttribute("user") UserEntity user,
                             Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("article", new ArticleEntity());
        redirectAttributes.addFlashAttribute("user", user);
        return "articleFormView";
    }

    @Secured("ROLE_ADMIN, ROLE_USER")
    @PostMapping("/addArticle")
    public String addArticle(@ModelAttribute ArticleEntity articleEntity, RedirectAttributes redirectAttributes) {

        articleEntity.setAuthorId(BlogApplication.user.getUserId());
        articleRepo.save(articleEntity);
        Iterable<ArticleEntity> articlesCol = articleRepo.findAllByOrderByTitleAsc();
        redirectAttributes.addFlashAttribute("articlesCol", articlesCol);
        return "redirect:/viewArticles";
    }


    @GetMapping("/viewArticles")
    public String viewArticles(@ModelAttribute("articlesCol") Iterable<ArticleEntity> articlesCol, Model model,
                               RedirectAttributes redirectAttributes) {
        if (Objects.isNull(articlesCol)) {
            //articlesCol = articleRepo.findAllByOrderByTitleAsc();
            articlesCol = articleRepo.findAllByOrderByIdAsc();
            redirectAttributes.addFlashAttribute("articlesCol", articlesCol);
        }
        model.addAttribute("articlesCol", articlesCol);
        return "articlesView";
    }

    @GetMapping("/viewAllArticles")
    public String viewAllArticles(Model model) {
        //Iterable<ArticleEntity> articlesCol = articleRepo.findAllByOrderByTitleAsc();
        Iterable<ArticleEntity> articlesCol = articleRepo.findAllByOrderByIdAsc();
        model.addAttribute("articlesCol", articlesCol);
        return "articlesView";
    }

    @GetMapping("/viewArticle")
    public String viewArticle(@ModelAttribute("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        article = articleRepo.findById(id).get();
        /*if (!listArticle.isEmpty()) {
            article = listArticle.get(0);
        }*/
        model.addAttribute("article", article);
        return "articleView";
    }

    @PostMapping("/viewArticle")
    public String viewArticle(@ModelAttribute("article") ArticleEntity article,
                              Model model) {
        model.addAttribute("article", article);
        return "articleView";
    }


}
