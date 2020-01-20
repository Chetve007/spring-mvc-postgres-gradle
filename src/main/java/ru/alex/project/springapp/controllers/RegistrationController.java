package ru.alex.project.springapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alex.project.springapp.entities.User;
import ru.alex.project.springapp.service.UserService;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String passConfirm,
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {
        boolean isEmptyPass = StringUtils.isEmpty(passConfirm);
        if (isEmptyPass)
            model.addAttribute("password2Error", "Password confirmation cannot be empty!");

        if (user.getPassword() != null && !user.getPassword().equals(passConfirm))
            model.addAttribute("passwordError", "There are different passwords!");

        if (isEmptyPass || bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }

        if (! userService.addUser(user))  {
            model.addAttribute("usernameError", "User already exists!");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "User successfully activated");
            model.addAttribute("messageType", "success");
        } else {
            model.addAttribute("message", "Activation code is not found");
            model.addAttribute("messageType", "danger");
        }


        return "login";
    }
}