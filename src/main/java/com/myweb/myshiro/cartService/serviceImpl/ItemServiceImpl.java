package com.myweb.myshiro.cartService.serviceImpl;

import com.myweb.myshiro.cartService.ItemService;
import com.myweb.myshiro.dao.ItemMapper;
import com.myweb.myshiro.model.Item;
import com.myweb.myshiro.model.ItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @create 2020/5/2-0:53
 **/
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemMapper itemMapper;
    @Override
    public Item getItemById(int id) {

        return  itemMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Item> listAllItem() {
        ItemExample example=new ItemExample();
        example.setOrderByClause("id asc,oid desc");
        List<Item> list=itemMapper.selectByExample(example);
        return list;
    }

    @Override
    public void deleteItemById(int id) {

        itemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteItemByOid(int oid) {
        ItemExample example=new ItemExample();
        ItemExample.Criteria criteria=example.createCriteria();
        criteria.andOidEqualTo(oid);
        itemMapper.deleteByExample(example);
    }

    @Override
    public int addItem(Item item) {
        int i=itemMapper.insert(item);
        return i;
    }

    @Override
    public void updateItem(Item item) {
        itemMapper.updateByPrimaryKey(item);
    }
}
