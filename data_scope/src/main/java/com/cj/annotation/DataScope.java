package com.cj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataScope {
    // 表的别名，，如果你用了别名，后面动态生成的sql也必须有别名
    String deptAlias() default "";
    String userAlias() default "";
}
