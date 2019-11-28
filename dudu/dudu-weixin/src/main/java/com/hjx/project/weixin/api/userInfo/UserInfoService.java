package com.hjx.project.weixin.api.userInfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hjx.po.Weiuser;
import com.hjx.project.weixin.api.accessToken.AccessTokenRedis;
import com.hjx.project.weixin.util.WeixinUtil;
import com.hjx.service.WeiuserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Description:
 * @Company:
 * @Auther:xin
 * @Date:2019/11/26
 * @Time:21:18
 */
@Service
public class UserInfoService {
    @Autowired
    private AccessTokenRedis accessTokenRedis;
    @Autowired
    private WeiuserService weiuserService;
    private static final String USER_INFO_URL="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    /**
     * 收集个人信息
     * 1.得到用户的openid
     * 2.根据openid向微信服务器发送get请求得到用户授权
     * 3.得到用户是JSONObject-->转成weiuser对象
     * 4.将weiuser对象进行数据库的添加操作
     */
    public void userInfo(String openid){

        Weiuser weiuserObj=weiuserService.selectByOpenId(openid);
        if(weiuserObj==null){
            //得到用户的openid
            JSONObject jsonObject=getJSONObjectByOpenId(openid);
            //得到用户是JSONObject-->转成weiuser对象
            Weiuser weiuser=convertJSONWeiuser(jsonObject);
            //将weiuser对象进行数据库的添加操作
            int num=addWeiuser(weiuser);

        }else {

        }

    }

    public JSONObject getJSONObjectByOpenId(String openid){
        String url=USER_INFO_URL.replace("ACCESS_TOKEN",accessTokenRedis.getAccessTokenVal()).replace("OPENID",openid);
        JSONObject jsonObject=WeixinUtil.httpRequest(url,"GET",null);
        return jsonObject;

    }
    public Weiuser convertJSONWeiuser(JSONObject jsonObject){
        /**
         *Weiuser weiuser=new Weiuser();
         *         weiuser.setOpenid(jsonObject.getString("openid"));
         */
        ObjectMapper objectMapper=new ObjectMapper();
        Weiuser weiuser=null;
        try {
             weiuser=objectMapper.readValue(jsonObject.toString(),Weiuser.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return weiuser;
    }
    public int addWeiuser(Weiuser weiuser){
        return weiuserService.addWeruser(weiuser);
    }

}
