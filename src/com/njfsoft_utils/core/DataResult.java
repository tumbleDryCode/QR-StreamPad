package com.njfsoft_utils.core;

public class DataResult {

    private static DataResult instance;
    private String data = null;

    protected DataResult() {

    }

    public static DataResult getInstance() { 
        if (instance == null) {
            instance = new DataResult();
        }
        return instance;
    }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
}
