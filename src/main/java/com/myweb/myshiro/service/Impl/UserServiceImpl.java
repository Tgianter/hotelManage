package com.myweb.myshiro.service.Impl;

import com.myweb.myshiro.mapper.UserMapper;
import com.myweb.myshiro.pojo.Permission;
import com.myweb.myshiro.pojo.Role;
import com.myweb.myshiro.pojo.User;
import com.myweb.myshiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @create 2020/4/26-23:44
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUser(String name) {

        return userMapper.getUserByName(name);

    }

    @Override
    public List<Role> getRole(String name) {
        return  userMapper.getUserByName(name).getRoles();
    }

    @Override
    public List<Permission> getPermission(String name) {
        return  userMapper.getUserByName(name).getPermissions();
    }
    @Override
    public void  addUser(User user){
         userMapper.addUser(user);
    }

    @Override
    public void deleteUser(int id) {
        userMapper.deleteRole(id);
    }

    @Override
    public List<User> getAllUser() {
        return userMapper.listUser();
    }
}
