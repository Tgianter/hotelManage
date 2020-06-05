package com.myweb.myshiro.rabbit.rabbitConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author
 * @create 2020/5/5-22:39
 **/
@Configuration
public class TopicRabbitConfig {

    private static final String que1="topic_queue1";
    private static final String que2="topic_queue2";
    private static final String exchange="topicExchange";
    //配置队列1
    @Bean("que1")
    public Queue getQueue1(){
        return new Queue(que1);
    }
    //配置队列2
    @Bean("que2")
    public Queue getQueue2(){
        return new Queue(que2);
    }
    //配置交换机exchange，类型为topic
    @Bean("topicExchange")
    public TopicExchange topicExchange(){
        return new TopicExchange(exchange);
    }
    //将Queue与Exchange完成绑定（bind）
    @Bean
    public Binding bindExchangeQueue1(@Qualifier("topicExchange")TopicExchange exchange1, @Qualifier("que1") Queue  que){

        return BindingBuilder.bind(que).to(exchange1).with("topic.first");
    }
    //将Queue与Exchange完成绑定（bind）
    @Bean
    public Binding bindExchangeQueue2(){
        TopicExchange exchange2=topicExchange();
        Queue que=getQueue2();
        //topic.#表示所有以topic开头的routingKey,例如: topic.first.message
        //而topic.*表示所以topic开头的只有两层的routingKey，例如：topic.first
        return BindingBuilder.bind(que).to(exchange2).with("topic.#");
    }

}
