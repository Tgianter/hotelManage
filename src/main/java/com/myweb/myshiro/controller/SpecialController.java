package com.myweb.myshiro.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myweb.myshiro.pojo.*;
import com.myweb.myshiro.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 * @create 2020/4/27-21:16
 **/
@RestController
@RequestMapping("/authc")
public class SpecialController {
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RolePermissionService rolePermissionService;

    @PostMapping("/admin")
    public Object adminManage(){

        List<User> users=userService.getAllUser();
        List<Role> roles=roleService.getAllRole();
        List<Permission> permissions=permissionService.getAllPermission();
        return users.toString()+roles.toString()+permissions.toString();
    }
    @GetMapping("/listRole")
    public PageInfo<Role> getRolesByName(@RequestParam("name") String name){
        PageHelper.startPage(1,5);
        List<Role> roles= userService.getRole(name);
        return new PageInfo<>(roles);
    }

    @PostMapping("/addRole")
    public Object addRole(@RequestParam(value = "name")String name){

        Role role=new Role(name);
        roleService.addRole(role);
//        Subject subject= SecurityUtils.getSubject();
//        Object sessionId=subject.getSession().getId();
//        subject.getSession().stop();
//        return "添加角色(Role)成功："+role.toString()+sessionId;
        return "添加角色(Role)成功："+role;
    }
    @PostMapping("addPermission")
    public Object addPermission(@RequestParam("name") String name){
        Permission permission=new Permission(name);
        permissionService.addPermission(permission);
        return "添加权限(Permission)成功"+permission.toString();
    }
    //因为使用了@RequestBody，所以是从Http的请求体中获得JSON数据
    //springboot会自动的在Http的请求体中寻找合适JSON数据，并赋值给userRole
    @PostMapping("/addUserRole")
    public Object addUserRole(@RequestBody UserRole userRole){

        userRoleService.addUR(userRole);
        return "添加U—R成功"+userRole;
    }
    @PostMapping("/addRolePermission")
    public Object addRolePermission(@RequestBody RolePermission rolePermission){

        rolePermissionService.addRP(rolePermission);
        return "添加R-P成功"+rolePermission;
    }
    @DeleteMapping("/deleteUser")
    public String deleteManage(int id){
        userService.deleteUser(id);
        return "用户删除成功";
    }
}
