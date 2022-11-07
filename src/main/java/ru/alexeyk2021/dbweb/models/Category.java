package ru.alexeyk2021.dbweb.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Category {
    private int categoryId;
    private String name;

    public Category(int categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public Category(ResultSet resultSet) {
        try {
            this.categoryId = resultSet.getInt("category_id");
            this.name = resultSet.getString("category_name");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return name;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
