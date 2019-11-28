package com.hjx.service;

import com.hjx.po.Weiuser;

/**
 * @Description:
 * @Company:
 * @Auther:xin
 * @Date:2019/11/26
 * @Time:17:53
 */
public interface WeiuserService {
    /**
     * 根据openid判断weiuser表中是否存在该对象信息
     */
    Weiuser selectByOpenId(String openId);
    /**
     * 添加功能
     */
    int addWeruser(Weiuser record);
}
