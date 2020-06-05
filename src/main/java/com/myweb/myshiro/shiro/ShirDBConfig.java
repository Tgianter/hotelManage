package com.myweb.myshiro.shiro;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @create 2020/4/27-18:11
 **/

/**
 * 自行shiroDB的数据源
 */
@Configuration
@MapperScan(basePackages ="com.myweb.myshiro.mapper" ,sqlSessionTemplateRef="template")
public class ShirDBConfig {
//     @ConfigurationProperties(prefix = "spring.datasource")

    //springboot会自动将配置文件中的配置参数配置给DruidDataSource
    //包括Druid使用了哪些filters，比如监控stat，防御sql注入攻击的wall
    @ConfigurationProperties(prefix = "ds1.datasource")
    @Bean(name = "datasource")
    @Primary
    public DataSource getDataSource(){
//        return   DataSourceBuilder.create().build();
        
        DruidDataSource dataSource = new DruidDataSource();
        /**
         * 使用数据库连接池后，只是改变了mybatis数据源的来源，即从druid处获得数据源
         *因此我们此处把application.properties中的mysql的连接配置都通过
         * @ConfigurationProperties(prefix="ds1.datasource")传给DruidDataSource，
         * spring会自动的将配置参数和DataSource的属性匹配起来，并赋值
         * 但是配置文件中的配置参数必须和DataSource中的属性名一致，spring才能完成上述的匹配和赋值
         * 同时，也可以通过@Value将配置文件中的配置参数进行单独赋值，不过这样比较麻烦
         */

        return dataSource;
    }
    //配置SqlSessionFactory,用于配置SqlSessionTemplate
    @Bean(name = "factory")
    @Primary
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("datasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean.getObject();

    }
    //配置事务管理，当mybatis访问数据库时，
    //会由DataSourceTransactionManager进行事务管理
    @Bean
    @Primary
    public DataSourceTransactionManager getDataSourceManager(@Qualifier("datasource")DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
    //springboot中mybatis的SQLSession由SQLSessionTemplate管理
    @Bean(name = "template")
    @Primary
    public SqlSessionTemplate getSqlSessionTemplate(@Qualifier("factory")SqlSessionFactory factory){
        return new SqlSessionTemplate(factory);
    }

    /**
     * 注册Servlet信息， 配置监控视图
     * 用于查看Druid的监控信息
     * 在浏览器输入http://localhost:8080/druid/index.html可以查看监控信息
     * @return
     */
    @Bean
    public ServletRegistrationBean druidServlet() {

        //springboot的Servlet注册管理类
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        //相当于在xml文件中定义了一个servlet，
        servletRegistrationBean.setServlet(new StatViewServlet());
        //为定义的servlet配置映射路径
        servletRegistrationBean.addUrlMappings("/druid/*");
        //为定义的servlet配置参数
        Map<String, String> initParameters = new HashMap<String, String>();
        //登录查看信息的账号密码, 用于登录Druid监控后台
        initParameters.put("loginUsername", "root");
        initParameters.put("loginPassword", "12345");
        // 禁用HTML页面上的“Reset All”功能
        initParameters.put("resetEnable", "false");
        // IP白名单 (没有配置或者为空，则允许所有访问)，标明了哪些IP地址可以登录监控页面
        initParameters.put("allow", "");
        // IP黑名单 (存在共同时，deny优先于allow)
        //initParameters.put("deny", "192.168.20.38");

        //把参数注入到定义的servlet
        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }

    /**
     * 注册Filter信息, 监控拦截器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        //springboot的过滤器Filter注册管理类
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        //相当于在xml里定义了一个Filter
        filterRegistrationBean.setFilter(new WebStatFilter());
        //Filter对所有的Url都将进行过滤
        filterRegistrationBean.addUrlPatterns("/*");
        //暂时未知
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
