package ru.alexeyk2021.dbweb.Repositories;

import ru.alexeyk2021.dbweb.managers.DbManager;
import ru.alexeyk2021.dbweb.models.Client;
import ru.alexeyk2021.dbweb.transfer.CreateClient;
import ru.alexeyk2021.dbweb.transfer.EditingClient;

import java.util.ArrayList;

public class ClientRepository {
    private ArrayList<Client> clients;

    public ClientRepository() {
        clients = DbManager.getInstance().getAllClients();
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public ArrayList<Client> findByPartPhoneNumber(String number) {
        ArrayList<Client> newClients = new ArrayList<>();
        for (Client c : clients) {
            if(c.getPhoneNumber().contains(number)) newClients.add(c);
        }
        return newClients;
    }

    public Client findByPhoneNumber(String number) {
        Client newClient = null;
        for (Client c : clients) {
            if(c.getPhoneNumber().equals(number)) newClient = c;
        }
        return newClient;
    }

    public void editClient(EditingClient client) {
        DbManager.getInstance().editClient(client);
    }

    public void createClient(CreateClient client) {
        DbManager.getInstance().newClient(client);
    }

    public void deleteClient(CreateClient client){

    }
    public void updateClientsList(){
        clients = DbManager.getInstance().getAllClients();
    }
}