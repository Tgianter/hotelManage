package com.myweb.myshiro.controller;

import com.myweb.myshiro.pojo.User;
import com.myweb.myshiro.service.UserService;
import com.myweb.myshiro.utils.ObjectToJson;
import com.myweb.myshiro.utils.UserHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author
 * @create 2020/4/27-11:38
 **/
@RestController
@RequestMapping("/home")
public class GeneralController {
    @Autowired
    UserHelper userHelper;
    @Autowired
    UserService userService;
    @GetMapping("/index")
    public ModelAndView indexPage(){
        ModelAndView mv=new ModelAndView("index");
        return mv;
    }
//    @GetMapping("/index")
//    public Object indexPage(){
////        ModelAndView mv=new ModelAndView("index");
//        return "首页";
//    }
    @GetMapping("/test")
    public Object testPage(){
        Subject subject =SecurityUtils.getSubject();
        return subject.getSession();
    }
    @RequestMapping("/login")
    public ModelAndView login(){
        ModelAndView mv=new ModelAndView("login");
        return mv;
    }
    @RequestMapping("/manageUser")
    public ModelAndView manageUser(){
        return new ModelAndView("manageUser");
    }
    @RequestMapping("/manageRole")
    public ModelAndView manageRole(){
        return new ModelAndView("manageRole");
    }
    @RequestMapping("/managePermission")
    public ModelAndView managePer(){
        return new ModelAndView("managePermission");
    }
    @RequestMapping("/manageProduct")
    public ModelAndView manageProduct(){
        return new ModelAndView("manageProduct");
    }
    @RequestMapping("/manageOrder")
    public ModelAndView manageOrder(){
        return new ModelAndView("manageOrder");
    }
    @RequestMapping("/manageItem")
    public ModelAndView manageItem(){
        return new ModelAndView("manageItem");
    }
    @PostMapping("/doLogin")
    public Object doLogin(@RequestParam("username") String username, @RequestParam("password") String password){

        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        Subject subject= SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException ice) {
            return "fail";
        } catch (UnknownAccountException uae) {
            return "fail";
        }
        User user=userService.getUser(username);
        //当用户登录后，把用户信息存入会话session中，便于其他页面通过session获得用户信息。
        subject.getSession().setAttribute("user", user);
       return "success";
        // return toJson.transferToJSONObject(user);
    }
    @PostMapping("/register")
    public Object register(@RequestParam("username") String username,@RequestParam("password") String password){
        String salt =new SecureRandomNumberGenerator().toString();
        User user=new User(username,password,salt);
        User newUser=userHelper.transferUser(user);
        //UserMapper中的addUser()实现了获取自增长id
        userService.addUser(newUser);
        return "success";
    }
    @GetMapping("/doLogout")
    public Object doLogout() {
        Subject subject=SecurityUtils.getSubject();
        Object sessionId=subject.getSession().getId();
        //退出时会清除redis中的session缓存
        subject.getSession().stop();
        return "success";
    }
    @RequestMapping("unauthc")
    public String unAuthPage(){
        return "loginPage";
    }
}
