package com.klaudi73.blog.controllers;

import com.klaudi73.blog.BlogApplication;
import com.klaudi73.blog.models.ArticleEntity;
import com.klaudi73.blog.models.UserEntity;
import com.klaudi73.blog.repositories.ArticleRepo;
import com.klaudi73.blog.repositories.UserRepo;
import com.klaudi73.blog.services.ArticleService;
import com.klaudi73.blog.services.UserService;
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
public class UserController {

    @Autowired
    UserRepo userRepo;
    private UserEntity userEntity;
    private Model model;

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/addUser")
    public String addArticle(Model model) { //, RedirectAttributes redirectAttributes) {
        System.out.println("---UserController---");
        System.out.println("---metoda addUser(Model model)---");
        try {
            model.addAttribute("user", new UserEntity());
            //redirectAttributes.addFlashAttribute("user", user);
        return "userFormView";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return "/";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/addUser")
    public String addArticle(@ModelAttribute UserEntity userEntity, RedirectAttributes redirectAttributes) {
        System.out.println("---UserController---");
        System.out.println("addUser(@ModelAttribute ...)---");
        try {
            //userEntity.setAuthorId(BlogApplication.user.getUserId());
            userRepo.save(userEntity);
            //Iterable<ArticleEntity> articlesCol = articleRepo.findAllByOrderByTitleAsc();
            Iterable<UserEntity> usersCol = showShortUsers();
            redirectAttributes.addFlashAttribute("usersCol", usersCol);
            return "redirect:/viewUsers";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return "/";
    }


    @GetMapping("/viewUsers")
    public String viewUsers(@ModelAttribute("usersCol") Iterable<UserEntity> usersCol, Model model) {
        System.out.println("---UserController---");
        System.out.println("---metoda viewUsers(@ModelAttribute(... ))---");
        if (Objects.isNull(usersCol)) {
            //articlesCol = articleRepo.findAllByOrderByTitleAsc();
            usersCol = userRepo.findAllByOrderByUserIdAsc();
            //redirectAttributes.addFlashAttribute("articlesCol", articlesCol);
        }
        model.addAttribute("usersCol", usersCol);
        return "manageUsersView";
    }

    @GetMapping("/viewAllUsers")
    public String viewAllUsers(Model model) {
        System.out.println("---UserController---");
        System.out.println("---metoda viewAllUsers(Model model)---");
        Iterable<UserEntity> usersCol = showShortUsers();
        model.addAttribute("usersCol", usersCol);
        return "manageUsersView";
    }

    /*
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/viewMyArticles")
    public String viewMyArticles(Model model) {
        System.out.println("---ArticleController---");
        System.out.println("---metoda viewMyArticles(Model model)---");
        Iterable<ArticleEntity> articlesCol = showShortArticles(BlogApplication.user.getUserId());
        model.addAttribute("articlesCol", articlesCol);
        return "articlesView";
    }
    */

    @GetMapping("/viewUser")
    public String viewUser(@ModelAttribute("id") Long id, Model model) {
        System.out.println("---UserController---");
        System.out.println("---metoda viewUser(@ModelAttribute(\"id\") Long id, Model model)---");
        userEntity = userRepo.findById(id).get();
        /*if (!listArticle.isEmpty()) {
            article = listArticle.get(0);
        }*/
        model.addAttribute("user", userEntity);
        return "manageUsersView";
    }

    @PostMapping("/viewUser")
    public String viewUser(@ModelAttribute("user") UserEntity userEntity,
                              Model model) {
        System.out.println("---UserController---");
        System.out.println("---metoda viewUser(@ModelAttribute(\"user\") UserEntity userEntity, Model model)---");
        model.addAttribute("user", userEntity);
        return "manageUsersView";
    }

    public Iterable<UserEntity> showShortUsers() {
        System.out.println("---UserController---");
        System.out.println("---metoda showShortUsers()---");
        return getUserEntities(null, userRepo);
        /*
        Iterable<ArticleEntity> articlesCol;
        System.out.println("articleRepo.count(): " + articleRepo.count());
        articlesCol = articleRepo.findAllByOrderByIdDesc();
        List<ArticleEntity> articlesCol2 = new ArrayList<>();
        ArticleService.changeArticlesToShort(articlesCol, articlesCol2);
        Iterable<ArticleEntity> articlesCollection = articlesCol2;
        return articlesCollection; */
    }

    public Iterable<UserEntity> showShortUsers(Long userId) {
        System.out.println("---UserController---");
        System.out.println("---metoda: showShortUsers(Long userId)---");
        return getUserEntities(userId, userRepo);
    }

    static Iterable<UserEntity> getUserEntities(Long userId, UserRepo userRepo) {
        System.out.println("---UserController---");
        System.out.println("---metoda: getUserEntities(Long userId, UserRepo userRepo)---");
        Iterable<UserEntity> usersCol;
        System.out.println("userRepo.count(): " + userRepo.count());
        if (userId == null) {
            usersCol = userRepo.findAllByOrderByUserIdAsc();
        } else {
            usersCol = userRepo.findAllByUserIdOrderByUserIdDesc(userId);
        }
        Iterable<UserEntity> usersCollection = usersCol;
        return usersCollection;
    }
}
