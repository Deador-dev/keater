package com.deador.keater.controller;

import com.deador.keater.entity.User;
import com.deador.keater.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
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
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        User userToDB = modelMapper
                .map(user, User.class);
        userRepository.save(userToDB);

        return "redirect:/user";
    }
}
