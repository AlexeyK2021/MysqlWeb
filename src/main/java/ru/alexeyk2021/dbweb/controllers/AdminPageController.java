package ru.alexeyk2021.dbweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alexeyk2021.dbweb.HashController;
import ru.alexeyk2021.dbweb.Repositories.AddsRepository;
import ru.alexeyk2021.dbweb.Repositories.ClientRepository;
import ru.alexeyk2021.dbweb.Repositories.TariffsRepository;
import ru.alexeyk2021.dbweb.managers.DbManager;
import ru.alexeyk2021.dbweb.managers.LoginManager;
import ru.alexeyk2021.dbweb.models.AddService;
import ru.alexeyk2021.dbweb.models.Client;
import ru.alexeyk2021.dbweb.models.Tariff;
import ru.alexeyk2021.dbweb.transfer.*;

import java.util.ArrayList;

@Controller
public class AdminPageController {
    private PageSettings pageSettings;
    private ArrayList<Client> clientsList;
    private ArrayList<Tariff> tariffsList;
    private ArrayList<AddService> addsList;

    private TariffsRepository tariffsRepository;
    private ClientRepository clientRepository;
    private AddsRepository addsRepository;

    private Stats stats;

    public AdminPageController() {
        tariffsRepository = new TariffsRepository();
        clientRepository = new ClientRepository();
        addsRepository = new AddsRepository();

        clientsList = clientRepository.getClients();
        tariffsList = tariffsRepository.getTariffs();
        addsList = addsRepository.getAdds();

        stats = new Stats(clientRepository.getClients(), tariffsRepository.getTariffs(), addsRepository.getAdds());
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
            model.addAttribute("stats", new Stats());
            return "admin_page";
        }
        return "redirect:/login";
    }

    @PostMapping("/admin/clients/find")
    public String findClient(@ModelAttribute("findForm") FindForm findForm, BindingResult bindingResult, Model model) {
        clientsList = clientRepository.findByPartPhoneNumber(findForm.getFindInfo());
        return "redirect:/admin/clients";
    }

    @PostMapping("/admin/tariffs/find")
    public String findTariff(@ModelAttribute("findForm") FindForm findForm, BindingResult bindingResult, Model model) {
        tariffsList = tariffsRepository.findByPartName(findForm.getFindInfo());
        return "redirect:/admin/tariffs";
    }

    @PostMapping("/admin/adds/find")
    public String findAdd(@ModelAttribute("findForm") FindForm findForm, BindingResult bindingResult, Model model) {
        addsList = addsRepository.findByPartName(findForm.getFindInfo());
        return "redirect:/admin/adds";
    }

    @GetMapping("/admin/clients/edit")
    public String editClientPage(Model model, @RequestParam("phone") String phone) {
        Client client = clientRepository.findByPhoneNumber(phone);
        EditingClient ec = new EditingClient(client, addsRepository);

        model.addAttribute("client", client);
        model.addAttribute("tariffs", tariffsList);
        model.addAttribute("adds", addsList);
        model.addAttribute("editUser", ec);
        return "parts/edit_client";
    }

    @PostMapping("/admin/clients/edit")
    public String editClientData(@ModelAttribute("editUser") EditingClient client, BindingResult bindingResult, Model model) {
        Tariff tariff = tariffsRepository.findByName(client.getTariff());
        client.setTariffId(tariff.getTariffId());

        client.setAddsIds(addsRepository.getIdsByNames(client.getAdds()));

        if (!client.getPassport().isEmpty())
            client.setPassport(HashController.hash(client.getPassword()));

//        DbManager.getInstance().editClient(client);
        clientRepository.editClient(client);
        return "redirect:/admin/clients";
    }

    @GetMapping("/admin/clients/new")
    public String newClient(Model model) {
        ArrayList<String> phones = DbManager.getInstance().getFreePhoneNumbers();
        model.addAttribute("createUser", new CreateClient());
        model.addAttribute("tariffs", tariffsList);
        model.addAttribute("adds", addsList);
        model.addAttribute("phoneNumbers", phones);
        return "parts/create_client";
    }

    @PostMapping("/admin/clients/new") // if dont work was /create and in html too
    public String createClient(@ModelAttribute("createUser") CreateClient client, Model model) {
        Tariff tariff = tariffsRepository.findByName(client.getTariff());
        client.setTariffId(tariff.getTariffId());

        client.setAddsIds(addsRepository.getIdsByNames(client.getAdds()));

        if (!client.getPassword().isEmpty())
            client.setPassword(HashController.hash(client.getPassword()));

//        DbManager.getInstance().newClient(client);
        clientRepository.createClient(client);
        updateClientsList();
        return "redirect:/admin/clients";
    }

    private void updateClientsList(){
        clientRepository.updateClientsList();
        clientsList = clientRepository.getClients();
    }

    private void updateTariffsList(){
        tariffsList = tariffsRepository.getTariffs();
    }

    private void updateAddsList(){
        addsList = addsRepository.getAdds();
    }
}
