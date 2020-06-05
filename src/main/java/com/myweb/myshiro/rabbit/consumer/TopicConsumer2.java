package com.myweb.myshiro.rabbit.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author
 * @create 2020/5/5-23:39
 **/
@Component
@RabbitListener(queues="topic_queue2")
public class TopicConsumer2 {
    @RabbitHandler
    public void getMessage(String message){
        System.out.println("consumer2:"+message);
    }
}
