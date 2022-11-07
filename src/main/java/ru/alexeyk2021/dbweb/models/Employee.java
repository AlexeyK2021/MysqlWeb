package ru.alexeyk2021.dbweb.models;

public class Employee {
    private int employeeId;
    private String fullName;
    private String login;
    private String password;

    public Employee(int employeeId, String fullName, String login, String password) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.login = login;
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
