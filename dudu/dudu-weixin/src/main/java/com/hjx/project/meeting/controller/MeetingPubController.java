package com.hjx.project.meeting.controller;

import com.hjx.po.Meetingpub;
import com.hjx.service.MeetingPubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description:
 * @Company:
 * @Auther:xin
 * @Date:2019/11/28
 * @Time:20:39
 */
@Controller
@RequestMapping("meetingPub")
public class MeetingPubController {
    @Autowired
    private MeetingPubService meetingPubService;
    @ResponseBody
    @RequestMapping("add")
    public int meetingPubAdd(Meetingpub meetingpub){
        int num=meetingPubService.insertSelective(meetingpub);
        return num;
    }

    /**
     * 我的会议
     * @param
     * @return
     */

    @ResponseBody
    @RequestMapping("myMeeting")
    public List<Meetingpub> myMeeting(@RequestParam("uid") String uid){
        List<Meetingpub> list= meetingPubService.selectMeetingPubByUid(uid);
        return  list;
    }

    /**
     * 会议-->会议抢单
     * 可抢单者列表
     * @param uid
     * @return
     */

    @ResponseBody
    @RequestMapping("grabList")
    public List<Meetingpub> selectGrabList(@RequestParam("uid") String uid,
                                           @RequestParam("tname") String tname){
        List<Meetingpub> list= meetingPubService.selectGrabList(uid,tname);
        return  list;
    }

}
