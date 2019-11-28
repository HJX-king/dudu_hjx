package com.hjx.service;

import com.hjx.po.Meetingpub;

import java.util.List;

/**
 * @Description:
 * @Company:
 * @Auther:xin
 * @Date:2019/11/28
 * @Time:19:05
 */
public interface MeetingPubService {
    int insertSelective(Meetingpub record);

    List<Meetingpub> selectMeetingPubByUid(String uid);

}
