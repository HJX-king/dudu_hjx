package com.hjx.service.impl;

import com.hjx.mapper.MeetinggrabMapper;
import com.hjx.po.Meetinggrab;
import com.hjx.service.MeetingGrabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Description:
 * @Company:
 * @Auther:xin
 * @Date:2019/11/30
 * @Time:15:01
 */
@Service
public class MeetingGrabServiceImpl implements MeetingGrabService {

    @Autowired
    private MeetinggrabMapper meetinggrabMapper;
    @Override
    public int insertMeetingGrab(Meetinggrab meetinggrab) {
        meetinggrab.setId(UUID.randomUUID().toString());
        meetinggrab.setCreatedate(new Date());
        meetinggrab.setGrabstatus(0);//默认为0 未匹配
        meetinggrab.setStatus((short) 1);
        return meetinggrabMapper.insertSelective(meetinggrab);
    }

    @Override
    public List<Meetinggrab> selectGrabListByPid(String pid) {
        List<Meetinggrab> list=meetinggrabMapper.selectGrabListByPid(pid);
        return list;
    }
    /**
     * 就选你功能
     * 1.现将所有的抢单根据皮带改为匹配失败,grabStatus=2
     * 2.将指定的用户(作为讲者)该状态为1 匹配成功
     *
     * spring 事务机制,如果遇到运行时异常 会回滚事务
     */
    @Override
    @Transactional
    public int chooseMeetingGrab(String pid, String uid) throws RuntimeException {

        int num=meetinggrabMapper.updateMeetingGrabMatchFail( pid);
        if (num<1){
            throw new RuntimeException("选择失败");
        }
        int num1=meetinggrabMapper.updateMeetingGrabSucc(pid,uid);
        if (num1<1){
            throw new RuntimeException("选择失败2");
        }
        return num;
    }
}
