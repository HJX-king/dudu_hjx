package com.hjx.project.meeting.controller;

import com.hjx.po.Meetinggrab;
import com.hjx.po.Meetingpub;
import com.hjx.service.MeetingGrabService;
import com.hjx.service.MeetingPubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private MeetingGrabService meetingGrabService;
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
    /**
     * 会议-->我的会议-->选择讲者
     */
    @RequestMapping("chooseGrabListToPage")
    public String chooseGrabListToPage(@RequestParam("uid") String uid,
                                       @RequestParam("pid") String pid, HttpServletRequest request){
        request.setAttribute("uid",uid);
        request.setAttribute("pid",pid);
        return "weixin/meetingPub/grabList";
    }
    /**
     * 加载讲者的列表信息
     */
    @RequestMapping("GrabList")
    @ResponseBody
    public List<Meetinggrab>selectGrabListByPid(@RequestParam("pid") String pid){
        List<Meetinggrab> list=meetingGrabService.selectGrabListByPid(pid);
        return list;
    }
    /**
     * 就选你
     */
    @RequestMapping("chooseGrabList")
    @ResponseBody
    public int chooseGrabList(@RequestParam("pid") String pid,
                                 @RequestParam("uid") String uid){
        int num=0;
        try {
                num=meetingGrabService.chooseMeetingGrab(pid, uid);

            }catch (RuntimeException e){
                e.printStackTrace();
            }
        return num;
    }

}
