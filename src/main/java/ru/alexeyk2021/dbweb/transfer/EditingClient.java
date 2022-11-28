package ru.alexeyk2021.dbweb.transfer;

import java.util.List;

public class EditingClient {
    private int addFunds;
    private double balance;
    private String phoneNumber;
    private boolean accountState;
    private String fullName;
    private int TariffId;
    private List<Integer> addsList;

    public EditingClient() {
    }

    public int getAddFunds() {
        return addFunds;
    }

    public void setAddFunds(int addFunds) {
        this.addFunds = addFunds;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isAccountState() {
        return accountState;
    }

    public void setAccountState(boolean accountState) {
        this.accountState = accountState;
    }

    public int getTariffId() {
        return TariffId;
    }

    public void setTariffId(int tariffId) {
        TariffId = tariffId;
    }

    public List<Integer> getAddsList() {
        return addsList;
    }

    public void setAddsList(List<Integer> addsList) {
        this.addsList = addsList;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
