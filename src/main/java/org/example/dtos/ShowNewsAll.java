package org.example.dtos;

public class ShowNewsAll {private String title;
    private String countryName;
    private String regionName;

    public ShowNewsAll() {
        // Конструктор без параметров (может потребоваться в зависимости от требований)
    }

    public ShowNewsAll(String title, String countryName, String regionName) {
        this.title = title;
        this.countryName = countryName;
        this.regionName = regionName;
    }

    // Геттеры и сеттеры
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}