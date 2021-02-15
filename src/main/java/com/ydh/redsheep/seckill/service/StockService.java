package com.ydh.redsheep.seckill.service;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StockService {

    public static final String redisKey = "phone";

    @Resource
    private RedisTemplate redisTemplate;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void deductingStock() {
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                List<Object> result = null;
                do {
                    int count = 0;
                    operations.watch(redisKey);  // watch某个key,当该key被其它客户端改变时,则会中断当前的操作
                    String value = (String) operations.opsForValue().get(redisKey);
                    if (!StringUtils.isEmpty(value)) {
                        count = Integer.parseInt(value);
                    }
                    String userInfo = UUID.randomUUID().toString();
                    if (count < 20) {
                        operations.multi(); //开始事务
                        operations.opsForValue().increment(redisKey);
                        result = operations.exec(); //提交事务
                        if (result != null && result.size() > 0) {
                            System.out.println("用户:" + userInfo + "，秒杀成功! 当前成功人数:" + (count + 1));
                            // 用于向broker发送消息
                            // 第一个参数是topic名称
                            // 第二个参数是消息内容
                            rocketMQTemplate.convertAndSend(
                                    "seckill",
                                    "seckill phone"
                            );
                        } else {
                            System.out.println("用户:" + userInfo + "，秒杀失败");
                        }
                    } else {
                        System.out.println("已经有20人秒杀成功，秒杀结束");
                        return null;
                    }
                } while (result == null); //如果失败则重试
                return null;
            }

        });
    }

}
