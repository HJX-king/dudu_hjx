package com.hjx.po;

import java.io.Serializable;
import java.util.Date;

public class Meetinggrab implements Serializable {
    private String id;

    private String pid;

    private String remark;

    private String uid;

    private Date createdate;

    private Integer grabstatus;

    private Date grabdate;

    private Short status;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Integer getGrabstatus() {
        return grabstatus;
    }

    public void setGrabstatus(Integer grabstatus) {
        this.grabstatus = grabstatus;
    }

    public Date getGrabdate() {
        return grabdate;
    }

    public void setGrabdate(Date grabdate) {
        this.grabdate = grabdate;
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
        sb.append(", pid=").append(pid);
        sb.append(", remark=").append(remark);
        sb.append(", uid=").append(uid);
        sb.append(", createdate=").append(createdate);
        sb.append(", grabstatus=").append(grabstatus);
        sb.append(", grabdate=").append(grabdate);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}