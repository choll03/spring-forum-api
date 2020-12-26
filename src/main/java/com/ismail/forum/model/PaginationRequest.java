package com.ismail.forum.model;

public class PaginationRequest {

    private String url;
    private Integer page;

    public PaginationRequest() {
    }

    public PaginationRequest(String url, Integer page) {
        this.url = url;
        this.page = page;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
