package ru.alexeyk2021.dbweb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(Model model) {
//        model.addAttribute("userForm", new User());
        return "login_page";
    }

    @PostMapping("/client_login")
    public String clientLogin(@ModelAttribute("userForm") User user, BindingResult bindingResult, Model model) {
        //if (LoginManager.getInstance().enter(user.getUsername(), user.getPassword()))
        System.out.println(user.toString());
        return "user_page";
//        else return "login_page";
    }

//    @PostMapping("/client")
//    public String clientLogin(@ModelAttribute("userForm") User user, BindingResult bindingResult, Model model) {
//        //if (LoginManager.getInstance().enter(user.getUsername(), user.getPassword()))
//        System.out.println(user.toString());
//        return "user_page";
////        else return "login_page";
//    }

//    @GetMapping("/admin")
//    public String adminLogin() {
//        return "admin_stats";
//    }

}
