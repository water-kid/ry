package com.cj.annotation;

import com.cj.LimitType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RateLimiter {
    // 前缀
    String key() default "rate_limit : ";

    // 时间窗  60s
    int time() default 60;

    // 在时间窗内的 限流次数
    int count() default 5;

    // 限流类型
    LimitType limitType() default LimitType.DEFAULT;
}
