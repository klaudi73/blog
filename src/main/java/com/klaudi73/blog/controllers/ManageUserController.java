package com.klaudi73.blog.controllers;

import com.klaudi73.blog.BlogApplication;
import com.klaudi73.blog.models.ArticleEntity;
import com.klaudi73.blog.models.UserEntity;
import com.klaudi73.blog.repositories.ArticleRepo;
import com.klaudi73.blog.repositories.RoleRepo;
import com.klaudi73.blog.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;


@Controller
public class ManageUserController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    private UserEntity userEntity;
    private Model model;

    /*
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
    */


    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/manageMyAccount")
    public String manageMyAccount(Model model) {
        System.out.println("---ManageUserController---");
        System.out.println("---metoda manageMyAccount(Model model)---");
        Long loginId = BlogApplication.user.getUserId();
        Iterable<UserEntity> usersCol = showUsersShort(loginId);
        model.addAttribute("usersCol", usersCol);
        return "manageUsersView";
    }

    @Secured({"ROLE_ADMIN", "PRIVILEGE_WRITE"})
    @GetMapping("/manageAllAccounts")
    public String manageAllUsers(Model model) {
        System.out.println("---ManageUsersController---");
        System.out.println("---metoda manageAllUsers(Model model)---");
        Long loginId = BlogApplication.user.getUserId();
        Iterable<UserEntity> usersCol = showAllUsersShort();
        model.addAttribute("usersCol", usersCol);
        return "manageUsersView";
    }

    /*
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/manageUser")
    public String manageMyArticle(@ModelAttribute("id") Long id, Model model) {
        System.out.println("---ManageUserController---");
        System.out.println("---metoda manageMyArticle(@ModelAttribute(\"id\") Long id, Model model)---");
        article = articleRepo.findById(id).get();
        System.out.println("article: " + article);
        model.addAttribute("article", article);
        return "manageArticleView";
    }
    */


    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/editUser")
    public String editUser(@ModelAttribute("id") Long userId, Model model,
                                RedirectAttributes redirectAttributes) {
        System.out.println("---ManageUserController---");
        System.out.println("---metoda editUser(@ModelAttribute(\"id\") Long userId, Model model, \n" +
                "RedirectAttributes redirectAttributes)---");
        userEntity = userRepo.findById(userId).get();
        System.out.println("user: " + userEntity);
        model.addAttribute("user", userEntity);

        redirectAttributes.addFlashAttribute("id", userEntity.getUserId());
        return "editUserFormView";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/editUser")
    public String confirmEditionOfUser(@ModelAttribute("newLogin") String newLogin,
                                            @ModelAttribute("newName") String newName,
                                            @ModelAttribute("newLastName") String newLastName,
                                            @ModelAttribute("newEmail") String newEmail,
                                            @ModelAttribute("newRole") String newRole,
                                            @ModelAttribute("newPassword") String newPassword,
                                            @ModelAttribute("id") Long id, Model model) {
        System.out.println("---ManageUserController---");
        System.out.println("---metoda String confirmEditionOfUser(@ModelAttribute(\"newArticle\") String newArticle, \n" +
                        "@ModelAttribute(\"newName\") String newName, \n" +
                        "@ModelAttribute(\"newLastName\") String newLastName, \n" +
                        "@ModelAttribute(\"newEmail\") String newEmail, \n" +
                        "@ModelAttribute(\"newRole\") String newRole, \n" +
                        "@ModelAttribute(\"id\") Long id, Model model)");
        UserEntity userToUpdate = userRepo.findById(id).get();
        userToUpdate.setLogin(newLogin);
        userToUpdate.setName(newName);
        userToUpdate.setEmail(newEmail);
        userToUpdate.setPassword(newPassword);
        userToUpdate.setRoles(Arrays.asList(roleRepo.findByName(newRole)));
        userRepo.save(userToUpdate);
        return "redirect:/manage";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/deleteUser")
    public String deleteUser(@ModelAttribute("id") Long userId, Model model,
                                RedirectAttributes redirectAttributes) {
        System.out.println("---ManageUserController---");
        System.out.println("---metoda deleteUser(@ModelAttribute(\"id\") Long userId, Model model, \n" +
                "RedirectAttributes redirectAttributes)---");
        userEntity = userRepo.findById(userId).get();
        System.out.println("user: " + userEntity);
        model.addAttribute("user", userEntity);

        redirectAttributes.addFlashAttribute("id", userEntity.getUserId());
        return "deleteUserFormView";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/deleteUser")
    public String confirmDeletionOfUser(@ModelAttribute("newArticle") String newLogin,
                                            @ModelAttribute("newTitle") String newName,
                                            @ModelAttribute("id") Long id, Model model) {
        System.out.println("---ManageUserController---");
        System.out.println("---metoda confirmDeletionOfUser(@ModelAttribute(\"newArticle\") String newArticle, \n" +
                "@ModelAttribute(\"newTitle\") String newTitle, \n" +
                "@ModelAttribute(\"id\") Long id, Model model)---");
        UserEntity userToDelete = userRepo.findById(id).get();
        userToDelete.setLogin(newLogin);
        userToDelete.setName(newName);
        userToDelete.setUserId(userToDelete.getUserId());
        userRepo.delete(userToDelete);
        return "redirect:/manageUsers";
    }

    public Iterable<UserEntity> showUsersShort(Long userId) {
        System.out.println("---ManageUserController---");
        System.out.println("---metoda showUsersShort(Long loginId)---");
        return UserController.getUserEntities(userId, userRepo);
    }


    public Iterable<UserEntity> showAllUsersShort() {
        System.out.println("---ManageUsersController---");
        System.out.println("---metoda showAllUsersShort()---");
        return UserController.getUserEntities(null, userRepo);
    }
}
