package org.example.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class New {
    private Date date;
    private String title;
    private String domain;
    private String country;
    private String traffic;

    private String countryName;
    private String regionName;
    protected New(){

    }

    public New(Date date, String title, String domain, String country, String traffic, String countryName, String regionName) {
        this.date = date;
        this.title = title;
        this.domain = domain;
        this.country = country;
        this.traffic = traffic;
        this.countryName = countryName;
        this.regionName = regionName;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }


    public String getDomain() {
        return domain;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    @Override
    public String toString() {
        return "New{" +
                "date=" + date +
                ", title='" + title + '\'' +
                ", domain='" + domain + '\'' +
                ", country='" + country + '\'' +
                ", traffic='" + traffic + '\'' +
                ", countryName='" + countryName + '\'' +
                ", regionName='" + regionName + '\'' +
                '}';
    }
}
