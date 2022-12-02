package ru.alexeyk2021.dbweb.transfer;

import ru.alexeyk2021.dbweb.HashController;

import java.util.List;

public class CreateClient {
    private String firstName;
    private String secondName;
    private String thirdName;

    private String tariff;
    private int tariffId;
    private List<String> adds;
    private List<Integer> addsIds;

    private String passport;
    private String login;
    private String password;
    private String phoneNumber;

    public CreateClient(String firstName, String secondName, String thirdName, String tariff, List<String> adds, String passport, String login, String password, String phoneNumber) {
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

    public CreateClient() {
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
        return "CreateClient{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", thirdName='" + thirdName + '\'' +
                ", tariff='" + tariff + '\'' +
                ", adds=" + adds +
                ", passport='" + passport + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
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
}
