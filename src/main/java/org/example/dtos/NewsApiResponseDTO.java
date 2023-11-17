package org.example.dtos;

import com.google.gson.annotations.SerializedName;
import org.example.models.New;

import java.util.List;

public class NewsApiResponseDTO {
    private String status;
    private int page;
    @SerializedName("items")
    private List<NewDTO> news;
    private int total;
    private int pageLength;

    private String traffic;

    public String getStatus() {
        return status;
    }

    public int getPage() {
        return page;
    }

    public List<NewDTO> getNews() {
        return news;
    }

    public int getTotal() {
        return total;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setNews(List<NewDTO> news) {
        this.news = news;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    @Override
    public String toString() {
        return "NewsApiResponseDTO{" +
                "status='" + status + '\'' +
                ", page=" + page +
                ", news=" + news +
                ", total=" + total +
                ", traffic='" + traffic + '\'' +
                '}';
    }
}
