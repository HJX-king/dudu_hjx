package com.hjx.po;

import java.io.Serializable;
import java.util.Date;

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

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode == null ? null : pcode.trim();
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime == null ? null : ptime.trim();
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname == null ? null : tname.trim();
    }

    public String getPtitle() {
        return ptitle;
    }

    public void setPtitle(String ptitle) {
        this.ptitle = ptitle == null ? null : ptitle.trim();
    }

    public String getPzone() {
        return pzone;
    }

    public void setPzone(String pzone) {
        this.pzone = pzone == null ? null : pzone.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pcode=").append(pcode);
        sb.append(", ptime=").append(ptime);
        sb.append(", tname=").append(tname);
        sb.append(", ptitle=").append(ptitle);
        sb.append(", pzone=").append(pzone);
        sb.append(", uid=").append(uid);
        sb.append(", remark=").append(remark);
        sb.append(", createdate=").append(createdate);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}