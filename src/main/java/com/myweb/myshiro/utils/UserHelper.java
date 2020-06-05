package com.myweb.myshiro.utils;

import com.myweb.myshiro.pojo.User;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Component;

/**
 * @author
 * @create 2020/4/27-10:33
 **/
@Component
public class UserHelper {
    //用户密码加密
    public User transferUser(User user){
        String password=user.getPassword();
        String name=user.getName();
        String salt=new SecureRandomNumberGenerator().nextBytes().toHex();
        String algorithm="md5";
        int iterate_time=2;
        //对注册用户的密码进行加密处理
        String newPassword= String.valueOf(new SimpleHash(algorithm,password,salt,iterate_time));
        User newUser=user;
        newUser.setPassword(newPassword);
        newUser.setSalt(salt);
        return newUser;
    }
}
