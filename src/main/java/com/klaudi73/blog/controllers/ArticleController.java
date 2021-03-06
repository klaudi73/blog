package com.klaudi73.blog.controllers;

import com.klaudi73.blog.BlogApplication;
import com.klaudi73.blog.models.ArticleEntity;
import com.klaudi73.blog.repositories.ArticleRepo;
import com.klaudi73.blog.services.ArticleService;
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
        System.out.println("---ArticleController---");
        System.out.println("---metoda addArticle(Model model)---");
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
        System.out.println("---ArticleController---");
        System.out.println("addArticle(@ModelAttribute ...)---");
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
    public String viewArticles(@ModelAttribute("articlesCol") Iterable<ArticleEntity> articlesCol, Model model) {
        System.out.println("---ArticleController---");
        System.out.println("---metoda viewArticles(@ModelAttribute(... ))---");
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
        System.out.println("---ArticleController---");
        System.out.println("---metoda viewAllArticles(Model model)---");
        Iterable<ArticleEntity> articlesCol = showShortArticles();
        model.addAttribute("articlesCol", articlesCol);
        return "articlesView";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/viewMyArticles")
    public String viewMyArticles(Model model) {
        System.out.println("---ArticleController---");
        System.out.println("---metoda viewMyArticles(Model model)---");
        Iterable<ArticleEntity> articlesCol = showShortArticles(BlogApplication.user.getUserId());
        model.addAttribute("articlesCol", articlesCol);
        return "articlesView";
    }

    @GetMapping("/viewArticle")
    public String viewArticle(@ModelAttribute("id") Long id, Model model) {
        System.out.println("---ArticleController---");
        System.out.println("---metoda viewArticle(@ModelAttribute(\"id\") Long id, Model model)---");
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
        System.out.println("---ArticleController---");
        System.out.println("---metoda viewArticle(@ModelAttribute(\"article\") ArticleEntity article, Model model)---");
        model.addAttribute("article", article);
        return "articleView";
    }

    public Iterable<ArticleEntity> showShortArticles() {
        System.out.println("---ArticleController---");
        System.out.println("---metoda showShortArticles()---");
        return getArticleEntities(null, articleRepo);
        /*
        Iterable<ArticleEntity> articlesCol;
        System.out.println("articleRepo.count(): " + articleRepo.count());
        articlesCol = articleRepo.findAllByOrderByIdDesc();
        List<ArticleEntity> articlesCol2 = new ArrayList<>();
        ArticleService.changeArticlesToShort(articlesCol, articlesCol2);
        Iterable<ArticleEntity> articlesCollection = articlesCol2;
        return articlesCollection; */
    }

    public Iterable<ArticleEntity> showShortArticles(Long userId) {
        System.out.println("---ArticleController---");
        System.out.println("---showShortArticles(Long userId)---");
        return getArticleEntities(userId, articleRepo);
    }

    static Iterable<ArticleEntity> getArticleEntities(Long userId, ArticleRepo articleRepo) {
        System.out.println("---ArticleController---");
        System.out.println("---getArticleEntities(Long userId, ArticleRepo articleRepo)---");
        Iterable<ArticleEntity> articlesCol;
        System.out.println("articleRepo.count(): " + articleRepo.count());
        if (userId == null) {
            articlesCol = articleRepo.findAllByOrderByIdDesc();
        } else {
            articlesCol = articleRepo.findAllByAuthorIdOrderByIdDesc(userId);
        }
        List<ArticleEntity> articlesCol2 = new ArrayList<>();
        ArticleService.changeArticlesToShort(articlesCol, articlesCol2);
        Iterable<ArticleEntity> articlesCollection = articlesCol2;
        return articlesCollection;
    }
}
