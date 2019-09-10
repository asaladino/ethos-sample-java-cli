package com.ellucian.app.models;

import com.opencsv.bean.CsvBindByName;

public class Building {
    @CsvBindByName
    private String id;

    @CsvBindByName
    private String code;

    @CsvBindByName
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
