package com.hjx.mapper;

import com.hjx.po.Meetingpub;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingpubMapper {
    int deleteByPrimaryKey(String id);

    int insert(Meetingpub record);

    int insertSelective(Meetingpub record);

    Meetingpub selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Meetingpub record);

    int updateByPrimaryKey(Meetingpub record);
    /**
     * 会议编号查询
     */
    String selectPcode(String pcode);

    List<Meetingpub> selectMeetingPubByUid(String uid);

    /**
     * bean
     * map方式
     * arg0,arg1-->param1,parm2
     * tname=-1时 代表用户要查所有数据
     * @param uid
     * @param tname
     * @return
     */
    List<Meetingpub> selectGrabList(String uid,String tname);
}