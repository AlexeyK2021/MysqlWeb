package ru.alexeyk2021.dbweb.models;


import java.sql.ResultSet;
import java.sql.SQLException;

public class AddService {
    private int addServiceId;
    private String name;
    private double cost;
    private String description;
    private double internetSize;
    private int minutesSize;
    private int smsSize;

    public AddService(int addServiceId, String name, double cost, String description, double internetSize, int minutesSize, int smsSize) {
        this.addServiceId = addServiceId;
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.internetSize = internetSize;
        this.minutesSize = minutesSize;
        this.smsSize = smsSize;
    }

    public AddService(ResultSet resultSet) {
        try {
            this.addServiceId = resultSet.getInt("add_service_id");
            this.name = resultSet.getString("name");
            this.cost = resultSet.getDouble("cost");
            this.description = resultSet.getString("description");
            this.internetSize = resultSet.getDouble("internet_size");
            this.minutesSize = resultSet.getInt("minutes_size");
            this.smsSize = resultSet.getInt("sms_size");
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }
/*

*/
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
}
