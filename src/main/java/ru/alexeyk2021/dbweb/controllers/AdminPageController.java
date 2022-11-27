package ru.alexeyk2021.dbweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alexeyk2021.dbweb.managers.DbManager;
import ru.alexeyk2021.dbweb.managers.LoginManager;
import ru.alexeyk2021.dbweb.models.AddService;
import ru.alexeyk2021.dbweb.models.Client;
import ru.alexeyk2021.dbweb.models.Tariff;
import ru.alexeyk2021.dbweb.transfer.FindForm;
import ru.alexeyk2021.dbweb.transfer.PageSettings;
import ru.alexeyk2021.dbweb.transfer.Stats;

import java.util.ArrayList;

@Controller
public class AdminPageController {
    private PageSettings pageSettings;
    private ArrayList<Client> clientsList;
    private ArrayList<Tariff> tariffsList;
    private ArrayList<AddService> addsList;

    private Stats stats;

    public AdminPageController() {
        clientsList = DbManager.getInstance().getAllClients();
        tariffsList = DbManager.getInstance().getAllTariffs();
        addsList = DbManager.getInstance().getAllAdds();
        stats = new Stats(clientsList, tariffsList, addsList);
        pageSettings = new PageSettings();
    }

    @GetMapping("/admin")
    public String admin() {
        if (LoginManager.getInstance().isAdminIsLogged())
            return "redirect:/admin/stats";
        return "redirect:/login";
    }

    @GetMapping("/admin/clients")
    public String clients_settings(Model model) {
        if (LoginManager.getInstance().isAdminIsLogged()) {
            pageSettings.setClients();
            model.addAttribute("pageSettings", pageSettings);
            model.addAttribute("clients_list", clientsList);
            model.addAttribute("findForm", new FindForm());
            return "admin_page";
        }
        return "redirect:/login";
    }

    @GetMapping("/admin/tariffs")
    public String tariffs_settings(Model model) {
        if (LoginManager.getInstance().isAdminIsLogged()) {
            pageSettings.setTariffs();
            model.addAttribute("pageSettings", pageSettings);
            model.addAttribute("tariffs_list", tariffsList);
            model.addAttribute("findForm", new FindForm());
            return "admin_page";
        }
        return "redirect:/login";
    }

    @GetMapping("/admin/adds")
    public String adds_settings(Model model) {
        if (LoginManager.getInstance().isAdminIsLogged()) {
            pageSettings.setAdds();
            model.addAttribute("pageSettings", pageSettings);
            model.addAttribute("adds_list", addsList);
            model.addAttribute("findForm", new FindForm());
            return "admin_page";
        }
        return "redirect:/login";
    }

    @GetMapping("/admin/stats")
    public String stats(Model model) {
        if (LoginManager.getInstance().isAdminIsLogged()) {
            pageSettings.setStats();
            model.addAttribute("pageSettings", pageSettings);
            DbManager dbManager = DbManager.getInstance();
            model.addAttribute("stats", new Stats());
            return "admin_page";
        }
        return "redirect:/login";
    }

    @PostMapping("/admin/clients/find")
    public String findClient(@ModelAttribute("findForm") FindForm findForm, BindingResult bindingResult, Model model) {
        ArrayList<String> phones = DbManager.getInstance().findByPartNumber(findForm.getFindInfo());
        clientsList.clear();
        for (String p : phones) {
            clientsList.add(DbManager.getInstance().findByPhoneNumber(p));
        }
        return "redirect:/admin/clients";
    }

    @PostMapping("/admin/tariffs/find")
    public String findTariff(@ModelAttribute("findForm") FindForm findForm, BindingResult bindingResult, Model model) {
        ArrayList<Tariff> tariffs = DbManager.getInstance().getAllTariffs();
        tariffsList.clear();
        for (Tariff t : tariffs) {
            if (t.getName().toLowerCase().contains(findForm.getFindInfo().toLowerCase()))
                tariffsList.add(t);
        }
        return "redirect:/admin/tariffs";
    }

    @PostMapping("/admin/adds/find")
    public String findAdd(@ModelAttribute("findForm") FindForm findForm, BindingResult bindingResult, Model model) {
        ArrayList<AddService> adds = DbManager.getInstance().getAllAdds();
        addsList.clear();
        for (AddService a : adds) {
            if (a.getName().toLowerCase().contains(findForm.getFindInfo().toLowerCase()))
                addsList.add(a);
        }
        return "redirect:/admin/adds";
    }

    @GetMapping ( "/admin/clients/edit")
    public String editClient(Model model, @RequestParam("id") String phone) {
        System.out.println(phone);
        model.addAttribute("data", "+" + phone);
        return "parts/edit_client";
    }
}
