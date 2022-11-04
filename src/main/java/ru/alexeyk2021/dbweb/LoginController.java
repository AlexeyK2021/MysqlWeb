package ru.alexeyk2021.dbweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(){
        return "login_page";
    }

    @GetMapping("/client")
    public String clientLogin(){
        return "user_page";
    }

    @GetMapping("/admin")
    public String adminLogin(){
        return "admin_stats";
    }

}
