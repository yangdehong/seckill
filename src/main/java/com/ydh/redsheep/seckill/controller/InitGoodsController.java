package com.ydh.redsheep.seckill.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class InitGoodsController {

    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping("initGoods")
    public String initGoods() {
        String redisKey = "phone";
        redisTemplate.opsForValue().set(redisKey, 0);
        return "初始化秒杀商品成功";
    }

}
