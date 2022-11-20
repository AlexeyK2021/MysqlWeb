package ru.alexeyk2021.dbweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.alexeyk2021.dbweb.managers.LoginManager;
import ru.alexeyk2021.dbweb.transfer.PageSettings;

@Controller
public class UserPageController {
    private PageSettings pageSettings;

    public UserPageController() {
        pageSettings = new PageSettings();
    }

    @GetMapping("/client")
    public String client() {
        if (LoginManager.getInstance().getCurrentUser() != null)
            return "redirect:/client/home";
        return "redirect:/login";
    }

    @GetMapping("/client/home")
    public String home(Model model) {
        if (LoginManager.getInstance().getCurrentUser() != null) {
            pageSettings.setMainPage();
            model.addAttribute("pageSettings", pageSettings);
            return "user_page";
        }
        return "redirect:/login";
    }

    @GetMapping("/client/tariffs")
    public String tariffs_settings(Model model) {
        if (LoginManager.getInstance().getCurrentUser() != null) {
            pageSettings.setTariffs();
            model.addAttribute("pageSettings", pageSettings);
            return "user_page";
        }
        return "redirect:/login";
    }

    @GetMapping("/client/adds")
    public String adds_settings(Model model) {
        if (LoginManager.getInstance().getCurrentUser() != null) {
            pageSettings.setAdds();
            model.addAttribute("pageSettings", pageSettings);
            return "user_page";
        }
        return "redirect:/login";
    }

    @GetMapping("/client/personal")
    public String personal_settings(Model model) {
        if (LoginManager.getInstance().getCurrentUser() != null) {
            pageSettings.setPersonalInfo();
            model.addAttribute("pageSettings", pageSettings);
            return "user_page";
        }
        return "redirect:/login";
    }
}
