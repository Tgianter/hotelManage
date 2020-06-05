package com.myweb.myshiro.service.Impl;

import com.myweb.myshiro.mapper.RoleMapper;
import com.myweb.myshiro.pojo.Permission;
import com.myweb.myshiro.pojo.Role;
import com.myweb.myshiro.pojo.User;
import com.myweb.myshiro.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @create 2020/4/26-23:59
 **/
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;

    @Override
    public void addRole(Role role) {
        roleMapper.addRole(role);
    }

    @Override
    public List<Role> getAllRole() {
        return roleMapper.listRole();
    }

    @Override
    public Role getRoleByName(String name) {
        return roleMapper.getRoleByName(name);
    }

    @Override
    public List<User> getUser(String name) {
        return  roleMapper.getRoleByName(name).getUsers();
    }

    @Override
    public List<Permission> getPermission(String name) {
        return  roleMapper.getRoleByName(name).getPermissions();
    }
}
