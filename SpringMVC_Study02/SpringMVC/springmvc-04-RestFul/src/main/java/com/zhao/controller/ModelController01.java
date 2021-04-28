package com.zhao.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class ModelController01 {

    @RequestMapping("/m1/h1")
    public String test1(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model){

        HttpSession session = httpServletRequest.getSession();
        System.out.println(session.getId());

        model.addAttribute("msg", "/m1/h1");
        return "test1";
    }

}
