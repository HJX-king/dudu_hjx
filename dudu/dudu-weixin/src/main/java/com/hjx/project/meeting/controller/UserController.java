package com.hjx.project.meeting.controller;

import com.hjx.po.User;
import com.hjx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Company:
 * @Auther:xin
 * @Date:2019/11/27
 * @Time:19:11
 */
@RequestMapping("user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 登录(绑定功能)
     * 用户输入的邮箱在数据库user表正是否存在
     * 1.存在
     *      1.1邮箱已将被其他用户绑定
     *      1.2该邮箱未被其他用户绑定
     *2.不存在
     *      该邮箱不存在,无法进行登录功能
     */
    @ResponseBody
    @RequestMapping("login")
    public String login(@RequestParam("email") final  String email,
                        @RequestParam("wid") final Integer wid){
        //用户输入的邮箱在数据库user表中是否存在
        User user=userService.selectUserByEmail(email);
        if(user!=null){
            if(user.getWid()!=null){
                return "1";//邮箱已经被其他用户绑定,请联系客服
            }else {
                user.setWid(wid);
                userService.updateByEmail(user);
                return "2";//登录成功
            }
        }else {
            return "3";//该邮箱不存在,无法进行登录功能
        }


    }

    /**
     * 更新个人信息
     */
    @RequestMapping(method = RequestMethod.POST,value = "updateUser")
    @ResponseBody
    public  String updateUserInfo(User user){
        int num=userService.updateByPrimaryKeySelective(user);
        return num+"";
    }

    /**
     * 页面跳转
     * @return
     */
    @RequestMapping("toLogin")
    public String tologin(HttpServletRequest request){
        String wid=request.getParameter("wid");
        request.setAttribute("wid",wid);
        return "weixin/login";

    }

    @RequestMapping("toUnauth")
    public String toUnauth(){

        return "weixin/unauth";

    }
    @RequestMapping("toMeetingGrab")
    public String toMeetingGrab(HttpServletRequest request){
        String uid=request.getParameter("uid");
        request.setAttribute("uid",uid);
        return "weixin/meetingGrab/meetingGrab";

    }
}
