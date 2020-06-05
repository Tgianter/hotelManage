package com.myweb.myshiro.service.Impl;

import com.myweb.myshiro.mapper.PermissionMapper;
import com.myweb.myshiro.pojo.Permission;
import com.myweb.myshiro.pojo.Role;
import com.myweb.myshiro.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @create 2020/4/27-0:03
 **/
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionMapper permissionMapper;
    @Override
    public List<Permission> getAllPermission() {
        return permissionMapper.listPermission();
    }

    @Override
    public List<Role> getRole(String name) {
        return permissionMapper.getPermissionByName(name).getRoles();
    }

    @Override
    public Permission getPermission(String name) {
        return permissionMapper.getPermissionByName(name);
    }

    @Override
    public void addPermission(Permission permission) {
        permissionMapper.addPermission(permission);
    }
}
