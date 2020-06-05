package com.myweb.myshiro.rabbit.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author
 * @create 2020/5/6-0:32
 **/
@Component
@RabbitListener(queues = "q_fanout_A")
public class FanoutConsumer1 {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("AReceiver  : " + hello + "/n");
    }
}
