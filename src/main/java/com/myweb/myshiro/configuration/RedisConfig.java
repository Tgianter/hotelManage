package com.myweb.myshiro.configuration;

import com.myweb.myshiro.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author
 * @create 2020/5/6-12:54
 **/

/**
 * redis配置类
 * 完成存储到redis中的数据 的key和value 的序列化器的配置
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory factory){
        RedisTemplate<String,Object>template=new RedisTemplate<>();
        //和redis客户端Lettuce实现关联
        template.setConnectionFactory(factory);
        //设置key的序列化器
        template.setKeySerializer(new StringRedisSerializer());
        // hash的key也采用String的序列化方式
//        template.setHashKeySerializer(new StringRedisSerializer());
        //设置value的序列化器
        //将实体类以json的形式，存储到redis数据库中
        //Jackson2JsonRedisSerializer是实现将一个Object序列化为一个Json格式的序列化对象
        //StringRedisSerializer是实现讲一个Object对象序列化为一个String字符串
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        // hash的value序列化方式采用jackson
//        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        return template;
    }
}
