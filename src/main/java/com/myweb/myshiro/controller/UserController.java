package com.myweb.myshiro.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myweb.myshiro.cartService.ItemService;
import com.myweb.myshiro.cartService.OrderService;
import com.myweb.myshiro.cartService.ProductService;
import com.myweb.myshiro.model.Item;
import com.myweb.myshiro.model.Order;
import com.myweb.myshiro.model.Product;
import com.myweb.myshiro.pojo.Permission;
import com.myweb.myshiro.pojo.Role;
import com.myweb.myshiro.pojo.User;
import com.myweb.myshiro.service.PermissionService;
import com.myweb.myshiro.service.RoleService;
import com.myweb.myshiro.service.UserRoleService;
import com.myweb.myshiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
//实现跨域注解
//origin="*"代表所有域名都可访问
//maxAge飞行前响应的缓存持续时间的最大年龄，简单来说就是Cookie的有效期 单位为秒
//若maxAge是负数,则代表为临时Cookie,不会被持久化,Cookie信息保存在浏览器内存中,浏览器关闭Cookie就消失
//@CrossOrigin(origins = "*",maxAge = 3600)
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:8080/static/js/axios.min.js:8 "})  // 填前端地址
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;
    @Autowired
    ItemService itemService;
	@GetMapping("/listUsers")
    public PageInfo<User> listUsers(@RequestParam(value = "start",defaultValue = "1")int start,
                                    @RequestParam(value = "size",defaultValue = "5") int size){
        PageHelper.startPage(start,size);
        List<User> users=userService.getAllUser();
        return new PageInfo<>(users);
    }
    @GetMapping("listAllRole")
    public PageInfo<Role> listAllRole(@RequestParam("start") int start){
        PageHelper.startPage(start,5);
        List<Role> roles=roleService.getAllRole();
        return new PageInfo<Role>(roles);
    }
    @GetMapping("/listAllPermission")
    public PageInfo<Permission> listAllPermission(@RequestParam("start") int start){
        PageHelper.startPage(start,5);
        List<Permission> permissions=permissionService.getAllPermission();
        return new PageInfo<Permission>(permissions);
    }
    @GetMapping("/listAllProduct")
    public PageInfo<Product> listAllProduct(@RequestParam("start") int start){
        PageHelper.startPage(start,5);
        List<Product> products=productService.listAllProduct();
        return new PageInfo<Product>(products);
    }
    @GetMapping("/listAllOrder")
    PageInfo<Order> listAllOrder(@RequestParam("start") int start ){
	    PageHelper.startPage(start,5);
	    List<Order> orders=orderService.listAllOrder();
	    return new PageInfo<>(orders);
    }
    @GetMapping("/listAllOrderItem")
    public PageInfo<Item> listAllItem(@RequestParam("start") int start){
        PageHelper.startPage(start,5);
        List<Item> items=itemService.listAllItem();
        return new PageInfo<>(items);
    }

}
