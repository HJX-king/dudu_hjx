package com.hjx.project.weixin.api.accessToken;

import com.hjx.project.weixin.main.MenuManager;
import com.hjx.project.weixin.util.WeixinUtil;
import net.sf.json.JSONObject;

/**
 * @Description:
 * @Company:
 * @Auther:xin
 * @Date:2019/11/22
 * @Time:19:04
 */
public class AccessTokenThread extends Thread{
    private  static  final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    public static String access_token;
    @Override
    public void run() {
        while (true){
            try {
                access_token=getAccessToken();
                System.out.println(access_token);
                Thread.sleep(720000L);
                }catch (Exception e){
                    e.printStackTrace();
                }

        }
    }

    /**
     * 向微信服务器发送get请求,获取access_token
     * @return
     */
    private String getAccessToken(){
        String requestUrl=ACCESS_TOKEN_URL.replace("APPID", MenuManager.appId).replace("APPSECRET",MenuManager.appSecret);
        //{"access_token":"ACCESS_TOKEN","expires_in":7200}
        JSONObject jsonObject=WeixinUtil.httpRequest(requestUrl,"GET",null);
        String result=(String)jsonObject.get("access_token");
        return result;

    }
}
