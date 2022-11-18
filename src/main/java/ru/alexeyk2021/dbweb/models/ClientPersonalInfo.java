package ru.alexeyk2021.dbweb.models;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public ClientPersonalInfo(ResultSet resultSet) {
        try {
            clientId = resultSet.getInt("client_id");
            fullName = resultSet.getString("full_name");
            passportData = resultSet.getString("passport_data");
            login = resultSet.getString("login");
            password = resultSet.getString("password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFullName() {
        return fullName;
    }

    public String getShortName() {
        String shortname = "";
        shortname = String.copyValueOf(fullName.toCharArray(), 0, fullName.indexOf(" ") + 1);
        shortname += String.copyValueOf(fullName.toCharArray(), fullName.indexOf(" ") + 1, 1) + ".";
        shortname += String.copyValueOf(fullName.toCharArray(), fullName.lastIndexOf(" ") + 1, 1) + ".";
        return shortname;
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
