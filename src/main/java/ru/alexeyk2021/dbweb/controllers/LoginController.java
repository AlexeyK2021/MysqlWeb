package ru.alexeyk2021.dbweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.alexeyk2021.dbweb.transfer.LoginForm;


@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userForm", new LoginForm());
        return "login_page";
    }

    @PostMapping("/client_login")
    public String clientLogin(@ModelAttribute("userForm") LoginForm loginForm, BindingResult bindingResult, Model model) {
        //if (LoginManager.getInstance().enter(user.getUsername(), user.getPassword()))
        System.out.println(loginForm.toString());
        if (loginForm.getUsername().equals("admin@admin.ru") && loginForm.getPassword().equals("admin1234"))
            return "redirect:/admin/stats";
        return "redirect:/client";
    }


}
