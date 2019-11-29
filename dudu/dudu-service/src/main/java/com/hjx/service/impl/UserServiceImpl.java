package com.hjx.service.impl;

import com.hjx.mapper.UserMapper;
import com.hjx.po.User;
import com.hjx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Company:
 * @Auther:xin
 * @Date:2019/11/27
 * @Time:19:48
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectUserByWid(Integer wid) {
        return userMapper.selectUserByWid(wid);
    }

    @Override
    public User selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    @Override
    public int updateByEmail(User user) {

        return userMapper.updateByEmail(user);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public User selectUserByOpenId(String openid) {
        return userMapper.selectUserByOpenId(openid);
    }
}
