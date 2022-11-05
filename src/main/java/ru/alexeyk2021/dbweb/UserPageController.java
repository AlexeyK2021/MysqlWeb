package ru.alexeyk2021.dbweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserPageController {

    @GetMapping("/client")
    public String client(){
        return "user_page";
    }
}
