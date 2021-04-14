package com.zhao.diy;

//方式三：注解实现aop

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//被@Aspect注解标注代表这个类是一个切面。
//在xml文件中就会被自动装配为bean切面。
@Component
@Aspect
public class AnnotationPointCut {

    //被@Before注解标记的方法就可以理解为通知。
    @Before("execution(* com.zhao.service.UserServiceImpl.* (..))")
    public void before(){
        System.out.println("注解实现aop：方法执行前。");
    }

    @After("execution(* com.zhao.service.UserServiceImpl.* (..))")
    public void after(){
        System.out.println("注解实现aop：方法执行后。");
    }

    @Around("execution(* com.zhao.service.UserServiceImpl.* (..))")
    public void around(){

    }

}
