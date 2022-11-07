package ru.alexeyk2021.dbweb.models;

public class ClientPersonalInfo {
    private int clientId;
    private String fullName;
    private String passportData;
    private String login;
    private String password;

    public ClientPersonalInfo(int clientId, String fullName, String passportData, String login, String password) {
        this.clientId = clientId;
        this.fullName = fullName;
        this.passportData = passportData;
        this.login = login;
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassportData() {
        return passportData;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getClientId() {
        return clientId;
    }
}
