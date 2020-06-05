package com.myweb.myshiro.cartService.serviceImpl;

import com.myweb.myshiro.cartService.ProductService;
import com.myweb.myshiro.dao.ProductMapper;
import com.myweb.myshiro.model.Product;
import com.myweb.myshiro.model.ProductExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @create 2020/5/2-13:40
 **/
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Override
    public Integer addProduct(Product product) {
        return productMapper.insert(product);
    }

    @Override
    public void deleteProduct(Integer id) {
        ProductExample example=new ProductExample();
        ProductExample.Criteria criteria=example.createCriteria();
        criteria.andIdEqualTo(id);
        productMapper.deleteByExample(example);
    }

    @Override
    public Product getProductById(Integer id) {
        ProductExample example=new ProductExample();
        ProductExample.Criteria criteria=example.createCriteria();
        criteria.andIdEqualTo(id);
        return productMapper.selectByExample(example).get(0);
    }

    @Override
    public List<Product> listAllProduct() {
        ProductExample example=new ProductExample();
        return productMapper.selectByExample(example);
    }

    @Override
    public void updateProduct(Product product) {
        ProductExample example=new ProductExample();
        productMapper.updateByExample(product,example);
    }
}
