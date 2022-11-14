package ru.alexeyk2021.dbweb.transfer;

import ru.alexeyk2021.dbweb.models.AddService;
import ru.alexeyk2021.dbweb.models.Tariff;

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
        return (int)(activeClients * 100.0 / clientQuantity) + "%";
    }

    public void setActiveClients(int activeClients) {
        this.activeClients = activeClients;
    }

    public int getInactiveClients() {
        return inactiveClients;
    }

    public String getInactiveClientsPers() {
        return (int)(inactiveClients * 100.0 / clientQuantity) + "%";
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
