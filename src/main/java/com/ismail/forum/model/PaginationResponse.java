package com.ismail.forum.model;


public class PaginationResponse<T> extends WebResponse{

    private PaginationLink links;
    private PaginationMeta meta;

    public PaginationResponse() {
    }

    public PaginationResponse(Integer code, String status, T data, PaginationLink links, PaginationMeta meta) {
        super(code, status, data);
        this.links = links;
        this.meta = meta;
    }

    public PaginationLink getLinks() {
        return links;
    }

    public void setLinks(PaginationLink links) {
        this.links = links;
    }

    public PaginationMeta getMeta() {
        return meta;
    }

    public void setMeta(PaginationMeta meta) {
        this.meta = meta;
    }
}
