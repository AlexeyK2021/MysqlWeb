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

    public String isStats() {
        String cssClass = "nav-link";
        if (stats) cssClass += " active";
        return cssClass;
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

    public String isClients() {
        String cssClass = "nav-link";
        if (clients) cssClass += " active";
        return cssClass;
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

    public String isTariffs() {
        String cssClass = "nav-link";
        if (tariffs) cssClass += " active";
        return cssClass;
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

    public String isAdds() {
        String cssClass = "nav-link";
        if (adds) cssClass += " active";
        return cssClass;
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

    public String isMainPage() {
        String cssClass = "nav-link";
        if (mainPage) cssClass += " active";
        return cssClass;
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

    public String isPersonalInfo() {
        String cssClass = "nav-link";
        if (personalInfo) cssClass += " active";
        return cssClass;
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
