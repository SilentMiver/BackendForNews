package org.example.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("NewRating")
public class NewRating {
    @Id
    private long id;
    private String title;
    private String domain;
    private String url;

    public NewRating(long id, String title, String domain, String url) {
        this.id = id;
        this.title = title;
        this.domain = domain;
        this.url = url;
    }

    protected NewRating() {

    }

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
}
