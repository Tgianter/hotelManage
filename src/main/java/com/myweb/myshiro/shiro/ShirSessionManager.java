package com.myweb.myshiro.shiro;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @author
 * @create 2020/5/4-13:48
 **/
public class ShirSessionManager {
    //在HTTP的请求头中sessionId是以键值对存储的，而我们可以自己定义键的名字为token
    private static final String AUTHORIZATION="token";
    public ShirSessionManager(){
        super();
    }


}
