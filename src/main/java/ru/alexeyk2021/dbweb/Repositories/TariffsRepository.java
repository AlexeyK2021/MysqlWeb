package ru.alexeyk2021.dbweb.Repositories;

import ru.alexeyk2021.dbweb.managers.DbManager;
import ru.alexeyk2021.dbweb.models.Client;
import ru.alexeyk2021.dbweb.models.Tariff;

import java.util.ArrayList;

public class TariffsRepository {
    private ArrayList<Tariff> tariffs;

    public TariffsRepository() {
        tariffs = DbManager.getInstance().getAllTariffs();
    }

    public ArrayList<Tariff> getTariffs() {
        return tariffs;
    }

    public ArrayList<Tariff> findByPartName(String name) {
        ArrayList<Tariff> newTariffs = new ArrayList<>();
        for (Tariff t : tariffs) {
            if (t.getName().toLowerCase().contains(name.toLowerCase())) newTariffs.add(t);
        }
        return newTariffs;
    }

    public Tariff findByName(String name) {
        Tariff newTariff = null;
        for (Tariff t : tariffs) {
            if (t.getName().toLowerCase().equals(name.toLowerCase())) newTariff = t;
        }
        return newTariff;
    }
}
