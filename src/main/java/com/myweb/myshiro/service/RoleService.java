package com.myweb.myshiro.service;

import com.myweb.myshiro.pojo.Permission;
import com.myweb.myshiro.pojo.Role;
import com.myweb.myshiro.pojo.User;

import java.util.List;

/**
 * @author
 * @create 2020/4/26-23:48
 **/
public interface RoleService {
    public void addRole(Role role);
    public List<Role> getAllRole();
    public Role getRoleByName(String name);
    public List<User> getUser(String name);
    public List<Permission> getPermission(String name);
}
