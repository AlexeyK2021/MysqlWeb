package ru.alexeyk2021.dbweb.transfer;

public class PageSettings {
    private boolean stats;
    private boolean clients;
    private boolean tariffs;
    private boolean adds;

    private boolean mainPage;
    private boolean personalInfo;

    private String currentPageName;

    public PageSettings() {
    }

    public boolean isStats() {
        return stats;
    }

    public void setStats() {
        this.stats = true;
        this.clients = false;
        this.tariffs = false;
        this.adds = false;
        this.mainPage = false;
        this.personalInfo = false;
        currentPageName = "Статистика";
    }

    public boolean isClients() {
        return clients;
    }

    public void setClients() {
        this.stats = false;
        this.clients = true;
        this.tariffs = false;
        this.adds = false;
        this.mainPage = false;
        this.personalInfo = false;
        currentPageName = "Настройка клиентов";
    }

    public boolean isTariffs() {
        return tariffs;
    }

    public void setTariffs() {
        this.stats = false;
        this.clients = false;
        this.tariffs = true;
        this.adds = false;
        this.mainPage = false;
        this.personalInfo = false;
        currentPageName = "Настройка тарифов";
    }

    public boolean isAdds() {
        return adds;
    }

    public void setAdds() {
        this.stats = false;
        this.clients = false;
        this.tariffs = false;
        this.adds = true;
        this.mainPage = false;
        this.personalInfo = false;
        currentPageName = "Настройка дополнительных услуг";
    }

    public boolean isMainPage() {
        return mainPage;
    }

    public void setMainPage() {
        this.stats = false;
        this.clients = false;
        this.tariffs = false;
        this.adds = false;
        this.mainPage = true;
        this.personalInfo = false;
        currentPageName = "Главная";
    }

    public boolean isPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo() {
        this.stats = false;
        this.clients = false;
        this.tariffs = false;
        this.adds = false;
        this.mainPage = false;
        this.personalInfo = true;
        currentPageName = "Настройка личной информации";
    }

    public String getCurrentPageName() {
        return currentPageName;
    }
}
