package com.myweb.myshiro.rabbit.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author
 * @create 2020/5/6-0:34
 **/
@Component
@RabbitListener(queues = "q_fanout_B")
public class FanoutConsumer2 {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("BReceiver  : " + hello + "/n");
    }
}
