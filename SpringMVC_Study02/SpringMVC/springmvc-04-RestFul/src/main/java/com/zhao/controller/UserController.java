package com.zhao.controller;

import com.zhao.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    //localhost:8080/user/t1?name=xxx
    @GetMapping("/t1")
    public String test1(@RequestParam("username") String name, Model model){

        //1.接受前端参数。
        System.out.println("接收到前端的参数为：" + name);

        //2.将返回结果传到前端。
        model.addAttribute("msg", "返回参数：" + name + "处理");

        return "test1";
    }

    //前端接受的是一个对象。
    //http://localhost:8080/springmvc_04_RestFul_war_exploded/user/t2?id=1&name=zlm&age=2
    @GetMapping("/t2")
    public String test2(User user){

        System.out.println(user);

        return "test1";
    }

}
