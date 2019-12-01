package com.hjx.mapper;

import com.hjx.po.Meetinggrab;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetinggrabMapper {
    int deleteByPrimaryKey(String id);

    int insert(Meetinggrab record);

    int insertSelective(Meetinggrab record);

    Meetinggrab selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Meetinggrab record);

    int updateByPrimaryKey(Meetinggrab record);

    List<Meetinggrab> selectGrabListByPid(String pid);

    /**
     * 就选你功能
     * 1.现将所有的抢单根据皮带改为匹配失败,grabStatus=2
     * 2.将指定的用户(作为讲者)该状态为1 匹配成功
     */
    int updateMeetingGrabMatchFail(String pid);

    int updateMeetingGrabSucc(String pid,String uid);
}