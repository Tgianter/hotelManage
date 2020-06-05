package com.myweb.myshiro.rabbit.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author
 * @create 2020/5/6-0:35
 **/
@Component
@RabbitListener(queues = "q_fanout_C")
public class FanoutConsumer3 {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("CReceiver  : " + hello + "/n");
    }
}
