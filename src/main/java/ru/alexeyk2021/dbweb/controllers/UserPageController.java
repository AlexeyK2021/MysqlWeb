package ru.alexeyk2021.dbweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alexeyk2021.dbweb.Repositories.AddsRepository;
import ru.alexeyk2021.dbweb.Repositories.ClientRepository;
import ru.alexeyk2021.dbweb.Repositories.TariffsRepository;
import ru.alexeyk2021.dbweb.managers.LoginManager;
import ru.alexeyk2021.dbweb.models.AddService;
import ru.alexeyk2021.dbweb.models.Client;
import ru.alexeyk2021.dbweb.models.Tariff;
import ru.alexeyk2021.dbweb.transfer.AddFund;
import ru.alexeyk2021.dbweb.transfer.EditingClient;
import ru.alexeyk2021.dbweb.transfer.FindForm;
import ru.alexeyk2021.dbweb.transfer.PageSettings;

import java.util.ArrayList;

@Controller
public class UserPageController {
    private PageSettings pageSettings;

    private ArrayList<Client> clientsList;
    private ArrayList<Tariff> tariffsList;
    private ArrayList<AddService> addsList;
    private TariffsRepository tariffsRepository;
    private ClientRepository clientRepository;
    private AddsRepository addsRepository;

    public UserPageController() {
        tariffsRepository = new TariffsRepository();
        clientRepository = new ClientRepository();
        addsRepository = new AddsRepository();

        clientsList = clientRepository.getClients();
        tariffsList = tariffsRepository.getTariffs();
        addsList = addsRepository.getAdds();
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
            model.addAttribute("client_info", LoginManager.getInstance().getCurrentUser());
            model.addAttribute("pageSettings", pageSettings);
            model.addAttribute("addFund", new AddFund());
            return "user_page";
        }
        return "redirect:/login";
    }

    @GetMapping("/client/tariffs")
    public String tariffs_settings(Model model) {
        if (LoginManager.getInstance().getCurrentUser() != null) {
            pageSettings.setTariffs();
            model.addAttribute("client_info", LoginManager.getInstance().getCurrentUser());
            model.addAttribute("findForm", new FindForm());
            model.addAttribute("pageSettings", pageSettings);
            model.addAttribute("tariffs_list", tariffsList);
            return "user_page";
        }
        return "redirect:/login";
    }

    @GetMapping("/client/tariffs/con")
    public String connectTariff(@RequestParam("id") int id, Model model) {
        EditingClient ec = new EditingClient(LoginManager.getInstance().getCurrentUser(), addsRepository, tariffsRepository);
        ec.setTariffId(id);
        ec.setPassword(LoginManager.getInstance().getCurrentUser().getPersonalInfo().getPassword());
        clientRepository.editClient(ec);
        updateClientsList();
        return "redirect:/client/tariffs";
    }

    @GetMapping("/client/adds")
    public String adds_settings(Model model) {
        if (LoginManager.getInstance().getCurrentUser() != null) {
            pageSettings.setAdds();
            model.addAttribute("client_info", LoginManager.getInstance().getCurrentUser());
            model.addAttribute("findForm", new FindForm());
            model.addAttribute("pageSettings", pageSettings);
            model.addAttribute("adds_list", addsList);
            return "user_page";
        }
        return "redirect:/login";
    }

    @GetMapping("/client/adds/con")
    public String connectAdd(@RequestParam("id") int id, Model model) {
        EditingClient ec = new EditingClient(LoginManager.getInstance().getCurrentUser(), addsRepository, tariffsRepository);
        ec.addAddId(id);
        ec.setPassword(LoginManager.getInstance().getCurrentUser().getPersonalInfo().getPassword());
        clientRepository.editClient(ec);
        updateClientsList();
        return "redirect:/client/adds";
    }

    @GetMapping("/client/adds/discon")
    public String disconnectAdd(@RequestParam("id") int id, Model model) {
        EditingClient ec = new EditingClient(LoginManager.getInstance().getCurrentUser(), addsRepository, tariffsRepository);
        ec.removeAddId(id);
        ec.setPassword(LoginManager.getInstance().getCurrentUser().getPersonalInfo().getPassword());
        clientRepository.editClient(ec);
        updateClientsList();
        return "redirect:/client/adds";
    }

    @GetMapping("/client/personal")
    public String personal_settings(Model model) {
        if (LoginManager.getInstance().getCurrentUser() != null) {
            pageSettings.setPersonalInfo();
            model.addAttribute("client_info", LoginManager.getInstance().getCurrentUser());
            model.addAttribute("pageSettings", pageSettings);
            return "user_page";
        }
        return "redirect:/login";
    }

    @PostMapping("/client/addFunds")
    public String addFunds(@ModelAttribute("editingClient") AddFund addFund, BindingResult bindingResult, Model model) {
        if(addFund.getFund() > 0)
            clientRepository.addFunds(addFund, LoginManager.getInstance().getCurrentUser());
        updateClientsList();
        return "redirect:/client/home";
    }

    private void updateTariffsList() {
        tariffsRepository.updateTariffsList();
        tariffsList = tariffsRepository.getTariffs();
    }

    private void updateClientsList() {
        clientRepository.updateClientsList();
        clientsList = clientRepository.getClients();
        LoginManager.getInstance().updateCurrentUser();
    }

    private void updateAddsList() {
        addsRepository.updateAddsList();
        addsList = addsRepository.getAdds();
    }
}
