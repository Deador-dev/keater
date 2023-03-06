package com.deador.keater.controller;

import com.deador.keater.entity.User;
import com.deador.keater.entity.dto.CaptchaResponseDTO;
import com.deador.keater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final RestTemplate restTemplate;
    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    public RegistrationController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("g-recaptcha-response") String captchaResponse,
            User user,
            Model model) {
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDTO response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDTO.class);

        if (!response.getSuccess()) {
            model.addAttribute("captchaError", "Fill captcha");
            return "registration";
        }

        if (!userService.addUser(user)) {

            model.addAttribute("message", "User exists!");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(
            @PathVariable(name = "code") String code,
            Model model) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("message", "User successfully activated!");
        } else {
            model.addAttribute("message", "Activation code is not found!");
        }
        return "login";
    }
}
