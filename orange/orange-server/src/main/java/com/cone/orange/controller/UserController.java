package com.cone.orange.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户模式
 * @Author Cone
 * @Date 2019/9/14 15:21
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/halo")
    @ResponseBody
    public String halo() {

        return "halo";
    }
}
