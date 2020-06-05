package com.myweb.myshiro.service.Impl;

import com.myweb.myshiro.mapper.UserRoleMapper;
import com.myweb.myshiro.pojo.UserRole;
import com.myweb.myshiro.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author
 * @create 2020/4/27-22:14
 **/
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    UserRoleMapper userRoleMapper;
    @Override
    public void addUR(UserRole userRole) {
        userRoleMapper.addUR(userRole);
    }
}
