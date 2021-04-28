package com.zhao.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zhao.pojo.User;
import com.zhao.utils.JsonUtil;
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

        Date date = new Date();

        return JsonUtil.getJson(date, "yyyy-MM-dd HH:mm:ss");
    }
}
