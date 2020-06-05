package com.myweb.myshiro.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myweb.myshiro.MyshiroApplication;
import com.myweb.myshiro.pojo.User;
import com.myweb.myshiro.rabbit.producer.TopicProducer;
import com.myweb.myshiro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author
 * @create 2020/4/27-16:54
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes= MyshiroApplication.class)
@Slf4j
public class DataTest {
    @Autowired
    UserService userService;
    @Test
    public void test(){
        int start=1;
        int size=5;
        PageHelper.startPage(start,size);
        List<User> users=userService.getAllUser();
        PageInfo page= new PageInfo<>(users);
        System.out.println(page.getPageNum());
        List<User> userList=page.getList();

        for (User user:userList){
            System.out.println(user.getName());
        }
    }
}
