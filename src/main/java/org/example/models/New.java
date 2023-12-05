package org.example.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Document(collection = "new")
public class New {
    @MongoId
    private long id;
    private long timestamp;
    private String title;
    private String domain;
    private String country;
    private String traffic;

    private String countryName;
    private String regionName;

    private String url;

    protected New() {

    }

    public New(long id, long timestamp, String title, String domain, String country, String traffic, String countryName, String regionName, String url) {
        this.id = id;
        this.timestamp = timestamp;
        this.title = title;
        this.domain = domain;
        this.country = country;
        this.traffic = traffic;
        this.countryName = countryName;
        this.regionName = regionName;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
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

    public String getUrl() {
        return url;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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

    public void setUrl(String url) {
        this.url = url;
    }

    protected void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "New{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", title='" + title + '\'' +
                ", domain='" + domain + '\'' +
                ", country='" + country + '\'' +
                ", traffic='" + traffic + '\'' +
                ", countryName='" + countryName + '\'' +
                ", regionName='" + regionName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
