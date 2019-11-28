package com.hjx.project.meeting.controller;

import com.hjx.po.Meetingtype;
import com.hjx.service.MeetingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description:
 * @Company:
 * @Auther:xin
 * @Date:2019/11/28
 * @Time:19:24
 */
@Controller
@RequestMapping("meetingType")
public class MeetingTypeController {
    @Autowired
    private MeetingTypeService meetingTypeService;

    /**
     * 在加载会议发布页面时,加载下拉框
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,value = "list")
    public List<Meetingtype> selectMeetingType(){
        List<Meetingtype> list=meetingTypeService.selectType();
        return list;
    }

}
