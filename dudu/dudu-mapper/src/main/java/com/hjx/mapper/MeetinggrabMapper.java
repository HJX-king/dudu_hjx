package com.hjx.mapper;

import com.hjx.po.Meetinggrab;

public interface MeetinggrabMapper {
    int deleteByPrimaryKey(String id);

    int insert(Meetinggrab record);

    int insertSelective(Meetinggrab record);

    Meetinggrab selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Meetinggrab record);

    int updateByPrimaryKey(Meetinggrab record);
}