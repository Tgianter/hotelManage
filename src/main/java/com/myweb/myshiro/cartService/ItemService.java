package com.myweb.myshiro.cartService;

import com.myweb.myshiro.model.Item;

import java.util.List;

/**
 * @author
 * @create 2020/5/2-0:47
 **/
public interface ItemService {

    public Item getItemById(int id);
    public List<Item> listAllItem();
    public void deleteItemById(int id);
    public void deleteItemByOid(int oid);
    public int addItem(Item item);
    public void updateItem(Item item);
}
