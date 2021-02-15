package com.ydh.redsheep.seckill;

import com.ydh.redsheep.seckill.service.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class SeckillApplicationTests {

    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private StockService stockService;

    @Test
    void contextLoads() {

        String redisKey = "phone";
        redisTemplate.opsForValue().set(redisKey, 0);

        for (int i = 0; i < 1000; i++) {
            executorService.execute(() -> {
                stockService.deductingStock();
            });
        }


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();


    }

}
