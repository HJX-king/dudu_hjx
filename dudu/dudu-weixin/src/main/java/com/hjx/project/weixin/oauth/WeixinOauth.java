package com.hjx.project.weixin.oauth;

import com.hjx.project.weixin.main.MenuManager;
import com.hjx.project.weixin.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Description:
 * @Company:
 * @Auther:xin
 * @Date:2019/11/26
 * @Time:20:10
 */
@RequestMapping("oauth")
@Controller("weixinOauth1")
public class WeixinOauth {
    @RequestMapping("weixin")
    public void oauth(HttpServletResponse response) throws IOException {
        //当用户完成请求之后要跳到的路径
        String redirect_uri=MenuManager.REAL_URL+"oauth/invoke";
        //授权后重定向的回调链接地址， 请使用 urlEncode 对链接进行处理
        try {
            redirect_uri=URLEncoder.encode(redirect_uri,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //第一步,用户同意授权,获取code
        String url="https://open.weixin.qq.com/connect/oauth2/authorize?"+
                "appid="+ MenuManager.appId+
                "&redirect_uri="+redirect_uri+
                "&response_type=code"+
                "&scope=snsapi_userinfo"+
                "&state=hjx"+
                "#wechat_redirect";
        response.sendRedirect(url);

    }

    /**
     * 如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE。
     * @return
     */
    @RequestMapping("invoke")
    public String invoke(HttpServletRequest request){
        //得到code
        String code=request.getParameter("code");
        System.out.println("code"+code);

        //第二步：通过code换取网页授权access_token
        //获取code后，请求以下链接获取access_token：  https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
        //认证服务器的接口
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?"+
                "appid="+MenuManager.appId+
                "&secret="+MenuManager.appSecret+
                "&code="+code+
                "&grant_type=authorization_code";
        //发送请求
        JSONObject jsonObject=WeixinUtil.httpRequest(url,"GET",null);
        System.out.println(jsonObject.toString());
        //得到access_token
        String access_token=jsonObject.getString("access_token");
        String openid=jsonObject.getString("openid");
        //第四步：拉取用户信息(需scope为 snsapi_userinfo)
        //http：GET（请使用https协议） https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
        String userInfoUrl="https://api.weixin.qq.com/sns/userinfo?"+
                "access_token="+access_token+
                "&openid="+openid+
                "&lang=zh_CN";
        JSONObject jsonInfo=WeixinUtil.httpRequest(userInfoUrl,"GET",null);
        request.setAttribute("userInfo",jsonInfo);



        return "oauth";
    }
}
