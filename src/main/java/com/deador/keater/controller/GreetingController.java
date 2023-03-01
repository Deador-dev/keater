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
public class GreetingController {
    private final MessageRepository messageRepository;

    @Autowired
    public GreetingController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                           Model model) {
        model.addAttribute("name", name);
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
