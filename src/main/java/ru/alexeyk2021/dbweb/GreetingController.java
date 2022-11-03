package ru.alexeyk2021.dbweb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetingController {

    @GetMapping("/hello")
    public String hello(Model model){
        model.addAttribute("name", "World");
        return "greeting";
    }

    @GetMapping("/login")
    public String login(){
        return "login_page";
    }
}
