package com.hjx.service.impl;

import com.hjx.mapper.WeiuserMapper;
import com.hjx.po.Weiuser;
import com.hjx.service.WeiuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Company:
 * @Auther:xin
 * @Date:2019/11/26
 * @Time:17:53
 */
@Service
public class WeiuserServiceImpl implements WeiuserService {
    @Autowired
    private WeiuserMapper weiuserMapper;
    /**
     * 根据openid判断weiuser表中是否存在该对象信息
     */

    @Override
    public Weiuser selectByOpenId(String openId) {

        return weiuserMapper.selectByOpenId(openId);
    }

    @Override
    public int addWeruser(Weiuser record) {
        return weiuserMapper.insertSelective(record);
    }


}
