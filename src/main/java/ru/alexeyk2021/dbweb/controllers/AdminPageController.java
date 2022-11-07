package ru.alexeyk2021.dbweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.alexeyk2021.dbweb.models.Client;
import ru.alexeyk2021.dbweb.models.ClientPersonalInfo;
import ru.alexeyk2021.dbweb.models.Tariff;
import ru.alexeyk2021.dbweb.transfer.PageSettings;

import java.util.ArrayList;

@Controller
public class AdminPageController {
    private PageSettings pageSettings;

    public AdminPageController() {
        pageSettings = new PageSettings();
    }

    @GetMapping("/admin")
    public String admin() {
        return "redirect:/admin/stats";
    }

    @GetMapping("/admin/clients")
    public String clients_settings(Model model) {
        pageSettings.setClients();
        ArrayList<Client> clientsList = new ArrayList<>();
        clientsList.add(new Client(1, 150, "+79123456789", true, new Tariff(1,"Test1", 150.0,"test1",30.0,30,30),   new ClientPersonalInfo(1,"test1 test1 test1","4500 000000","test1@mail.ru","testetates"), null, null));
        clientsList.add(new Client(2, 300, "+79461376351", true, new Tariff(2,"Test2", 200,"test2",15.0,15,15),     new ClientPersonalInfo(1,"test2 test2 test2","4500 594412","test2@mail.ru","testetates"), null, null));
        clientsList.add(new Client(3, -50, "+79261524546", false, new Tariff(3,"Test3", 300.0,"test3",10.0,10,10),  new ClientPersonalInfo(1,"test3 test3 test3","4500 646196","test3@mail.ru","testetates"), null, null));
        model.addAttribute("pageSettings", pageSettings);
        model.addAttribute("clients_list", clientsList);
        return "admin_page";
    }

    @GetMapping("/admin/tariffs")
    public String tariffs_settings(Model model) {
        pageSettings.setTariffs();
        model.addAttribute("pageSettings", pageSettings);
        return "admin_page";
    }

    @GetMapping("/admin/adds")
    public String adds_settings(Model model) {
        pageSettings.setAdds();
        model.addAttribute("pageSettings", pageSettings);
        return "admin_page";
    }

    @GetMapping("/admin/stats")
    public String stats(Model model) {
        pageSettings.setStats();
        model.addAttribute("pageSettings", pageSettings);
        return "admin_page";
    }
}
