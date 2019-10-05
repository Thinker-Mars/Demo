package com.cone.orange.service.user;

import com.cone.orange.dao.LoginDao;
import com.cone.orange.helper.TokenHelper;
import com.cone.orange.model.OperInfo;
import com.cone.orange.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Cone
 * @Date 2019/9/21 17:06
 */
@Service
public class LoginService {

    @Autowired
    private LoginDao loginDao;

    public OperInfo queryUser(User bean) {
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
