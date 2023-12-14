package org.example.dtos;

public class ShowNewsAll {
    private String title;
    private String countryName;
    private String regionName;
    private String url;

    public ShowNewsAll() {
        // Конструктор без параметров (может потребоваться в зависимости от требований)
    }

    public ShowNewsAll(String title, String countryName, String regionName,String url) {
        this.title = title;
        this.countryName = countryName;
        this.regionName = regionName;
        setUrl(url);
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if(url.indexOf("https://") == -1){
            url ="https://" + url;
        }
        this.url = url;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}