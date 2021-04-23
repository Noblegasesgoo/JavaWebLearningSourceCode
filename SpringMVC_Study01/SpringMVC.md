# 1、Hello！SpringMVC

## 1.1 什么是MVC：

- M（model）V（view）C（controller）。
- 模型（dao、service层等）、视图（jsp、html层等）、控制（servlet层等），是一个软件设计的规范。
- 最典型的MVC就是：jsp + servlet + javabean的模式。

## 1.2 MVC框架的作用：

- 可以封装用户提交的数据。
- 处理请求 -> 调用业务相关处理逻辑 -> 封装响应数据。
- 将url映射到java类或类中方法上。
- 将响应的数据进行渲染到html页面等表示层。



# 2、初识SpringMVC

## 2.1 基础配置文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"/>
    <bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter"/>

    <!-- 视图解析器：DispatcherServlet给他的ModleAndView -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="internalResourceViewResolver">
        <!-- 配置前缀 -->
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <!-- 配置后缀 -->
        <property name="suffix" value=".jsp"/>
    </bean>

</beans>
```

## 2.2 基础测试类com.zhao.controller.HelloController.class：

```java
package com.zhap.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//我们先继承和导入org.springframework.web.servlet.mvc包下的Controller接口。
public class HelloController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        //创建一个ModelAndView（模型和视图）。
        ModelAndView modelAndView = new ModelAndView();

        //封装一个对象，放在我们的ModelAndView中。
        modelAndView.addObject("message", "Hello!SpringMVC!");

        //封装要跳转的视图，同样放在我们的ModelAndView中。
        //在有了xml文件中的配置之后，这下面这条语句的hellomvc的路径就等同于/WEB-INF/jsp/hellomvc.jsp
        modelAndView.setViewName("hellomvc");

        return modelAndView;
    }
}
```



# 3、注解开发SpringMVC

## 3.1 步骤流程

- 新建项目，导入对应jar包之后在项目结构中添加lib目录。
- 配置web.xml文件，注册DispatcherServlet。
- 编写applicationContext.xml文件。
- 创建对应的控制类。
- 最后做视图与controller之间的对应。

## 3.2 代码

- web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>

        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath*:applicationContext.xml</param-value>
        </init-param>

        <load-on-startup> 1 </load-on-startup>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"

       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd

       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd

       http://www.springframework.org/schema/mvc
       http://www.springframework.org/schema/mvc/spring-mvc.xsd">


    <!-- 自动扫描包，让所有指定包下的注解生效，并且由ioc容器统一管理 -->
    <context:component-scan base-package="com.zhao.controller"/>

    <!-- 设置SpringMVC不处理静态资源 -->
    <mvc:default-servlet-handler/>

    <!--
        支持mvc注解驱动：
            在spring中一般采用@RequestMapping注解来完成映射的关系，

            要想使@RequestMapping注解生效，必须在context中注册DefaultAnnotationHandlerMapping
            以及一个AnnotationMethodHandlerAdapter实例
            这两个实例分别在类级别和方法级别处理。

            而我们的annotation-driven配置帮助我们自动完成上述两个实例的注入。
    -->
    <mvc:annotation-driven/>

    <!-- 视图解析器 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!-- 配置前缀 -->
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <!-- 配置后缀 -->
        <property name="suffix" value=".jsp"/>
    </bean>

</beans>
```

Controller包中的测试类：

```java
package com.zhao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

//这里就不用自己手动去applicationContext.xml之中去配置这个类的bean了。
@Controller
@RequestMapping("/HelloController")
public class HelloController {

    //下面这个方法真实的访问地址：/HelloController/Hello
    @RequestMapping("/Hello")
    public String hello(Model model){
        //Model可以向模型中添加属性msg与值，可以在JSP页面中去除并且渲染。
        //具体是哪个jsp页面这就得看return的值以及@RequestMapping注解的初始值。
        model.addAttribute("msg", "Hello,Annotation-SpringMVC");

        //返回的是我们视图的名字，会被视图解析器处理。
        return "Hello";
    }

}
```





