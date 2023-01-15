package ru.alexeyk2021.dbweb.transfer;

import ru.alexeyk2021.dbweb.HashController;
import ru.alexeyk2021.dbweb.Repositories.AddsRepository;
import ru.alexeyk2021.dbweb.Repositories.TariffsRepository;
import ru.alexeyk2021.dbweb.models.AddService;
import ru.alexeyk2021.dbweb.models.Client;

import java.util.ArrayList;
import java.util.List;

public class EditingClient {
    private int clientId;
    private String firstName;
    private String secondName;
    private String thirdName;
    private boolean accountState;
    private double balance;

    private String tariff;
    private int tariffId;
    private List<String> adds;
    private List<Integer> addsIds;

    private String passport;
    private String login;
    private String password;
    private String phoneNumber;


    public EditingClient(String firstName, String secondName, String thirdName, String tariff, List<String> adds, String passport, String login, String password, String phoneNumber) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.tariff = tariff;
        this.adds = adds;
        this.passport = passport;
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public EditingClient(Client client, AddsRepository addsRepository, TariffsRepository tariffsRepository) {
        if (client != null) {
            adds = new ArrayList<>();
            clientId = client.getClientId();
            accountState = client.getAccountState();
            balance = client.getBalance();
            phoneNumber = client.getPhoneNumber();
            login = client.getPersonalInfo().getLogin();
            tariff = client.getTariff().getName();
            passport = client.getPersonalInfo().getPassportData();
            firstName = client.getPersonalInfo().getFirstName();
            secondName = client.getPersonalInfo().getSecondName();
            thirdName = client.getPersonalInfo().getThirdName();

            for (AddService a:client.getAddServiceList()) {
                adds.add(a.getName());
            }
            addsIds = addsRepository.getIdsByNames(adds);
            tariffId = tariffsRepository.findByName(tariff).getTariffId();
        }
    }

    public EditingClient() {
        accountState = true;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getThirdName() {
        return thirdName;
    }

    public String getFullName() {
        return firstName + " " + secondName + " " + thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public List<String> getAdds() {
        return adds;
    }

    public void setAdds(List<String> adds) {
        this.adds = adds;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "CreateClient{" + "firstName='" + firstName + '\'' + ", secondName='" + secondName + '\'' + ", thirdName='" + thirdName + '\'' + ", tariff='" + tariff + '\'' + ", adds=" + adds + ", passport='" + passport + '\'' + ", login='" + login + '\'' + ", password='" + password + '\'' + '}';
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getTariffId() {
        return tariffId;
    }

    public void setTariffId(int tariffId) {
        this.tariffId = tariffId;
    }

    public List<Integer> getAddsIds() {
        return addsIds;
    }

    public void setAddsIds(List<Integer> addsIds) {
        this.addsIds = addsIds;
    }

    public void addAddId(int addId){
        this.addsIds.add(addId);
    }
    public void removeAddId(int addId){
        this.addsIds.remove((Integer)addId);
    }

    public boolean getAccountState() {
        return accountState;
    }

    public void setAccountState(boolean accountState) {
        this.accountState = accountState;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

}
