package com.cj.aspect;

import com.cj.DataSource;
import com.cj.DataSourceType;
import com.cj.DynamicDataSource;
import com.cj.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

//@Component
//@Aspect
//@Order(1) // 越小的在越前面
public class GlobalDataSourceAspect {
    @Pointcut("execution(* com.cj.service.*.*(..))")
    public void pc(){}

    @Autowired
    HttpSession httpSession;

    @Around("pc()")
    public Object around(ProceedingJoinPoint pjp){
        DynamicDataSourceContextHolder.setDataSourceType(((String) httpSession.getAttribute(DataSourceType.DS_SESSION_KEY)));
        try {
            pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }finally {
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
        return null;
    }
}
