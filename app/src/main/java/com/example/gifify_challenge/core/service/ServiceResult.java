package com.example.gifify_challenge.core.service;

public class ServiceResult<T> {

    private Status status;
    private T data;
    private String error;

    public enum Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    public ServiceResult(Status status, T data, String error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }

}
