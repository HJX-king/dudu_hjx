package com.hjx.service.impl;

import com.hjx.mapper.MeetingtypeMapper;
import com.hjx.po.Meetingtype;
import com.hjx.service.MeetingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Company:
 * @Auther:xin
 * @Date:2019/11/28
 * @Time:19:17
 */
@Service
public class MeetingTypeServiceImpl implements MeetingTypeService {

    @Autowired
    private MeetingtypeMapper meetingtypeMapper;
    @Override
    public List<Meetingtype> selectType() {

        return meetingtypeMapper.selectType();
    }
}
