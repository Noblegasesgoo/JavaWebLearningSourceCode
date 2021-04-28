package com.zhao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/c2")
public class ControllerDemo02 {

    @RequestMapping("/hello2")
    public String test01(Model model){
        model.addAttribute("msg","Test01");

        return "hello2";
    }

}
