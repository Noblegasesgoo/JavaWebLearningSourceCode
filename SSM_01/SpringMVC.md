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



# 4、RestFul风格



## 4.1 概念：

- 就是一种资源定位及操作风格而已，不是协议也不是标准。



## 4.2 测试

> 编写一个测试类：

- 在springmvc中我们可以使用**@PathVariable**注解让方法的参数的值对应绑定到一个URL模板变量上。
- 我们点开RequestMapping的源码可以看到，我们还可以为该注解添加好多参数，其中一个就是method参数。
- method的参数存于枚举RequestMethod中。

```java
package com.zhao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RestFulController {

    @RequestMapping(value = "/add/{a}/{b}", method = RequestMethod.GET)
    public String test1(@PathVariable int a, @PathVariable int b, Model model){

        int result = a + b;
        model.addAttribute("msg", "Hello!RestFul and result = " + result);

        return "test1";
    }
}
```

> 我们可以精简一下我们的方法：

```java
package com.zhao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RestFulController {

    //@RequestMapping(value = "/add/{a}/{b}", method = RequestMethod.GET)
    @GetMapping(value = "/add/{a}/{b}")
    public String test1(@PathVariable int a, @PathVariable int b, Model model){

        int result = a + b;
        model.addAttribute("msg", "Hello!RestFul and result = " + result);

        return "test1";
    }
}
```

> 我们把注解替换了！！！我们@RequestMapping(value = "/add/{a}/{b}", method = RequestMethod.GET)这个注解，可以对应的换成method参数的同样名称的@xxxMapping就可以了。



## 4.3 深入

> 我们此时来两个方法，请求路径一样，但是请求方法一个是post一个是get。

```java
package com.zhao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RestFulController {

    //@RequestMapping(value = "/add/{a}/{b}", method = RequestMethod.GET)
    @GetMapping(value = "/add/{a}/{b}")
    public String test1(@PathVariable int a, @PathVariable int b, Model model){

        int result1 = a + b;
        model.addAttribute("msg", "Hello!RestFul and result1 = " + result1);

        return "test1";
    }

    @PostMapping(value = "/add/{a}/{b}")
    public String test2(@PathVariable int a, @PathVariable int b, Model model){

        int result2 = a + b;
        model.addAttribute("msg", "Hello!RestFul and result2 = " + result2);

        return "test2";
    }
}
```

> 我们再新建一个表单test2.jsp

```xml
<%--
  Created by IntelliJ IDEA.
  User: noblegasesgoo
  Date: 2021/4/25
  Time: 12:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="/springmvc_04_RestFul_war_exploded/add/1/2" method="post">
        <input type="submit">
    </form>

</body>
</html>

```

> 总结：

- 运行结果，我们通过test2.jsp提交申请访问的网址和直接输入/add/1/2的网址一样，但是我们提交到后台的方法不一样，所以我们地址就像是重用了一样。
- 此时通过test2.jsp提交的地址 **localhost:8080/springmvc_04_RestFul_war_exploded/add/1/2** 接收的方法为 **POST** 方法。
- 通过直接 **localhost:8080/springmvc_04_RestFul_war_exploded/add/1/2** 接收的方法为 **GET** 方法。
- 虽然最终地址一样，但是他们的结果不一样。



# 5、什么是json



## 5.1 个人理解：

>  json就是一种前后端数据交互的中间文件格式。

## 5.2 测试：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <!-- 来个脚本 -->
    <script type="text/javascript">
        // 定义一个javascript对象。

        var User = {
            name: "zlm",
            age:3,
            sex:"m"
        };

        console.log(User);

        //将js对象转换为json对象。

         var json = JSON.stringify(User);

         console.log(json);

         //将json对象转为javascript对象。

        var object = JSON.parse(json);

        console.log(object);
    </script>

</head>
<body>

</body>
</html>
```

# 6、Jackson使用

## 6.1 什么是jackson

> Jackson 是当前用的比较广泛的，用来序列化和反序列化 json 的 Java 的开源框架。



## 6.2 尝试实现：

> 1、导入jackson依赖：

```xml
        <!-- jackson依赖  -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.12.2</version>
        </dependency>
```

> 2、创建一个实体类及其控制器：

```java
package com.zhao.pojo;

public class User {
    private String name;
    private int age;
    private String sex;

    public User() {

    }

    public User(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }
}
```

```java
package com.zhao.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhao.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @RequestMapping("/j1")
    @ResponseBody//只要你加了这个注解，他就不会走视图解析器，不会去找页面，会直接返回一个字符串。
    public String json1() throws JsonProcessingException {

        //1、 jackson中的ObjectMapper类。
        ObjectMapper mapper = new ObjectMapper();

        //2、 创建一个对象。
        User user = new User("zlm", 20, "m");

        String s = mapper.writeValueAsString(user);

        return s;
    }
}

```



==注意点：==

- json对象他就是一个字符串

所以我们的延申内容：

```java
package com.zhao.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zhao.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class UserController {

    @RequestMapping("/j1")
    @ResponseBody//只要你加了这个注解，他就不会走视图解析器，不会去找页面，会直接返回一个字符串。
    public String json1() throws JsonProcessingException {

        //1、 jackson中的ObjectMapper类。
        ObjectMapper mapper = new ObjectMapper();

        //2、 创建一个对象。
        User user = new User("zlm", 20, "m");

        String s = mapper.writeValueAsString(user);

        return s;
    }

    @RequestMapping("/j2")
    @ResponseBody//只要你加了这个注解，他就不会走视图解析器，不会去找页面，会直接返回一个字符串。
    public String json2() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        List<User> users = new ArrayList<User>();


        User user1 = new User("zlm1", 20, "m");
        User user2 = new User("zlm2", 20, "m");
        User user3 = new User("zlm3", 20, "m");

        users.add(user1);
        users.add(user2);
        users.add(user3);

        String s = mapper.writeValueAsString(users);

        return s;
    }

    //Jackson实现时间显示。
    @RequestMapping("/j3")
    @ResponseBody//只要你加了这个注解，他就不会走视图解析器，不会去找页面，会直接返回一个字符串。
    public String json3() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        mapper.setDateFormat(simpleDateFormat);

        Date date = new Date();

        return mapper.writeValueAsString(date);
    }
}
```



我们可以把这个内容给他做成一个工具类：

```java
package com.zhao.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

public class JsonUtil {

    //源码复用思想。
    public static String getJson(Object object) throws JsonProcessingException {
        return getJson(object, "yyyy-MM-hh HH:mm:ss");
    }

    //sdf:simpledateformat
    public static String getJson(Object object, String sdf) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sdf);
        mapper.setDateFormat(simpleDateFormat);

        return mapper.writeValueAsString(object);
    }
}
```

