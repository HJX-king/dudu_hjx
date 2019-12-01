package com.hjx.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Meetingpub implements Serializable {
    private String id;

    private String pcode;

    private String ptime;

    private String tname;

    private String ptitle;

    private String pzone;

    private String uid;

    private String remark;

    private Date createdate;

    private Short status;

    private Meetinggrab meetinggrab;

    private static final long serialVersionUID = 1L;


}