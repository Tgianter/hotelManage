package com.myweb.myshiro.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Component;

/**
 * @author
 * @create 2020/5/6-17:32
 **/
@Component
public class ObjectToJson {
    public JSONObject transferToJSONObject(Object object){
        Session session=SecurityUtils.getSubject().getSession();
        String sessionId= (String) session.getId();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("sessionID",sessionId);
        jsonObject.put("data",object);
        return jsonObject;
    }
}
