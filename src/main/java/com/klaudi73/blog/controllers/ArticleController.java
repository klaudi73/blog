package com.klaudi73.blog.controllers;

import com.klaudi73.blog.BlogApplication;
import com.klaudi73.blog.models.ArticleEntity;
import com.klaudi73.blog.repositories.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Controller
public class ArticleController {

    @Autowired
    ArticleRepo articleRepo;
    private ArticleEntity article;
    private Model model;

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/addArticle")
    public String addArticle(Model model) { //, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("article", new ArticleEntity());
            //redirectAttributes.addFlashAttribute("user", user);
        return "articleFormView";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return "/";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/addArticle")
    public String addArticle(@ModelAttribute ArticleEntity articleEntity, RedirectAttributes redirectAttributes) {
        try {
            articleEntity.setAuthorId(BlogApplication.user.getUserId());
            articleRepo.save(articleEntity);
            //Iterable<ArticleEntity> articlesCol = articleRepo.findAllByOrderByTitleAsc();
            Iterable<ArticleEntity> articlesCol = showShortArticles();
            redirectAttributes.addFlashAttribute("articlesCol", articlesCol);
            return "redirect:/viewArticles";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return "/";
    }


    @GetMapping("/viewArticles")
    public String viewArticles(@ModelAttribute("articlesCol") Iterable<ArticleEntity> articlesCol, Model model
                               ) {
        if (Objects.isNull(articlesCol)) {
            //articlesCol = articleRepo.findAllByOrderByTitleAsc();
            articlesCol = articleRepo.findAllByOrderByIdAsc();
            //redirectAttributes.addFlashAttribute("articlesCol", articlesCol);
        }
        model.addAttribute("articlesCol", articlesCol);
        return "articlesView";
    }

    @GetMapping("/viewAllArticles")
    public String viewAllArticles(Model model) {
        Iterable<ArticleEntity> articlesCol = showShortArticles();
        model.addAttribute("articlesCol", articlesCol);
        return "articlesView";
    }

    @GetMapping("/viewArticle")
    public String viewArticle(@ModelAttribute("id") Long id, Model model) {
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

    public Iterable<ArticleEntity> showShortArticles() {
        Iterable<ArticleEntity> articlesCol;
        System.out.println("articleRepo.count(): " + articleRepo.count());
        articlesCol = articleRepo.findAll();
        List<ArticleEntity> articlesCol2 = new ArrayList<>();

        for (ArticleEntity article: articlesCol) {
            ArticleEntity article2 = article;
            String strArticle = article.getArticle();
            String strPoczatek = "";
            int j;
            if (strArticle.length() > 50) {
                j = 50;
            } else {
                j = strArticle.length();
            }
            for (int i = 0; i < j ; i++) {
                strPoczatek += strArticle.charAt(i);
            }
            strPoczatek += "...";
            article2.setArticle(strPoczatek);
            articlesCol2.add(article2);
        }
        Iterable<ArticleEntity> articlesCollection = articlesCol2;
        return articlesCollection;
    }
}
