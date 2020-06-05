package com.myweb.myshiro.cartService;

import com.myweb.myshiro.model.Order;

import java.util.List;

/**
 * @author
 * @create 2020/5/2-2:16
 **/
public interface OrderService {
    public Order getOrderById(int id);
    public List<Order> getOrderByUid(int uid);
    public List<Order> listAllOrder();
    public void deleteOrderById(int id);
    public void updateOrder(Order order);
    public int addOrder(Order order);
}
