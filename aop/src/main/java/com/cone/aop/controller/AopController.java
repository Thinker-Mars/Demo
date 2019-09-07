package com.cone.aop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author Cone
 * @Date 2019/9/7 21:13
 */
@Controller
@RequestMapping("/test")
public class AopController {

    @RequestMapping("/halo")
    @ResponseBody
    public String halo() {
        return "Halo!";
    }

    @RequestMapping("/error")
    @ResponseBody
    public Object error() {
        return 1 / 0;
    }
}
