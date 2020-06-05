package com.myweb.myshiro.utils;

import com.myweb.myshiro.pojo.Permission;
import com.myweb.myshiro.pojo.Role;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author
 * @create 2020/4/28-13:52
 **/
@Component
public class TransferToSetString {
    //将List<Role>转换为Set<String>
    public Set<String> transferRole(List<Role> set){
        Set<String> stringSet=new HashSet<>();
        for(Role role:set){
            //上转型对象obj调用的toString()是重写的方法
            stringSet.add(role.getName());
        }
        return stringSet;
    }
    //将List<Permission>转换为Set<String>
    public Set<String> transferPermission(List<Permission> set){
        Set<String> stringSet=new HashSet<>();
        for(Permission permission:set){
            //上转型对象obj调用的toString()是重写的方法
            stringSet.add(permission.getName());
        }
        return stringSet;
    }
}
