package com.ydh.redsheep.seckill.controller;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRocketProducerController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @RequestMapping("test")
    public void test() {
        // 用于向broker发送消息
        // 第一个参数是topic名称
        // 第二个参数是消息内容
        this.rocketMQTemplate.convertAndSend(
                "seckill",
                "seckill phone"
        );
    }

}
