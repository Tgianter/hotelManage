package com.myweb.myshiro.rabbit.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author
 * @create 2020/5/5-23:27
 **/
@Component
@RabbitListener(queues = "topic_queue1")
public class TopicConsumer1 {
//
//    private static final String que1="topic_que1";
//    private static final String que2="topic_que2";
    @RabbitHandler
    public void getMessage(String message){
        System.out.println("consumer1:"+message);
    }

}
