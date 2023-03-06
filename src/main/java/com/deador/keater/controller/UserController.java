package com.deador.keater.controller;

import com.deador.keater.entity.User;
import com.deador.keater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @GetMapping("/{user}")
    public String userEditForm(@PathVariable(name = "user") User user,
                               Model model) {
        model.addAttribute("user", user);
        return "userEdit";
    }

    @PostMapping
    public String updateUser(@ModelAttribute(name = "user") User user) {
        userService.saveUser(user);

        return "redirect:/user";
    }

    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal User user,
                             Model model) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@AuthenticationPrincipal User user,
                                @RequestParam("password") String password,
                                @RequestParam("email") String email){
        userService.updateProfile(user, password, email);
        return "redirect:/user/profile";
    }

}
