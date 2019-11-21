package com.hjx.project.weixin.api.tuling.bean;

import lombok.Data;


/**
 * @Description:
 * @Company:
 * @Auther:xin
 * @Date:2019/11/21
 * @Time:17:53
 */
@Data
public class TulingBean {
    private  int reqType=0;
    private Perception perception;
    private UserInfo userInfo;


}
