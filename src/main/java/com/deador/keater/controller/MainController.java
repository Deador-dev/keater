package com.deador.keater.controller;

import com.deador.keater.entity.Message;
import com.deador.keater.entity.User;
import com.deador.keater.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class MainController {
    private final MessageRepository messageRepository;
    public static String uploadPath = System.getProperty("user.dir") + "/src/main/resources/static/img/";

    @Autowired
    public MainController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String greeting(Model model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(
            @RequestParam(name = "filter", required = false, defaultValue = "") String filter,
            Model model
    ) {
        if (filter != null && !filter.isEmpty()) {
            model.addAttribute("messages", messageRepository.findByTag(filter));
        } else {
            model.addAttribute("messages", messageRepository.findAll());
        }
        model.addAttribute("filter", filter);

        return "main";
    }

    @GetMapping("/main/new")
    public String getNew(@ModelAttribute(name = "message") Message message) {
        return "new";
    }

    @PostMapping("/main")
    public String createNewMessage(
            @AuthenticationPrincipal User user,
            @ModelAttribute(name = "message") Message message,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (!file.isEmpty()) {
            Path uploadDir = Paths.get(uploadPath);

            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));
            message.setFilename(resultFilename);
        } else {
            message.setFilename("unknown");
        }

        message.setAuthor(user);
        messageRepository.save(message);
        return "redirect:/main";
    }
}
