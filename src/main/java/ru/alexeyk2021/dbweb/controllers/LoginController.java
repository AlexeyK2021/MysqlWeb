package ru.alexeyk2021.dbweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.alexeyk2021.dbweb.managers.LoginManager;
import ru.alexeyk2021.dbweb.transfer.LoginForm;


@Controller
public class LoginController {

    @GetMapping("/")
    public String home(Model model){
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userForm", new LoginForm());
        return "login_page";
    }

    @PostMapping("/client_login")
    public String clientLogin(@ModelAttribute("userForm") LoginForm loginForm, BindingResult bindingResult, Model model) {
        if(LoginManager.getInstance().enterAsAdmin(loginForm.getUsername(), loginForm.getPassword())) return "redirect:/admin/stats";
        else if(LoginManager.getInstance().enter(loginForm.getUsername(), loginForm.getPassword())) return "redirect:/client";
        return "redirect:/admin";
    }

    @GetMapping("/logout")
    public String logout(){
        LoginManager.getInstance().exit();
        return "redirect:/login";
    }

}
