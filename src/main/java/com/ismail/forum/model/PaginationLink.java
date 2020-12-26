package com.ismail.forum.model;

public class PaginationLink {

    private String next;

    public PaginationLink() {
    }

    public PaginationLink(String next) {
        this.next = next;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
