package ru.alexeyk2021.dbweb.Repositories;

import ru.alexeyk2021.dbweb.managers.DbManager;
import ru.alexeyk2021.dbweb.models.AddService;
import ru.alexeyk2021.dbweb.models.Tariff;

import java.util.ArrayList;
import java.util.List;

public class AddsRepository {
    private ArrayList<AddService> adds;

    public AddsRepository() {
        adds = DbManager.getInstance().getAllAdds();
    }

    public ArrayList<AddService> getAdds() {
        return adds;
    }

    public ArrayList<AddService> findByPartName(String name) {
        ArrayList<AddService> newAdds = new ArrayList<>();
        for (AddService a : adds) {
            if (a.getName().toLowerCase().contains(name.toLowerCase())) newAdds.add(a);
        }
        return newAdds;
    }

    public ArrayList<Integer> getIdsByName(List<String> names) {
        ArrayList<Integer> addsIds = new ArrayList<>();
        for (String addName : names) {
            for (AddService a : adds) {
                if (a.getName().equals(addName)) {
                    addsIds.add(a.getAddServiceId());
                }
            }
        }
        return addsIds;
    }

//    public AddService findByName(String name) {
//        AddService newAdd = null;
//        for (AddService a : adds) {
//            if (a.getName().toLowerCase().equals(name.toLowerCase())) newAdd = a;
//        }
//        return newAdd;
//    }

        public ArrayList<String> getAddsNames () {
            ArrayList<String> addsNames = new ArrayList<>();
            for (AddService a : adds) {
                addsNames.add(a.getName());
            }
            return addsNames;
        }

    }
