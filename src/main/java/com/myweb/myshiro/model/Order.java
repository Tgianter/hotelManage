package com.myweb.myshiro.model;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private Integer id;

    private Integer uid;

    private List<Item> items;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}