package com.hjx.mapper;

import com.hjx.po.Meetingtype;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MeetingtypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Meetingtype record);

    int insertSelective(Meetingtype record);

    Meetingtype selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Meetingtype record);

    int updateByPrimaryKey(Meetingtype record);

    List<Meetingtype> selectType();
}