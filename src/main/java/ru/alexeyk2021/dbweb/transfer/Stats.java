package ru.alexeyk2021.dbweb.transfer;

import ru.alexeyk2021.dbweb.models.AddService;
import ru.alexeyk2021.dbweb.models.Client;
import ru.alexeyk2021.dbweb.models.Tariff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Stats {
    private int clientQuantity;
    private int activeClients;
    private int inactiveClients;

    private int tariffQuantity;
    private Tariff popularTariff;

    private int addsQuantity;
    private AddService popularAdd;

    public Stats() {

    }

    public Stats(int clientQuantity, int activeClients, int inactiveClients, int tariffQuantity, Tariff popularTariff, int addsQuantity, AddService popularAdd) {
        this.clientQuantity = clientQuantity;
        this.activeClients = activeClients;
        this.inactiveClients = inactiveClients;
        this.tariffQuantity = tariffQuantity;
        this.popularTariff = popularTariff;
        this.addsQuantity = addsQuantity;
        this.popularAdd = popularAdd;
    }

    public Stats(ArrayList<Client> clientsList, ArrayList<Tariff> tariffsList, ArrayList<AddService> addsList) {
        //получить с БД таблицу популярных   [id_тарифа, count(client.tariff == this)]
//        clientQuantity = clientsList.size();
//        for (Client c : clientsList) {
//            if (c.getAccountState()) activeClients++;
//        }
//        inactiveClients = clientQuantity - activeClients;
//        tariffQuantity = tariffsList.size();
//
//
//        for(Map.Entry<Tariff, Integer> entry: popularTariffs(clientsList).entrySet()){
//
//        }
//
//    }
//
//    private HashMap<Tariff, Integer> popularTariffs(ArrayList<Client> clients) {
//        HashMap<Tariff, Integer> tariffsNum = new HashMap<>();
//        for (Client c : clients) {
//            if (tariffsNum.containsKey(c.getTariff())) tariffsNum.put(c.getTariff(), tariffsNum.get(c.getTariff()) + 1);
//            else tariffsNum.put(c.getTariff(), 1);
//        }
//        return tariffsNum;
    }

    public int getClientQuantity() {
        return clientQuantity;
    }

    public void setClientQuantity(int clientQuantity) {
        this.clientQuantity = clientQuantity;
    }

    public int getActiveClients() {
        return activeClients;
    }

    public String getActiveClientsPers() {
        return (int) (activeClients * 100.0 / clientQuantity) + "%";
    }

    public void setActiveClients(int activeClients) {
        this.activeClients = activeClients;
    }

    public int getInactiveClients() {
        return inactiveClients;
    }

    public String getInactiveClientsPers() {
        return (int) (inactiveClients * 100.0 / clientQuantity) + "%";
    }

    public void setInactiveClients(int inactiveClients) {
        this.inactiveClients = inactiveClients;
    }

    public int getTariffQuantity() {
        return tariffQuantity;
    }

    public void setTariffQuantity(int tariffQuantity) {
        this.tariffQuantity = tariffQuantity;
    }

    public Tariff getPopularTariff() {
        return popularTariff;
    }

    public void setPopularTariff(Tariff popularTariff) {
        this.popularTariff = popularTariff;
    }

    public int getAddsQuantity() {
        return addsQuantity;
    }

    public void setAddsQuantity(int addsQuantity) {
        this.addsQuantity = addsQuantity;
    }

    public AddService getPopularAdd() {
        return popularAdd;
    }

    public void setPopularAdd(AddService popularAdd) {
        this.popularAdd = popularAdd;
    }
}
