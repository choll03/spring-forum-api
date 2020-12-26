package com.ismail.forum.model;

public class PaginationMeta {

    private Integer currentPage;
    private Integer totalData;
    private Integer perPage;
    private Integer totalPage;

    public PaginationMeta() {
    }

    public PaginationMeta(Integer currentPage, Integer totalData, Integer perPage, Integer totalPage) {
        this.currentPage = currentPage;
        this.totalData = totalData;
        this.perPage = perPage;
        this.totalPage = totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalData() {
        return totalData;
    }

    public void setTotalData(Integer totalData) {
        this.totalData = totalData;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
