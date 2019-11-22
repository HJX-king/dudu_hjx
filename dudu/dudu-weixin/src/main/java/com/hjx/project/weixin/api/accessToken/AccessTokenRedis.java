package com.hjx.project.weixin.api.accessToken;

import com.hjx.project.weixin.main.MenuManager;
import com.hjx.project.weixin.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Company:
 * @Auther:xin
 * @Date:2019/11/22
 * @Time:20:43
 */
@Service
public class AccessTokenRedis {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Resource(name = "redisTemplate")
    private ValueOperations<String, Object> string;
    private static final String REDIS_ACCESS_TOKEN="ACCESS_TOKEN";
    private  static  final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";


    /**
     * key如果存在,到redis中进行查询
     * 如果不存在,在微信服务器中获得请求结果,存入redis中
     */

    public String getAccessTokenVal(){
        if(redisTemplate.hasKey(REDIS_ACCESS_TOKEN)){
            String result=(String) string.get(REDIS_ACCESS_TOKEN);
            System.out.println("redis获取的accesstoken:"+result);
            return result;
        }else {
            String AccessTokenVal=getAccessTokenByRedis();
            string.set(REDIS_ACCESS_TOKEN,AccessTokenVal,2, TimeUnit.HOURS);
            return AccessTokenVal;
        }

    }
    private String getAccessTokenByRedis(){
        String requestUrl=ACCESS_TOKEN_URL.replace("APPID", MenuManager.appId).replace("APPSECRET",MenuManager.appSecret);
        //{"access_token":"ACCESS_TOKEN","expires_in":7200}
        JSONObject jsonObject= WeixinUtil.httpRequest(requestUrl,"GET",null);
        String result=(String)jsonObject.get("access_token");
        return result;

    }
}
