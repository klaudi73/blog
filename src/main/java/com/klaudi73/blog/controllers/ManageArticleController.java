package com.klaudi73.blog.controllers;

import com.klaudi73.blog.BlogApplication;
import com.klaudi73.blog.models.ArticleEntity;
import com.klaudi73.blog.repositories.ArticleRepo;
import com.klaudi73.blog.services.ArticleService;
import com.klaudi73.blog.services.MyAppUserDetailsService;
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
public class ManageArticleController {

    @Autowired
    ArticleRepo articleRepo;
    private ArticleEntity article;
    private Model model;

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/manage")
    public String showMyArticles(Model model) {
        System.out.println("---ManageArticleController---");
        System.out.println("---metoda showMyArticles(Model model)---");
        Long loginId = BlogApplication.user.getUserId();
        Iterable<ArticleEntity> articlesCol = showMyArticlesShort(loginId);
        model.addAttribute("articlesCol", articlesCol);
        return "manageArticlesView";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/manageMyArticles")
    public String manageMyArticles(Model model) {
        System.out.println("---ManageArticleController---");
        System.out.println("---metoda manageMyArticles(Model model)---");
        Long loginId = BlogApplication.user.getUserId();
        Iterable<ArticleEntity> articlesCol = showMyArticlesShort(loginId);
        model.addAttribute("articlesCol", articlesCol);
        return "manageArticlesView";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/manageAllArticles")
    public String manageAllArticles(Model model) {
        System.out.println("---ManageArticleController---");
        System.out.println("---metoda manageAllArticles(Model model)---");
        Long loginId = BlogApplication.user.getUserId();
        Iterable<ArticleEntity> articlesCol = showAllArticlesShort();
        model.addAttribute("articlesCol", articlesCol);
        return "manageArticlesView";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/manageArticle")
    public String manageMyArticle(@ModelAttribute("id") Long id, Model model) {
        System.out.println("---ManageArticleController---");
        System.out.println("---metoda manageMyArticle(@ModelAttribute(\"id\") Long id, Model model)---");
        article = articleRepo.findById(id).get();
        System.out.println("article: " + article);
        model.addAttribute("article", article);
        return "manageArticleView";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/editArticle")
    public String editMyArticle(@ModelAttribute("id") Long articleId, Model model,
                                RedirectAttributes redirectAttributes) {
        System.out.println("---ManageArticleController---");
        System.out.println("---metoda editMyArticle(@ModelAttribute(\"id\") Long articleId, Model model, \n" +
                "RedirectAttributes redirectAttributes)---");
        article = articleRepo.findById(articleId).get();
        System.out.println("article: " + article);
        model.addAttribute("article", article);

        redirectAttributes.addFlashAttribute("id", article.getId());
        return "editArticleFormView";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/editArticle")
    public String confirmEditionOfMyArticle(@ModelAttribute("newArticle") String newArticle,
                                            @ModelAttribute("newTitle") String newTitle,
                                            @ModelAttribute("id") Long id, Model model) {
        System.out.println("---ManageArticleController---");
        System.out.println("---metoda String confirmEditionOfMyArticle(@ModelAttribute(\"newArticle\") String newArticle, \n" +
                "@ModelAttribute(\"newTitle\") String newTitle, \n" +
                "@ModelAttribute(\"id\") Long id, Model model)---");
        ArticleEntity articleToUpdate = articleRepo.findById(id).get();
        articleToUpdate.setArticle(newArticle);
        articleToUpdate.setTitle(newTitle);
        articleToUpdate.setAuthorId(articleToUpdate.getAuthorId());
        articleRepo.save(articleToUpdate);
        return "redirect:/manage";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/deleteArticle")
    public String deleteMyArticle(@ModelAttribute("id") Long articleId, Model model,
                                RedirectAttributes redirectAttributes) {
        System.out.println("---ManageArticleController---");
        System.out.println("---metoda deleteMyArticle(@ModelAttribute(\"id\") Long articleId, Model model, \n" +
                "RedirectAttributes redirectAttributes)---");
        article = articleRepo.findById(articleId).get();
        System.out.println("article: " + article);
        model.addAttribute("article", article);

        redirectAttributes.addFlashAttribute("id", article.getId());
        return "deleteArticleFormView";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/deleteArticle")
    public String confirmDeletionOfMyArticle(@ModelAttribute("newArticle") String newArticle,
                                            @ModelAttribute("newTitle") String newTitle,
                                            @ModelAttribute("id") Long id, Model model) {
        System.out.println("---ManageArticleController---");
        System.out.println("---metoda confirmDeletionOfMyArticle(@ModelAttribute(\"newArticle\") String newArticle, \n" +
                "@ModelAttribute(\"newTitle\") String newTitle, \n" +
                "@ModelAttribute(\"id\") Long id, Model model)---");
        ArticleEntity articleToDelete = articleRepo.findById(id).get();
        articleToDelete.setArticle(newArticle);
        articleToDelete.setTitle(newTitle);
        articleToDelete.setAuthorId(articleToDelete.getAuthorId());
        articleRepo.delete(articleToDelete);
        return "redirect:/manage";
    }

    public Iterable<ArticleEntity> showMyArticlesShort(Long loginId) {
        System.out.println("---ManageArticleController---");
        System.out.println("---metoda showMyArticlesShort(Long loginId)---");
        return ArticleController.getArticleEntities(loginId, articleRepo);
    }

    public Iterable<ArticleEntity> showAllArticlesShort() {
        System.out.println("---ManageArticleController---");
        System.out.println("---metoda showAllArticlesShort()---");
        return ArticleController.getArticleEntities(null, articleRepo);
    }
}
