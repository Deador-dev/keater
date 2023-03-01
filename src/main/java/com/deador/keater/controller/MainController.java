package com.deador.keater.controller;

import com.deador.keater.entity.Message;
import com.deador.keater.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    private final MessageRepository messageRepository;

    @Autowired
    public MainController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String greeting(Model model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        return "main";
    }

    @GetMapping("/main/new")
    public String getNew(@ModelAttribute(name = "message") Message message) {
        return "new";
    }

    @PostMapping("/main")
    public String createNew(@ModelAttribute(name = "message") Message message) {
        // TODO: 01.03.2023 messageService.save()
        messageRepository.save(message);
        return "redirect:/main";
    }

    @GetMapping("filter")
    public String filter(@RequestParam(name = "filter") String tag,
                         Model model) {
        if (tag != null && !tag.isEmpty()) {
            model.addAttribute("messages", messageRepository.findByTag(tag));
        } else {
            model.addAttribute("messages", messageRepository.findAll());
        }

        return "/main";
    }
}
