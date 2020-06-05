package com.myweb.myshiro.dao;

import com.myweb.myshiro.model.Order;
import com.myweb.myshiro.model.OrderExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
@Qualifier(value = "cartTemplate")
@Component
public interface OrderMapper {
    //自定义
    List<Order> getOrderByUid(@Param("uid") Integer uid);
    //自定义
    List<Order> listAllOrder();
    //自定义，返回结果的第一个元素即为要查找订单
    List<Order> getOrderById(@Param("id") Integer id);

    int countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}