package com.myweb.myshiro.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author
 * @create 2020/4/26-21:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements Serializable {
    private int id;
    private String name;
    private List<Role> roles;
    public Permission(String name){
        this.name=name;
    }
}
