package com.cj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
// Controller的方法上，，可以通过aop处理，，也可以通过拦截器处理（因为加载接口上面的）
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RepeatSubmit {
    // 两个请求之间的间隔时间
    int interval() default 5000;

    // 自定义提示文本
    String message() default "不允许重复提交，请稍后再试";
}
