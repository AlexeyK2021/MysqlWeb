package ru.alexeyk2021.dbweb.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Tariff {
    private int tariffId;
    private String name;
    private double cost;
    private String description;
    private double internetSize;
    private int minutesSize;
    private int smsSize;

    public Tariff(int tariffId, String name, double cost, String description, double internetSize, int minutesSize, int smsSize) {
        this.tariffId = tariffId;
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.internetSize = internetSize;
        this.minutesSize = minutesSize;
        this.smsSize = smsSize;
    }

    public Tariff() {
    }

    public Tariff(ResultSet resultSet) {
        try {
            this.tariffId = resultSet.getInt("tariff_id");
            this.name = resultSet.getString("name");
            this.cost = resultSet.getDouble("cost");
            this.description = resultSet.getString("description");
            this.internetSize = resultSet.getDouble("internet_size");
            this.minutesSize = resultSet.getInt("minutes_size");
            this.smsSize = resultSet.getInt("sms_size");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public double getInternetSize() {
        return internetSize;
    }

    public int getMinutesSize() {
        return minutesSize;
    }

    public int getSmsSize() {
        return smsSize;
    }

    public int getTariffId() {
        return tariffId;
    }

    public void setTariffId(int tariffId) {
        this.tariffId = tariffId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInternetSize(double internetSize) {
        this.internetSize = internetSize;
    }

    public void setMinutesSize(int minutesSize) {
        this.minutesSize = minutesSize;
    }

    public void setSmsSize(int smsSize) {
        this.smsSize = smsSize;
    }
}
