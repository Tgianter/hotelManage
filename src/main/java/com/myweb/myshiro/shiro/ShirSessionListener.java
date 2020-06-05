package com.myweb.myshiro.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author
 * @create 2020/5/3-19:50
 **/
//会话监听器
public class ShirSessionListener implements SessionListener {

    //AtomicInteger是一个线程安全的Integer整型封装类
    //表示当前的会话数
    private final AtomicInteger sessionCount= new AtomicInteger(0);

    /**
     *  会话创建时触发
     * @param session
     */
    @Override
    public void onStart(Session session) {
        //等价于sessionCount.incrementAndGet();
        sessionCount.addAndGet(1);
    }
    /**
     * 退出会话时触发
     * @param session
     */
    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();
    }
    /**
     * 会话过期时触发
     * @param session
     */
    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
    }
    /**
     * 获取在线人数使用
     * @return
     */
    public AtomicInteger getOnlineSession(){
        return sessionCount;
    }
}
