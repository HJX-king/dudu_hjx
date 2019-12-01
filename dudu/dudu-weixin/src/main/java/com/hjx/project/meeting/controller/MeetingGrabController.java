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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Company:
 * @Auther:xin
 * @Date:2019/11/30
 * @Time:14:25
 */

@Controller
public class MeetingGrabController {
    @Autowired
    private MeetingGrabService meetingGrabService;
    @Autowired
    private MeetingPubService meetingPubService ;
    /**
     * 会议抢单-->
     *新用法:用map传值
     */
    @RequestMapping("meetingGrab/ToaddMeeting")
    public String ToaddMeeting(@RequestParam("uid") String uid
            , @RequestParam("pid") String pid, Map<String,Object> map){
        map.put("uid",uid);
        map.put("pid",pid);
        return "/weixin/meetingGrab/meetingGrabAdd";

    }
    @RequestMapping("meetingGrab/addMeeting")
    public ModelAndView  insertMeetingGrab(Meetinggrab meetinggrab){
        ModelAndView modelAndView=new ModelAndView();
        meetingGrabService.insertMeetingGrab(meetinggrab);
        //return "weixin/meetingPub/meetingPub";
        //如果类上@RequestMapping("meetingGrab"),会变成meetingGrab/weixin/meetingPub/meetingPub
        modelAndView.setViewName("weixin/meetingGrab/meetingGrab");
        //传值
        modelAndView.addObject("uid",meetinggrab.getUid());


        return modelAndView;
    }

    @RequestMapping("meetingGrab/myMeeting")
    @ResponseBody
    public List<Meetingpub> selectMyMeetingList(String uid){
        List<Meetingpub> list=meetingPubService.selectMyMeetingList(uid);
        return list;
    }



}
