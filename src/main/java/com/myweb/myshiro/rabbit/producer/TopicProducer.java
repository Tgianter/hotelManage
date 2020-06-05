package com.myweb.myshiro.rabbit.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author
 * @create 2020/5/5-22:05
 **/
//rabbitmq的主题模式
@Component
public class TopicProducer {
    @Autowired
    private AmqpTemplate rabbitTemplate;
    private static final String exchange="topicExchange";

    public void sendMessage(){
        String message="hello , i am the First topic message";
        rabbitTemplate.convertAndSend(exchange,"topic.first",message);
    }
    public void sendMessages(){
        String message="hello , i am the Second topic message";
        rabbitTemplate.convertAndSend(exchange,"topic.second",message);
    }
    public void sendThiMessage(){
        String message="hello , i am the Third  topic message";
        rabbitTemplate.convertAndSend(exchange,"topic.third",message);
    }
}
