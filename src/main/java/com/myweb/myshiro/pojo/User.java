package com.myweb.myshiro.pojo;

import com.myweb.myshiro.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

/**
 * @author
 * @create 2020/4/26-13:03
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class User implements Serializable{

    private int id;
    private String name;
    private String password;
    private String salt;
    private List<Role> roles;
    private List<Permission> permissions;
    private List<Order> orders;
    public User(String name,String password,String salt){
        this.name=name;
        this.password=password;
        this.salt=salt;
    }
}
