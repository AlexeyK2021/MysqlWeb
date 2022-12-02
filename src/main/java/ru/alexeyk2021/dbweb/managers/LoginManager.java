package ru.alexeyk2021.dbweb.managers;


import ru.alexeyk2021.dbweb.HashController;
import ru.alexeyk2021.dbweb.models.Client;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginManager {
    private static LoginManager loginManager = null;
    private DbManager dbManager;

    private Client currentUser = null;
    private boolean adminIsLogged = false;
    private String adminLogin = "admin@admin.ru";
    private String adminPasswd = "ac9689e2272427085e35b9d3e3e8bed88cb3434828b43b86fc0596cad4c6e270";

    private LoginManager() {
        dbManager = DbManager.getInstance();
    }

    public static LoginManager getInstance() {
        if (loginManager == null)
            loginManager = new LoginManager();
        return loginManager;
    }

    public boolean enter(String login, String password) {
        String encryptedPassword = HashController.hash(password);
        currentUser = dbManager.approveEnter(login, encryptedPassword);
        return currentUser != null;
    }

    public boolean enterAsAdmin(String login, String password) {
        String encryptedPassword = HashController.hash(password);
        adminIsLogged =  login.equals(adminLogin) && encryptedPassword.equals(adminPasswd);
        return adminIsLogged;
    }

    public boolean isAdminIsLogged() {
        return adminIsLogged;
    }

    public Client getCurrentUser() {
        return currentUser;
    }

    public void exit(){
        currentUser = null;
        adminIsLogged = false;
    }

}
