package ru.alexeyk2021.dbweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.alexeyk2021.dbweb.managers.DbManager;
import ru.alexeyk2021.dbweb.models.AddService;
import ru.alexeyk2021.dbweb.models.Client;
import ru.alexeyk2021.dbweb.models.Tariff;
import ru.alexeyk2021.dbweb.transfer.PageSettings;
import ru.alexeyk2021.dbweb.transfer.Stats;

import java.util.ArrayList;

@Controller
public class AdminPageController {
    private final PageSettings pageSettings;
    private final ArrayList<Client> clientsList;
    private final ArrayList<Tariff> tariffsList;
    private final ArrayList<AddService> addsList;

    private final Stats stats;

    public AdminPageController() {
        clientsList = DbManager.getInstance().getAllClients();
        tariffsList = DbManager.getInstance().getAllTariffs();
        addsList = DbManager.getInstance().getAllAdds();
        stats = new Stats(clientsList, tariffsList, addsList);
        pageSettings = new PageSettings();
    }

    @GetMapping("/admin")
    public String admin() {
        return "redirect:/admin/stats";
    }

    @GetMapping("/admin/clients")
    public String clients_settings(Model model) {
        pageSettings.setClients();
        model.addAttribute("pageSettings", pageSettings);
        model.addAttribute("clients_list", clientsList);
        return "admin_page";
    }

    @GetMapping("/admin/tariffs")
    public String tariffs_settings(Model model) {
        pageSettings.setTariffs();
        model.addAttribute("pageSettings", pageSettings);
        model.addAttribute("tariffs_list", tariffsList);
        return "admin_page";
    }

    @GetMapping("/admin/adds")
    public String adds_settings(Model model) {
        pageSettings.setAdds();
        model.addAttribute("pageSettings", pageSettings);
        model.addAttribute("adds_list", addsList);
        return "admin_page";
    }

    @GetMapping("/admin/stats")
    public String stats(Model model) {
        pageSettings.setStats();
        model.addAttribute("pageSettings", pageSettings);
        model.addAttribute("stats", new Stats(6,5,1,5,new Tariff(1,"Test1", 150.0, "Test1 Test1", 15.0, 150, 150),5,new AddService(1,"Test1", 150.0, "Test1 Test1", 15.0, 150, 150)));
        return "admin_page";
    }
}
