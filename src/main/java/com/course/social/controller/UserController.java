package com.course.social.controller;

import com.course.social.domain.Role;
import com.course.social.domain.User;
import com.course.social.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserSevice userSevice;

   // @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("users", userSevice.findAll());
        model.addAttribute("user", user);

        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        Role[] roles = new Role[1];
        roles[0] = Role.ADMIN;
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);

        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/save")
    public String saveUsers() {
        return "redirect:/user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {
        userSevice.saveUser(user, username, form);

        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("user", user);

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String email,
            Model model
    ) {
        if (!userSevice.updateProfile(user, oldPassword, newPassword, email)) {
            model.addAttribute("username", user.getUsername());
            model.addAttribute("oldPasswordError", "Invalid old password");
            return "profile";
        }

        return "redirect:/home";
    }

    @GetMapping("subscribe/{user}")
    public String subscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user
    ) {
        userSevice.subscribe(currentUser, user);

        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("unsubscribe/{user}")
    public String unsubscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user
    ) {
        userSevice.unsubscribe(currentUser, user);

        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("{type}/{user}/list")
    public String userList(
            Model model,
            @PathVariable User user,
            @PathVariable String type
    ) {
        model.addAttribute("userChannel", user);
        model.addAttribute("type", type);

        if ("subscriptions".equals(type)) {
            model.addAttribute("users", user.getSubscriptions());
        } else {
            model.addAttribute("users", user.getSubscribers());
        }

        return "subscriptions";
    }
}
