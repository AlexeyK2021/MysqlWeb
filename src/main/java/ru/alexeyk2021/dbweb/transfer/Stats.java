package ru.alexeyk2021.dbweb.transfer;

import ru.alexeyk2021.dbweb.managers.DbManager;
import ru.alexeyk2021.dbweb.models.AddService;
import ru.alexeyk2021.dbweb.models.Client;
import ru.alexeyk2021.dbweb.models.Tariff;

import java.util.ArrayList;

public class Stats {
    private int clientQuantity;
    private int activeClients;
    private int inactiveClients;

    private int tariffQuantity;
    private Tariff popularTariff;

    private int addsQuantity;
    private AddService popularAdd;

    public Stats() {
        this.clientQuantity = DbManager.getInstance().getClientsCount();
        this.activeClients = DbManager.getInstance().getActiveClientsCount();
        this.inactiveClients = DbManager.getInstance().getClientsCount() - activeClients;
        this.tariffQuantity = DbManager.getInstance().getTariffsCount();
        this.addsQuantity = DbManager.getInstance().getAddsCount();
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
        clientQuantity = clientsList.size();
        tariffQuantity = tariffsList.size();
        addsQuantity = addsList.size();

        activeClients = 0;
        for (Client c: clientsList) {
            if(c.getAccountState()) activeClients++;
        }
        inactiveClients = clientQuantity - activeClients;
    }

    public int getClientQuantity() {
        return clientQuantity;
    }


    public int getActiveClients() {
        return activeClients;
    }

    public String getActiveClientsPers() {
        return (int) (activeClients * 100.0 / clientQuantity) + "%";
    }


    public int getInactiveClients() {
        return inactiveClients;
    }

    public String getInactiveClientsPers() {
        return (int) (inactiveClients * 100.0 / clientQuantity) + "%";
    }


    public int getTariffQuantity() {
        return tariffQuantity;
    }

    public ArrayList<String> getPopularTariffNames() {
        ArrayList<String> tariffs = new ArrayList<>();
        ArrayList<String> allTariffs = DbManager.getInstance().getPopularTariffs();
        for (int i = 0; i < 3; i++){
            tariffs.add(allTariffs.get(i));
        }
        return tariffs;
    }


    public int getAddsQuantity() {
        return addsQuantity;
    }


    public ArrayList<String> getPopularAddNames() {
        ArrayList<String> adds = new ArrayList<>();
        ArrayList<String> allAdds = DbManager.getInstance().getPopularAdds();
        for (int i = 0; i < 3; i++){
            adds.add(allAdds.get(i));
        }
        return adds;
    }
}
