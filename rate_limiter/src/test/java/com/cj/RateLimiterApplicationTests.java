package com.cj;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class RateLimiterApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    DefaultRedisScript redisScript;


    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
        RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
        List<String> hehe1 = Arrays.asList("hehe");
        Object hehe = redisTemplate.execute(redisScript, stringSerializer, stringSerializer, hehe1, "10","3");
        System.out.println("hehe = " + hehe);
    }


    @Test
    public void test01(){
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("2.lua"));
        script.setResultType(Long.class);
        RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
        Long hehe = (Long) redisTemplate.execute(script, stringSerializer, stringSerializer, Arrays.asList("hehe"), "3");
        System.out.println("hehe = " + hehe);
    }


    @Test
    public void test02(){
        DefaultRedisScript<String> script = new DefaultRedisScript<>();
        script.setScriptText("return 'hello world'");
        script.setResultType(String.class);
        RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
        String result = (String) redisTemplate.execute(script, stringSerializer, stringSerializer, Arrays.asList("hehe"),"1","2");
        System.out.println("result = " + result);
    }

}
