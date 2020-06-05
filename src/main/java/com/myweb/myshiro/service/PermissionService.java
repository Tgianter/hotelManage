package com.myweb.myshiro.service;

import com.myweb.myshiro.pojo.Permission;
import com.myweb.myshiro.pojo.Role;

import java.util.List;

/**
 * @author
 * @create 2020/4/26-23:54
 **/
public interface PermissionService {
    public List<Permission> getAllPermission();
    public List<Role> getRole(String name);
    public Permission getPermission(String name);
    public void addPermission(Permission permission);
}
