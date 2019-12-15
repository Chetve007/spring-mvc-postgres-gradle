package ru.alex.project.springapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alex.project.springapp.entities.Message;
import ru.alex.project.springapp.entities.User;
import ru.alex.project.springapp.repositories.MessageRepo;

import java.util.Map;

@Controller
public class MainPageController {

    @Autowired
    private MessageRepo messageRepo;

//    @GetMapping("/")
//    public String getMainPage(@RequestParam(name="name", required = false, defaultValue = "Cyka") String name,
//                              Map<String, Object> model) {
//        model.put("name", name);
//        return "greeting";
//    }

    @GetMapping("/")
    public String getMainPage() {
        return "greeting";
    }

    @GetMapping("/main")
    public String getMainPage(Map<String, Object> model) {
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            Map<String, Object> model)
    {
        Message message = new Message(text, tag, user);
        messageRepo.save(message);
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Message> messages;

        if (filter != null && !filter.isEmpty())
            messages = messageRepo.findByTag(filter);
        else
            messages = messageRepo.findAll();

        model.put("messages", messages);
        return "main";
    }
}