package com.deador.keater.controller;

import com.deador.keater.entity.Role;
import com.deador.keater.entity.User;
import com.deador.keater.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {
    private final UserRepository userRepository;

    @Autowired
    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user,
                          Model model) {
        User userFroDB = userRepository.findByUsername(user.getUsername());
        if (userFroDB != null) {
            model.addAttribute("message", "User exists!");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        // FIXME: 01.03.2023 userService.save()
        userRepository.save(user);
        return "redirect:/login";
    }
}
