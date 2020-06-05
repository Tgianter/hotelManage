package com.myweb.myshiro.cartService;

import com.myweb.myshiro.model.Product;

import java.util.List;

/**
 * @author
 * @create 2020/5/2-13:36
 **/
public interface ProductService {
    Integer addProduct(Product product);
    void deleteProduct(Integer id);
    Product getProductById(Integer id);
    List<Product> listAllProduct();
    void updateProduct(Product product);
}
