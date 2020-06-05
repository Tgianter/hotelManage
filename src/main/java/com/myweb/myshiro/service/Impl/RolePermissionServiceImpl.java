package com.myweb.myshiro.service.Impl;

import com.myweb.myshiro.mapper.RolePermissionMapper;
import com.myweb.myshiro.pojo.RolePermission;
import com.myweb.myshiro.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author
 * @create 2020/4/27-22:17
 **/
@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    @Autowired
    RolePermissionMapper rolePermissionMapper;
    @Override
    public void addRP(RolePermission rolePermission) {
        rolePermissionMapper.addRP(rolePermission);
    }
}
