package com.myweb.myshiro.controller;

import com.myweb.myshiro.cartService.ItemService;
import com.myweb.myshiro.cartService.OrderService;
import com.myweb.myshiro.cartService.ProductService;
import com.myweb.myshiro.model.Item;
import com.myweb.myshiro.model.Order;
import com.myweb.myshiro.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author
 * @create 2020/5/2-15:44
 **/
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    ItemService itemService;
    @Autowired
    ProductService proService;
    @Autowired
    OrderService orderService;
    @GetMapping("/getOrders")
//    @Cacheable(value="user-key")
    public List<Order> getOrderByUid(@RequestParam("uid") int uid){

        return orderService.getOrderByUid(uid);
    }
    @GetMapping("/getOrder")
    public Order getOrderById(@RequestParam("id") int id){
        return orderService.getOrderById(id);
    }
    @PostMapping("/buyGoods")
    public Object buyGoods(@RequestBody List<Item> items){
        Subject subject= SecurityUtils.getSubject();
        User user= (User) subject.getSession().getAttribute("user");
        int uid=user.getId();
        Order order=new Order();
        order.setUid(uid);
        orderService.addOrder(order);
        for(Item item:items){
            itemService.addItem(item);
        }
        return "购买商品成功";
    }
    @PostMapping("/deleteOrder")
    public Object deleteOrder(@RequestParam("id") int id){
        orderService.deleteOrderById(id);
        itemService.deleteItemByOid(id);
        return "删除订单成功";
    }
    
}
