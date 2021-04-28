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

        return "test1";
    }

}
