package com.cone.orange.dao;

import com.cone.orange.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 登录
 * @Author Cone
 * @Date 2019/9/21 15:38
 */
@Mapper
@Component
public interface LoginDao {

    /**
     * 用户登录
     * @param user
     * @return
     */
    User login(User user);

}
