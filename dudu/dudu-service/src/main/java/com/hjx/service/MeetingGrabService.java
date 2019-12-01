package com.hjx.service;

import com.hjx.po.Meetinggrab;

import java.util.List;

/**
 * @Description:
 * @Company:
 * @Auther:xin
 * @Date:2019/11/30
 * @Time:15:00
 */
public interface MeetingGrabService {
    int insertMeetingGrab (Meetinggrab meetinggrab);

    /**
     * 微信端 会议发布-->我的会议
     * 根据会议发单id查询抢单人列表信息
     * @param pid
     * @return
     */
    List<Meetinggrab> selectGrabListByPid(String pid);

    int chooseMeetingGrab(String pid,String uid);
}
