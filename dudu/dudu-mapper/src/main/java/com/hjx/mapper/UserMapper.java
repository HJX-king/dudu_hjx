package com.hjx.mapper;

import com.hjx.po.User;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 根据wid查询user对象是否存在
     */
    User selectUserByWid(Integer wid);
    /**
     * 根据邮箱查询user对象是否存在
     */
    User selectUserByEmail(String email);
    /**
     * 执行登录(绑定功能)
     */
    int updateByEmail(User user);
}