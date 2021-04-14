package com.zhao.log;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class Log implements MethodBeforeAdvice {

    /*
        Method：要执行的目标对象的方法。
        Object[] args：参数。
        target：目标对象。
     */
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println(target.getClass().getName()+"类对象的"+method.getName()+"方法被执行了！！");
    }
}
