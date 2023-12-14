package org.example.dtos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewDTO implements Serializable {
    @SerializedName("timestamp")
    private Long timestamp;
    @SerializedName("mm_id")
    private long id;
    private String title;
    private String domain;
    private String country;
    private String traffic;
    @SerializedName("country_name")
    private String countryName;
    @SerializedName("region_name")
    private String regionName;

    @SerializedName("url")
    private String url;

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

    public String getTraffic() {
        return traffic;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getUrl() {
        return url;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "NewDTO{" +
                "timestamp=" + timestamp +
                ", id=" + id +
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
