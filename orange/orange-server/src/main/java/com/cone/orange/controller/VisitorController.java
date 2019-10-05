package com.cone.orange.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 游客模式
 * @Author Cone
 * @Date 2019/9/14 15:20
 */
@Controller
@RequestMapping("/visitor")
public class VisitorController {

    @GetMapping("/halo")
    @ResponseBody
    public String halo() {

        return "halo";
    }


}
