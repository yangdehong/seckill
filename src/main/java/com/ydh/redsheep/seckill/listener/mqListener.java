package com.ydh.redsheep.seckill.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RocketMQMessageListener(topic = "seckill", consumerGroup = "consumer_seckill_grp")
public class mqListener implements RocketMQListener<String> {

    private AtomicInteger count = new AtomicInteger(20);

    @Override
    public void onMessage(String message) {
        // 处理broker推送过来的消息
        log.info("接受到消息>>>>>>={}",message);
        // 业务、mysql操作等
        log.info("接受到消息>>>>>>={}。商品出库，当前库存={}", message, count.decrementAndGet());
    }
}
