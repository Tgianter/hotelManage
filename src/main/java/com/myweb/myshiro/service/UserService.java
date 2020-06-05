package com.myweb.myshiro.service;

import com.myweb.myshiro.pojo.Permission;
import com.myweb.myshiro.pojo.Role;
import com.myweb.myshiro.pojo.User;

import java.util.List;

/**
 * @author
 * @create 2020/4/26-23:42
 **/
public interface UserService {
    public User getUser(String name);
    public List<Role> getRole(String name);
    public List<Permission> getPermission(String name);
    public void addUser(User user);
    void deleteUser(int id);
    public List<User> getAllUser();
}


