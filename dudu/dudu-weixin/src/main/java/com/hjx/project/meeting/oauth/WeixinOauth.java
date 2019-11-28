package com.hjx.project.meeting.oauth;

import com.hjx.po.User;
import com.hjx.po.Weiuser;
import com.hjx.project.weixin.main.MenuManager;
import com.hjx.project.weixin.util.WeixinUtil;
import com.hjx.service.UserService;
import com.hjx.service.WeiuserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.soap.SOAPBinding;
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
@Controller
public class WeixinOauth {
    @Autowired
    private UserService userService;
    @Autowired
    private WeiuserService weiuserService;

    /**
     * 点击个人中心
     * @param response
     * @throws IOException
     */
    @RequestMapping("weixin/user")
    public void oauth(HttpServletResponse response) throws IOException {
        //当用户完成请求之后要跳到的路径
        String redirect_uri=MenuManager.REAL_URL+"oauth/weixin/user/invoke";
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
    @RequestMapping("weixin/user/invoke")
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
        /**
         * 判断用户是否进行绑定
         * 1.根据openid查询weiuser表 得到主键id
         * 2.根据wid去查询user表是否存在
         */
        Weiuser weiuser=weiuserService.selectByOpenId(openid);
        if(weiuser!=null){
           //根据wid判断当前微信用户是否进行登录(绑定)功能
            User user=userService.selectUserByWid(weiuser.getId());
            if(user==null){//未绑定,跳到登录页面
                request.setAttribute("wid",weiuser.getId());
                return "weixin/login";
            }else {//已绑定,跳到目标页面
                request.setAttribute("user",user);
                return "weixin/user/userInfo";
            }

        }else{//微信的个人信息在我库中不存在
            System.out.println("该用户微信个人信息不存在"+openid);
        }
        return "oauth";
    }
    /**
     * 点击会议发布
     */
    @RequestMapping("weixin/meetingPub")
    public void oauthMeetingPub(HttpServletResponse response) throws IOException {
        //当用户完成请求之后要跳到的路径
        String redirect_uri=MenuManager.REAL_URL+"oauth/weixin/meetingPub/invoke";
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
    @RequestMapping("weixin/meetingPub/invoke")
    public String meetingPubinvoke(HttpServletRequest request){
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
        //得到openid
        String openid=jsonObject.getString("openid");
        /**
         *1.数据库weiuser表中是否有数据,没有-->提示重新关注
         * 2.根据wid查user表,是否绑定,没绑定-->跳到登录页面
         *
         */
        System.out.println(openid);
        Weiuser weiuser=weiuserService.selectByOpenId(openid);
        if(weiuser==null){
            System.out.println("该用户微信个人信息不存在"+openid);
        }else{
            User user=userService.selectUserByWid(weiuser.getId());
            if (user==null){
                request.setAttribute("wid",weiuser.getId());
                return "weixin/login";
            }else {//已绑定,判断角色
                if(1==user.getRid()){//发单组
                    request.setAttribute("uid",user.getId());
                    return "weixin/meetingPub/meetingPub";

                }else if(2==user.getRid()) {//抢单组
                    return "weixin/unauth";
                }else {//其他情况
                    return "";
                }

            }
        }

        return "oauth";
    }
}
