package com.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPermission {
    String[] roles();   //用于指定允许执行该操作的角色
    boolean allowSelf() default false;  //用于指定是否允许对自己的操作进行权限检查，注解的参数不能像方法参数一样指定默认值
}