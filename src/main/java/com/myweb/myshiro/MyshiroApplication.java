package com.myweb.myshiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//@SpringBootApplication
//当配置了多数据源，要用这个注解，不使用数据源的自动配置
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@MapperScan(basePackages = "com.myweb.myshiro.mapper")
public class MyshiroApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
        SpringApplication.run(MyshiroApplication.class, args);
    }
    //这里配置静态资源文件的路径导包都是默认的直接导入就可以
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
        super.addResourceHandlers(registry);
    }

}
