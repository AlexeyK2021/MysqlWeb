package ru.alexeyk2021.dbweb.models;

import ru.alexeyk2021.dbweb.transfer.CreateClient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Client {
    private int clientId;
    private double balance;
    private String phoneNumber;
    private boolean accountState;

    private Tariff tariff;
    private ClientPersonalInfo personalInfo;
    private ArrayList<AddService> addServiceList;
    private ArrayList<Report> reportList;

    public Client(int clientId, double balance, String phoneNumber, boolean accountState, Tariff tariff,
                  ClientPersonalInfo personalInfo, ArrayList<AddService> addServiceList, ArrayList<Report> reportList) {
        this.clientId = clientId;
        this.balance = balance;
        this.phoneNumber = phoneNumber;
        this.accountState = accountState;

        this.tariff = tariff;
        this.personalInfo = personalInfo;
        this.addServiceList = addServiceList;

        this.reportList = reportList;
    }

    public Client(ResultSet resultSet) {
        try {
            clientId = resultSet.getInt("client_id");
            balance = resultSet.getDouble("balance");
            phoneNumber = resultSet.getString("phone_number");
            accountState = resultSet.getBoolean("account_state");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getClientId() {
        return clientId;
    }

    public double getBalance() {
        return balance;
    }

    public boolean getAccountState() {
        return accountState;
    }

    public String getAccountStateString() {
        return accountState ? "Активен" : "Неактивен";
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public ClientPersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(ClientPersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public ArrayList<AddService> getAddServiceList() {
        return addServiceList;
    }
    public ArrayList<Integer> getAddServiceListIds(){
        ArrayList<Integer> addsIds = new ArrayList<>();
        for (AddService add:addServiceList) {
            addsIds.add(add.getAddServiceId());
        }
        return addsIds;
    }

    public void setAddServiceList(ArrayList<AddService> addServiceList) {
        this.addServiceList = addServiceList;
    }


    public void setReportList(ArrayList<Report> reportList) {
        this.reportList = reportList;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getShortPhoneName(){
        return phoneNumber.substring(1);
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", balance=" + balance +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", accountState=" + accountState +
                ", tariff=" + tariff +
                ", personalInfo=" + personalInfo +
                ", addServiceList=" + addServiceList +
                ", reportList=" + reportList +
                '}';
    }
}

