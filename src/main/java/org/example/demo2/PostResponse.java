package org.example.demo2;

import org.springframework.http.HttpStatus;

import java.util.List;

public class PostResponse<T> {
    private String message;
    private T payload;
    private HttpStatus status;
    private String time;
    private int total;
    private int page;
    private int size;
    private int totalPages;

    public PostResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public PostResponse(String message, T payload, HttpStatus status) {
        this.message = message;
        this.payload = payload;
        this.status = status;
    }

    public PostResponse(String message, T payload, int total, int page, int size, int totalPages, HttpStatus status) {
        this.message = message;
        this.payload = payload;
        this.total = total;
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    // Getters and Setters for all fields
}
