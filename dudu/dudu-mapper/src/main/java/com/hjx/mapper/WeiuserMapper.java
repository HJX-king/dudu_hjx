package com.hjx.mapper;

import com.hjx.po.Weiuser;

public interface WeiuserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Weiuser record);

    int insertSelective(Weiuser record);

    Weiuser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Weiuser record);

    int updateByPrimaryKey(Weiuser record);

    /**
     * 根据openid判断weiuser表中是否存在该对象信息
     * @param openId
     * @return
     */

    Weiuser selectByOpenId(String openId);
}