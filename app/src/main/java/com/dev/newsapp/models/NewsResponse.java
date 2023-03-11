package com.dev.newsapp.models;

import java.util.List;

public class NewsResponse {
    private String serviceMessageCode;
    private String serviceMessageText ;

    private List<NewsData> items ;

    public String getServiceMessageCode() {
        return serviceMessageCode;
    }

    public void setServiceMessageCode(String serviceMessageCode) {
        this.serviceMessageCode = serviceMessageCode;
    }

    public String getServiceMessageText() {
        return serviceMessageText;
    }

    public void setServiceMessageText(String serviceMessageText) {
        this.serviceMessageText = serviceMessageText;
    }

    public List<NewsData> getItems() {
        return items;
    }

    public void setItems(List<NewsData> items) {
        this.items = items;
    }
}
