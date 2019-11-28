package com.hjx.service;

import com.hjx.po.User;
import org.apache.ibatis.annotations.Select;

/**
 * @Description:
 * @Company:
 * @Auther:xin
 * @Date:2019/11/27
 * @Time:19:37
 */
public interface UserService {
    //#######TODO 后台端
    /**
     * 后台
     */


    //#######TODO * 微信端
    /**
     * 根据wid查询user对象是否存在
     *
     */
    User selectUserByWid(Integer wid);
    /**
     * 根据email查询user对象是否存在
     *
     */
    User selectUserByEmail(String email);
    /**
     * 执行登录(绑定功能)
     */
    int updateByEmail(User user);

    /**
     * 更新个人信息
     */
    int updateByPrimaryKeySelective(User record);


}
