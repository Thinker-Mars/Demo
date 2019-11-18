package com.cone.orange.service.login;

import com.cone.orange.dao.login.LoginDao;
import com.cone.orange.helper.TokenHelper;
import com.cone.orange.model.base.OperInfo;
import com.cone.orange.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author Cone
 * @Date 2019/9/21 17:06
 */
@Service
@Slf4j
public class LoginService {

    @Autowired
    private LoginDao loginDao;

    public OperInfo queryUser(User bean) {
        log.info("用户登录中...");
        OperInfo operInfo = new OperInfo();
        User user = loginDao.login(bean);
        if (null == user) {
            operInfo.setFlag(false);
            operInfo.setInfo("用户名或密码错误");
        } else {
            operInfo.setData("token", TokenHelper.createToken(String.valueOf(user.getId()), user.getPassword()));
        }
        return operInfo;
    }
}
