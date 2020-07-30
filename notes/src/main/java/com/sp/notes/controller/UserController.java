package com.sp.notes.controller;

import com.sp.notes.model.User;
import com.sp.notes.service.UserService;
import com.sp.utils.ModelManager;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("entity", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid User user, BindingResult bindingResult, Model model) {
        String message = null, error = null, output = null;
        if (bindingResult.hasErrors()) {
            model.addAttribute("entity", user);
            return "registration";
        }
        output = userService.save(user);
        switch (output) {
            case "11":
                message = "Registration successful. You can login now";
                break;
            case "01":
                error = "Username already taken";
                break;
            case "00":
                System.out.println("Problem occured inserting data into role table");
                error = "Server is currently down. Please try again later";
        }
        ModelManager.formModel(model, null, new User(), message, error);
        return (output.equals(11)) ? "registration" : "login";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Your username and password is invalid.");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        model.addAttribute("entity", new User());
        return "login";
    }

    @GetMapping(value = {"/", "/welcome"})
    public String welcome(Model model) {
        return "redirect:/note/";
    }
}
