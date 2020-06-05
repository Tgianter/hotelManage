package com.myweb.myshiro.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author
 * @create 2020/5/2-0:11
 **/
@Configuration
@MapperScan(basePackages = "com.myweb.myshiro.dao",sqlSessionTemplateRef = "cartTemplate")
public class DatabaseConfig {
    @Bean(name = "cartDatasource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource getDataSource(){
//        return DataSourceBuilder.create().build();
        return new DruidDataSource();
    }
    @Bean(name = "cartFactory")
    @Primary
    public SqlSessionFactory getSqlSessionFactory(@Qualifier(value = "cartDatasource") DataSource data) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(data);
        /**如果mapper接口不是使用注解方式，而是使用配置文件
         * 则一定要将mapper的xml配置文件
         */
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().
                getResources("classpath:/mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }
    @Bean(name = "cartTransactionManager")
    @Primary
    public DataSourceTransactionManager getTransactionManager(@Qualifier(value = "cartDatasource") DataSource data){
        return new DataSourceTransactionManager(data);
    }

    @Bean(name = "cartTemplate")
    @Primary
    public SqlSessionTemplate getSqlSessionTemplate(@Qualifier("cartFactory") SqlSessionFactory factory){

        return new SqlSessionTemplate(factory);
    }

}
