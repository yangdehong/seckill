package com.ydh.redsheep.seckill.util;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
*
* @author : yangdehong
* @date : 2021/1/19 18:51
*/
public class ObjectRedisSerializer implements RedisSerializer<Object> {

    private final Charset charset;

    public ObjectRedisSerializer() {
        this(StandardCharsets.UTF_8);
    }

    public ObjectRedisSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    @Override
    public byte[] serialize(Object obj) throws SerializationException {
        String string;
        if (obj instanceof String) {
            string = (String) obj;
        } else {
            string = JsonUtils.object2String(obj);
        }
        if (string == null) {
            return null;
        }
        return string.getBytes(charset);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return (bytes == null ? null : new String(bytes, charset));
    }

}
