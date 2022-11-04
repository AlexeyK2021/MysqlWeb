package ru.alexeyk2021.dbweb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminPage {
    @GetMapping("/admin/clients")
    public String clients_settings(Model model) {
        return "admin_clients";
    }

    @GetMapping("/admin/tariffs")
    public String tariffs_settings() {
        return "admin_tariffs";
    }

    @GetMapping("/admin/adds")
    public String adds_settings() {
        return "admin_adds";
    }

    @GetMapping("/admin/stats")
    public String stats() {
        return "admin_stats";
    }
}
