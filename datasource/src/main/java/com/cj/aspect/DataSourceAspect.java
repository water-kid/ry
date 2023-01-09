package com.cj.aspect;

import com.cj.DataSource;
import com.cj.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
//@Order(20)
public class DataSourceAspect {

    // 方法上有注解  @annotation(com.cj.DataSource)
    //  类上面有注解，将类中的方法拦截下来  @within(com.cj.DataSource)
    @Pointcut(value = "@annotation(com.cj.DataSource) || @within(com.cj.DataSource)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        try {
            MethodSignature signature = (MethodSignature) pjp.getSignature();
//        signature.getMethod().getAnnotation()
            DataSource annotation;

            annotation = AnnotationUtils.findAnnotation(signature.getMethod(), DataSource.class);
            if (annotation ==null){
                annotation = AnnotationUtils.findAnnotation(signature.getDeclaringType(),DataSource.class);
                if (annotation == null){
                    return pjp.proceed();
                }
            }

            // 有这个注解
            String dsName = annotation.value();
            DynamicDataSourceContextHolder.setDataSourceType(dsName);
           return  pjp.proceed();
        } catch (Throwable throwable) {
            // 将异常 扔出去
            throwable.printStackTrace();
            throw throwable;
        } finally {
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }
}
