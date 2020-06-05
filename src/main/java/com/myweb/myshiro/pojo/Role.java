package com.myweb.myshiro.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author
 * @create 2020/4/26-13:15
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {

    private int id;
    private String name;
    private List<User> users;
    private List<Permission> permissions;
    public Role(String name){
        this.name=name;
    }
//    private String desc_;
}
