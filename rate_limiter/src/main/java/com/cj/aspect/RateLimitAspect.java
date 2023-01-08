package com.cj.aspect;

import com.cj.LimitType;
import com.cj.annotation.RateLimiter;

import com.cj.exception.RateLimitException;
import jdk.nashorn.internal.ir.CallNode;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collections;


@Aspect
@Component
public class RateLimitAspect {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitAspect.class);

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    DefaultRedisScript redisScript;

    @Before("@annotation(rateLimiter)")
    public void before(JoinPoint joinpoint, RateLimiter rateLimiter) throws RateLimitException {
        int time = rateLimiter.time();
        int count = rateLimiter.count();

//        组合key ，，，   key: ip地址 - com.cj.controller.HelloController-方法名
        //
        String prefix = rateLimiter.key();
        LimitType limitType = rateLimiter.limitType();
        StringBuffer sb = new StringBuffer(prefix);
        if (limitType == LimitType.IP){
            // ip
            sb.append("ip address : ").append("-");
        }
        MethodSignature signature = (MethodSignature) joinpoint.getSignature();
        Method method = signature.getMethod();
        sb.append(signature.getDeclaringTypeName()).append("-").append(method.getName());

        try {
            Long number = (Long) redisTemplate.execute(redisScript, Collections.singletonList(sb.toString()), time, count);

            if (number == null || number>count){
                // 超过限流
                logger.info("当前接口已达到最大限流次数 :" +count);
                throw  new RateLimitException("访问过于频繁，请稍后访问");
            }
            logger.info("一个时间窗内请求次数 ： {}  当前请求次数 ： {}  ，缓存的key为： {}",count,number,sb.toString());
        } catch (Exception e) {

            // 要把exception抛出去，，全局处理，
            e.printStackTrace();
            throw e ;
        }


    }

}
