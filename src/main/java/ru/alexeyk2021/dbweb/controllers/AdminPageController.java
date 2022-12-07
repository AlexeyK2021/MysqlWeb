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
        if (LoginManager.getInstance().isAdminIsLogged()) {
            return "redirect:/admin/stats";
        }
        return "redirect:/login";
    }

    ////NAVBAR////
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

    @GetMapping("/admin/clients")
    public String clients_settings(Model model) {
        if (LoginManager.getInstance().isAdminIsLogged()) {
            updateClientsList();
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
            updateTariffsList();
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
            updateAddsList();
            pageSettings.setAdds();
            model.addAttribute("pageSettings", pageSettings);
            model.addAttribute("adds_list", addsList);
            model.addAttribute("findForm", new FindForm());
            return "admin_page";
        }
        return "redirect:/login";
    }

    ////CLIENT////
    @PostMapping("/admin/clients/find")
    public String findClient(@ModelAttribute("findForm") FindForm findForm, BindingResult bindingResult, Model model) {
        clientsList = clientRepository.findByPartPhoneNumber(findForm.getFindInfo());
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

    @PostMapping("/admin/clients/new")
    public String createClient(@ModelAttribute("createUser") CreateClient client, Model model) {
        Tariff tariff = tariffsRepository.findByName(client.getTariff());
        client.setTariffId(tariff.getTariffId());

        client.setAddsIds(addsRepository.getIdsByNames(client.getAdds()));

        if (!client.getPassword().isEmpty())
            client.setPassword(HashController.hash(client.getPassword()));

        clientRepository.createClient(client);
        updateClientsList();
        return "redirect:/admin/clients";
    }

    @GetMapping("/admin/clients/edit")
    public String editClientPage(Model model, @RequestParam("phone") String phone) {
        Client client = clientRepository.findByPhoneNumber("+" + phone);
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

        if (!client.getPassword().isEmpty())
            client.setPassword(HashController.hash(client.getPassword()));

        clientRepository.editClient(client);
        updateClientsList();
        return "redirect:/admin/clients";
    }

    @GetMapping("/admin/clients/del")
    public String deleteClient(Model model, @RequestParam("phone") String phone) {
        clientRepository.deleteClient("+" + phone);
        updateClientsList();
        return "redirect:/admin/clients";
    }

    ////TARIFF////
    @PostMapping("/admin/tariffs/find")
    public String findTariff(@ModelAttribute("findForm") FindForm findForm, BindingResult bindingResult, Model model) {
        tariffsList = tariffsRepository.findByPartName(findForm.getFindInfo());
        return "redirect:/admin/tariffs";
    }

    @GetMapping("/admin/tariffs/new")
    public String newTariff(Model model) {
        model.addAttribute("createTariff", new Tariff());
        return "create_tariff";
    }

    @PostMapping("/admin/tariffs/new")
    public String createTariff(@ModelAttribute("createTariff") Tariff tariff, Model model) {
        if (!(tariff.getCost() < 0 || tariff.getInternetSize() < 0 || tariff.getMinutesSize() < 0 || tariff.getSmsSize() < 0))
            tariffsRepository.newTariff(tariff);
        updateTariffsList();
        return "redirect:/admin/tariffs";
    }

    @GetMapping("/admin/tariffs/edit")
    public String editTariffPage(Model model, @RequestParam("id") int id) {
        Tariff editTariff = tariffsRepository.findById(id);
        model.addAttribute("editTariff", editTariff);
        return "parts/edit_tariff";
    }

    @PostMapping("/admin/tariffs/edit")
    public String editTariffData(@ModelAttribute("editTariff") Tariff tariff, BindingResult bindingResult, Model model) {
        if (!(tariff.getCost() < 0 || tariff.getInternetSize() < 0 || tariff.getMinutesSize() < 0 || tariff.getSmsSize() < 0))
            tariffsRepository.editTariff(tariff);
        updateTariffsList();
        return "redirect:/admin/tariffs";
    }

    @GetMapping("/admin/tariffs/del")
    public String deleteTariff(Model model, @RequestParam("id") int id) {
        tariffsRepository.deleteTariff(id);
        updateTariffsList();
        return "redirect:/admin/tariffs";
    }

    ////ADDS
    @PostMapping("/admin/adds/find")
    public String findAdd(@ModelAttribute("findForm") FindForm findForm, BindingResult bindingResult, Model model) {
        addsList = addsRepository.findByPartName(findForm.getFindInfo());
        return "redirect:/admin/adds";
    }

    @GetMapping("/admin/adds/new")
    public String newAddService(Model model) {
        model.addAttribute("createAdd", new AddService());
        return "parts/create_add";
    }

    @PostMapping("/admin/adds/new")
    public String createAddService(@ModelAttribute("createAdd") AddService add, Model model) {
        if (!(add.getCost() < 0 || add.getInternetSize() < 0 || add.getMinutesSize() < 0 || add.getSmsSize() < 0))
            addsRepository.newAddService(add);
        updateAddsList();
        return "redirect:/admin/adds";
    }

    @GetMapping("/admin/adds/edit")
    public String editAddServicePage(Model model, @RequestParam("id") int id) {
        AddService addService = addsRepository.findById(id);
        model.addAttribute("editAdd", addService);
        return "parts/edit_add";
    }

    @PostMapping("/admin/adds/edit")
    public String editAddServiceData(@ModelAttribute("editAdd") AddService add, BindingResult bindingResult, Model model) {
        if (!(add.getCost() < 0 || add.getInternetSize() < 0 || add.getMinutesSize() < 0 || add.getSmsSize() < 0))
            addsRepository.editAddService(add);
        updateAddsList();
        return "redirect:/admin/adds";
    }

    @GetMapping("/admin/adds/del")
    public String deleteAddService(Model model, @RequestParam("id") int id) {
        addsRepository.deleteAdd(id);
        updateAddsList();
        return "redirect:/admin/adds";
    }

    @GetMapping("/admin/logout")
    public String logout() {
        LoginManager.getInstance().exit();
        return "redirect:/login";
    }

    private void updateTariffsList() {
        tariffsRepository.updateTariffsList();
        tariffsList = tariffsRepository.getTariffs();
    }

    private void updateClientsList() {
        clientRepository.updateClientsList();
        clientsList = clientRepository.getClients();
    }


    private void updateAddsList() {
        addsRepository.updateAddsList();
        addsList = addsRepository.getAdds();
    }
}
