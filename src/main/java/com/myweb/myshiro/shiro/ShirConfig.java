package com.myweb.myshiro.shiro;


import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * @author
 * @create 2020/4/26-14:51
 **/
@Configuration
public class ShirConfig {
    /**
     * 默认过期时间30分钟，即在30分钟内不进行操作则清空缓存信息，页面即会提醒重新登录
     */
    private final int EXPIRE = 1800;

    /**
     *  Redis配置
     */
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
//    @Value("${spring.redis.password}")
//    private String password;

    /**
     *  shiro的生命周期管理
     * @return
     */
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * shiro的url权限配置
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(@Qualifier("securityManager")SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //设置shiro的安全管理securityManager
        //此时的SecurityManager是传入的参数，不是使用的下方的securityManager方法
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        //身份认证失败，则跳转到登录页面的配置 没有登录的用户请求需要登录的页面时自动跳转到登录页面，
//        shiroFilterFactoryBean.setLoginUrl("/home/doLogin");
//
//        // 登录成功后要跳转的链接
//        //登录成功默认跳转页面，不配置则跳转至”/”。如果登陆前点击的一个需要登录的页面，则在登录自动跳转到那个需要登录的页面。不跳转到此。
//        shiroFilterFactoryBean.setSuccessUrl("/home/index");
//
//        //未授权要跳转的界面;
//        shiroFilterFactoryBean.setUnauthorizedUrl("/home/unauthc");

        Map<String,String> filterChainDefinitionMap= new HashMap<String, String>();
        //logout是shiro的一个默认过滤器，当跳转路径即url为"doLogout"时，用户就会退出登录
        //右边的默认过滤器即对应着当前登录用户所拥有的各种权限
        //当用户完成认证(Authentication)后，shiro就会根据dataBaseRealm获取当前登录用户的权限(Authorization)
        //和用户对应的角色(Role),此时，当前用户的权限和角色便由shiro管理
        //添加Shiro内置过滤器
        /**
         * Shiro内置过滤器，可以实现权限相关的拦截器
         *    常用的过滤器：
         *       anon: 无需认证（登录）可以访问
         *       authc: 必须认证才可以访问
         *       user: 如果使用rememberMe的功能可以直接访问
         *       perms： 该资源必须得到资源权限才可以访问
         *       role: 该资源必须得到角色权限才可以访问
         */


        filterChainDefinitionMap.put("/home/**","anon");
        filterChainDefinitionMap.put("/user/**","anon");
        //调用自带的过滤器logout，shiro会自动清除用户的sessionId
        filterChainDefinitionMap.put("/home/doLogout", "logout");
        filterChainDefinitionMap.put("/cart/*","authc");
        filterChainDefinitionMap.put("/authc/index","authc");
        filterChainDefinitionMap.put("/authc/admin","roles[admin]");
        filterChainDefinitionMap.put("/authc/addRole","perms[Create,Update]");
        filterChainDefinitionMap.put("/authc/addPermission","perms[Create,Update]");
        filterChainDefinitionMap.put("/authc/addUserRole","perms[Create,Update]");
        filterChainDefinitionMap.put("/authc/addRolePermission","perms[Create,Update]");
        filterChainDefinitionMap.put("/authc/deleteUser","perms[Delete]");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:
        filterChainDefinitionMap.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;

    }
    //安全管理器
    @Bean("securityManager")
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //securityManager实现sessionManager
        securityManager.setSessionManager(sessionManager());
        //securityManager实现cacheManager
        securityManager.setCacheManager(cacheManager());
        //实现rememberMe
        securityManager.setRememberMeManager(rememberMeManager());

        // 自定义Realm验证
        securityManager.setRealm(getShirRealm());
        return securityManager;
    }
    /**
     * 身份验证器
     */
    //Realm实现
    @Bean("shirRealm")
    public ShirRealm getShirRealm(){
        ShirRealm shirRealm =new ShirRealm();
        shirRealm.setCachingEnabled(true);
//        //启用身份验证缓存，即缓存AuthenticationInfo信息，默认true
//        shirRealm.setAuthenticationCachingEnabled(true);
        //缓存AuthenticationInfo信息的缓存名称 在ehcache-shiro.xml中有对应缓存的配置
        shirRealm.setAuthenticationCacheName("authenticationCache");
        //启用授权缓存，即缓存AuthorizationInfo信息，默认true
        shirRealm.setAuthorizationCachingEnabled(true);
        //缓存AuthorizationInfo信息的缓存名称  在ehcache-shiro.xml中有对应缓存的配置
        shirRealm.setAuthorizationCacheName("authorizationCache");
        shirRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shirRealm;
    }
    //将AuthenticationInfo传来的username，password，salt进行加密处理
    //若得到的加密密码和存储在DB中的密码一样则验证通过
    //凭证匹配器实现
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher=new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    /**
     * 配置Redis管理器：使用的是shiro-redis开源插件
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        //crazyCake 3.2.1默认实现redis的端口，不需要设置 ，但3.1.0需要设置
        //最好使用3.1.0
        redisManager.setPort(port);
        redisManager.setTimeout(timeout);
//        redisManager.setPassword(password);
        return redisManager;
    }
    /**
     * shiro缓存管理器;
     * 向Redis缓存用户信息，一般是用户的授权信息  (使用的是shiro-redis开源插件)
     * 需要添加到securityManager中
     * @return
     */
    @Bean("cacheManager")
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //redis中针对不同用户缓存
        //pojo中的用户实体类必须有name属性
        //在redis中会为每个用户单独的权限信息设置一个key-value进行缓存，key值即为用户名
        redisCacheManager.setPrincipalIdFieldName("name");
        //设置redis中的缓存数据的过期时间，当数据过期后，shiro会去mysql中查询最新数据。
        redisCacheManager.setExpire(10000);

        return redisCacheManager;
    }

    /**
     * 配置会话ID生成器，非必要
     * @return
     */
    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }
    /**
     * 配置session监听，非必要
     * @return
     */
    @Bean("sessionListener")
    public ShirSessionListener sessionListener(){
        ShirSessionListener sessionListener = new ShirSessionListener();
        return sessionListener;
    }

    /**
     * 配置RedisSessionDAO (使用的是shiro-redis开源插件)
     * 将会话进行持久化操作，即把会话内容（一般是sessionId）存储到redis中。
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setExpire(EXPIRE);
        return redisSessionDAO;
    }
    /**
     * 配置保存sessionId的cookie
     * 注意：这里的cookie 不是上面的记住我 cookie 记住我需要一个cookie session管理 也需要自己的cookie
     * 默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid
     * @return
     */
    @Bean("sessionIdCookie")
    public SimpleCookie sessionIdCookie(){
        //这个参数是cookie的名称
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：

        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //maxAge=-1表示浏览器关闭时失效此Cookie
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }
    /**
     * 配置会话管理器，设定会话超时及保存
     * @return
     */
    @Bean("sessionManager")
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        //配置监听
        listeners.add(sessionListener());
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        sessionManager.setSessionDAO(redisSessionDAO());
//        sessionManager.setCacheManager(cacheManager());

        //全局会话超时时间（单位毫秒），默认30分钟  暂时设置为10秒钟 用来测试
        sessionManager.setGlobalSessionTimeout(1800000);
        //是否开启删除无效的session对象  默认为true
        sessionManager.setDeleteInvalidSessions(true);
        //是否开启定时调度器进行检测过期session 默认为true
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
        //设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
        //暂时设置为 5秒 用来测试
        sessionManager.setSessionValidationInterval(3600000);
        //取消url 后面的 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;

    }
    /**
     * cookie对象;会话Cookie模板 ,默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid或rememberMe，自定义
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }

    /**
     * cookie管理对象;记住我功能,rememberMe管理器
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    /**
     * FormAuthenticationFilter 过滤器 过滤记住我
     * @return
     */
    @Bean
    public FormAuthenticationFilter formAuthenticationFilter(){
        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
        //对应前端的checkbox的name = rememberMe
        formAuthenticationFilter.setRememberMeParam("rememberMe");
        return formAuthenticationFilter;
    }

    /**
     *  开启shiro aop注解支持.
     *  支持在controller的方法上添加相关注解，实现权限控制
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }
}
