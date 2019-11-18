package com.cone.orange.controller.login;

import com.cone.orange.model.base.OperInfo;
import com.cone.orange.model.user.User;
import com.cone.orange.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *  用户登录控制
 * @Author Cone
 * @Date 2019/9/21 15:19
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @ResponseBody
    public OperInfo login(User user) {

        return loginService.queryUser(user);
    }

}
