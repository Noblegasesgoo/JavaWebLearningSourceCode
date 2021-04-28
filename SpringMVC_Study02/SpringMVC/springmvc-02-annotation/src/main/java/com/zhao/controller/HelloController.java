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
