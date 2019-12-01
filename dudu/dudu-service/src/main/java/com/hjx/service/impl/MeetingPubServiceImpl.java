package com.hjx.service.impl;

import com.hjx.mapper.MeetingpubMapper;
import com.hjx.mapper.MeetingtypeMapper;
import com.hjx.po.Meetingpub;
import com.hjx.service.MeetingPubService;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Description:
 * @Company:
 * @Auther:xin
 * @Date:2019/11/28
 * @Time:20:28
 */
@Service
public class MeetingPubServiceImpl implements MeetingPubService {

    @Autowired
    private MeetingpubMapper meetingpubMapper;

    /**
     * 微信端 会议发单功能
     * @param record
     * @return
     */
    @Override
    public int insertSelective(Meetingpub record) {
        //使用uuid为主键
        record.setId(UUID.randomUUID().toString());

        record.setCreatedate(new Date());
        record.setPcode(pcodeGet(record.getPtime()));//按规则
        record.setStatus((short) 1);
        return meetingpubMapper.insertSelective(record);
    }

    @Override
    public List<Meetingpub> selectMeetingPubByUid(String uid) {
        return meetingpubMapper.selectMeetingPubByUid(uid);
    }

    @Override
    public List<Meetingpub> selectGrabList(String uid, String tname) {
        return meetingpubMapper.selectGrabList(uid, tname);
    }

    @Override
    public List<Meetingpub> selectMyMeetingList(String uid) {
        return meetingpubMapper.selectMyMeetingList(uid);
    }

    /**
     * 根据会议召开日期进行生成
     * 1.时间进行字符串截取 20191130
     * 2.根据时间字符串去数据库查询
     * select max(pcode) from meeingpub where Left(pcode,8)='20191130'
     * 如果查询结果为null
     * 20191130+001
     * 不为null,将最大取出
     * 对其进行+1
     *
     */

    public String pcodeGet(String ptime){
        //2019-11-30
        String str=ptime.substring(0,10).replace("-","");
        String maxPcode=meetingpubMapper.selectPcode(str);
        if (StringUtils.isEmpty(maxPcode)){
            return str+"001";
        }else {
            Long l=Long.parseLong(maxPcode)+1;
            return l.toString();
        }

    }

}
