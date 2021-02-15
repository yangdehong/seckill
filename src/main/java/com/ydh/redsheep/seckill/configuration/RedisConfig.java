package com.ydh.redsheep.seckill.configuration;

import com.ydh.redsheep.seckill.util.ObjectRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
*
* @author : yangdehong
* @date : 2021/1/19 18:50
*/
@Configuration
public class RedisConfig {

    // 自定义一个RedisTemplate
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        //定义value的序列化方式
        RedisSerializer<Object> redisSerializer = new ObjectRedisSerializer();

        template.setKeySerializer(redisSerializer);
        template.setValueSerializer(redisSerializer);
        template.setHashKeySerializer(redisSerializer);
        template.setHashValueSerializer(redisSerializer);
        template.afterPropertiesSet();
        return template;

    }

}
