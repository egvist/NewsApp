package com.dev.newsapp.models;

import java.util.List;

public class CommentsResponse {
    private String serviceMessageCode;
    private String serviceMessageText ;

    private List<CommentsData> items ;

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

    public List<CommentsData> getItems() {
        return items;
    }

    public void setItems(List<CommentsData> items) {
        this.items = items;
    }
}


