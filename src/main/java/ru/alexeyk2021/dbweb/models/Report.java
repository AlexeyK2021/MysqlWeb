package ru.alexeyk2021.dbweb.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Report {
    private int reportId;
    private Category category;
    private Date finish_date;
    private boolean result;
    private String description;

    public Report(int reportId, Category category, Date finish_date, boolean result, String description) {
        this.reportId = reportId;
        this.category = category;
        this.finish_date = finish_date;
        this.result = result;
        this.description = description;
    }

    public Report(ResultSet resultSet) {
        try {
            reportId = resultSet.getInt("report_id");
            finish_date = resultSet.getDate("finish_date");
            result = resultSet.getBoolean("result");
            description = resultSet.getString("description");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getReportId() {
        return reportId;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public Date getFinish_date() {
        return finish_date;
    }

    public boolean isResult() {
        return result;
    }

    public String getDescription() {
        return description;
    }
}
