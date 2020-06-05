package com.myweb.myshiro.cartService.serviceImpl;

import com.myweb.myshiro.cartService.OrderService;
import com.myweb.myshiro.dao.OrderMapper;
import com.myweb.myshiro.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @create 2020/5/2-10:43
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Override
    public Order getOrderById(int id) {
        //必须使用自定义的查询，不能使用OrderExample提供的查询，
        //因为逆向工程生成的查询，都只是针对单标查询，不能实现联表查询，
        //其查询结果不能体现一对多，多对多的特性
        return orderMapper.getOrderById(id).get(0);
    }

    //自定义的联表查询
    @Override
    public List<Order> getOrderByUid(int uid) {
        return orderMapper.getOrderByUid(uid);
    }

//    @Override
//    public List<Order> getOrderByUid(int uid) {
//        OrderExample example=new OrderExample();
//        OrderExample.Criteria criteria =example.createCriteria();
//        criteria.andUidEqualTo(uid) ;
//        return orderMapper.selectByExample(example);
//    }

    //自定义查询
    @Override
    public List<Order> listAllOrder() {

        return orderMapper.listAllOrder();
    }

    @Override
    public void deleteOrderById(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateOrder(Order order) {
        orderMapper.updateByPrimaryKey(order);
    }

    @Override
    public int addOrder(Order order) {
        return orderMapper.insert(order);
    }
}
