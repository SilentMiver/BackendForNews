package org.example.dtos;

import java.io.Serializable;

public class NewRatingDTO implements Serializable {
    private long id;
    private String title;
    private String domain;
    private String url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "NewRatingDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", domain='" + domain + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
