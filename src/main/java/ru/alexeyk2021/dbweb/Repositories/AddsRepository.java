package ru.alexeyk2021.dbweb.Repositories;

import ru.alexeyk2021.dbweb.managers.DbManager;
import ru.alexeyk2021.dbweb.models.AddService;

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

    public ArrayList<Integer> getIdsByNames(List<String> names) {
        ArrayList<Integer> addsIds = new ArrayList<>();
        if(names == null) return addsIds;
        for (String addName : names) {
            for (AddService a : adds) {
                if (a.getName().equals(addName)) {
                    addsIds.add(a.getAddServiceId());
                }
            }
        }
        return addsIds;
    }

    public ArrayList<String> getAddsNames() {
        ArrayList<String> addsNames = new ArrayList<>();
        for (AddService a : adds) {
            addsNames.add(a.getName());
        }
        return addsNames;
    }

    public AddService findById(int id) {
        for (AddService a : adds) {
            if (a.getAddServiceId() == id) return a;
        }
        return null;
    }

    public void newAddService(AddService add) {
        DbManager.getInstance().newAddService(add);
    }

    public void deleteAdd(int id) {
        DbManager.getInstance().deleteAddService(id);
    }

    public void editAddService(AddService add) {
        DbManager.getInstance().updateAddService(add);
    }

    public void updateAddsList() {
        adds = DbManager.getInstance().getAllAdds();
    }
}
