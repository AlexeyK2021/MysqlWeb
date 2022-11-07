package ru.alexeyk2021.dbweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.alexeyk2021.dbweb.transfer.PageSettings;

@Controller
public class UserPageController {
    private PageSettings pageSettings;

    public UserPageController() {
        pageSettings = new PageSettings();
    }

    @GetMapping("/client")
    public String client(){
        return "redirect:/client/home";
    }
    @GetMapping("/client/home")
    public String home(Model model) {
        pageSettings.setMainPage();
        model.addAttribute("pageSettings", pageSettings);
        return "user_page";
    }

    @GetMapping("/client/tariffs")
    public String tariffs_settings(Model model) {
        pageSettings.setTariffs();
        model.addAttribute("pageSettings", pageSettings);
        return "user_page";
    }

    @GetMapping("/client/adds")
    public String adds_settings(Model model) {
        pageSettings.setAdds();
        model.addAttribute("pageSettings", pageSettings);
        return "user_page";
    }

    @GetMapping("/client/personal")
    public String personal_settings(Model model) {
        pageSettings.setPersonalInfo();
        model.addAttribute("pageSettings", pageSettings);
        return "user_page";
    }
}
