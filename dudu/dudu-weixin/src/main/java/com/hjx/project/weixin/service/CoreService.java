package com.hjx.project.weixin.service;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hjx.po.User;
import com.hjx.po.Weiuser;
import com.hjx.project.weixin.api.accessToken.AccessTokenRedis;
import com.hjx.project.weixin.api.hitokoto.HitokotoUtil;
import com.hjx.project.weixin.api.tuling.TulingUtil;
import com.hjx.project.weixin.api.tuling.bean.TulingBean;
import com.hjx.project.weixin.api.userInfo.UserInfoService;
import com.hjx.service.UserService;
import com.hjx.service.WeiuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjx.project.weixin.main.MenuManager;
import com.hjx.project.weixin.pojo.AccessToken;
import com.hjx.project.weixin.util.MessageUtil;
import com.hjx.project.weixin.util.WeixinUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import net.sf.json.JSONObject;


import com.hjx.project.weixin.bean.resp.Article;
import com.hjx.project.weixin.bean.resp.MusicMessage;
import com.hjx.project.weixin.bean.resp.NewsMessage;
import com.hjx.project.weixin.bean.resp.TextMessage;

@Service
public class CoreService {
	@Autowired
	private  TulingUtil tulingUtil;
	@Autowired
	private HitokotoUtil hitokotoUtil;
	@Autowired
	private AccessTokenRedis accessTokenRedis;
	@Autowired
    private UserInfoService userInfoService;
	@Autowired
	private UserService userService;
	@Autowired
	private WeiuserService weiuserService;

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public String processRequest(HttpServletRequest request) {

		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";

			// xml请求解析 调用消息工具类MessageUtil解析微信发来的xml格式的消息，解析的结果放在HashMap里；
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id） 下面三行代码是： 从HashMap中取出消息中的字段；
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			// 回复文本消息 组装要返回的文本消息对象；
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(System.currentTimeMillis());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			// 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义
			// textMessage.setContent("欢迎访问<a
			// href=\"http://www.baidu.com/index.php?tn=site888_pg\">百度</a>!");
			// 将文本消息对象转换成xml字符串
			respMessage = MessageUtil.textMessageToXml(textMessage);
			/**
			 * 演示了如何接收微信发送的各类型的消息，根据MsgType判断属于哪种类型的消息；
			 */

			// 接收用户发送的文本消息内容
			String content = requestMap.get("Content");

			// 创建图文消息
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(System.currentTimeMillis());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setFuncFlag(0);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				//respContent=tulingUtil.sendMessage(content);
				//respContent = "您发送的是文本消息！";

				System.out.println("accesstoken的值是"+accessTokenRedis.getAccessTokenVal());
				respContent=tulingUtil.invoke(content);
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是音频消息！";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					/**
					 * 收集个人信息
					 * 1.得到用户的openid
					 * 2.根据openid向微信服务器发送get请求得到用户授权
					 * 3.得到用户是JSONObject-->转成weiuser对象
					 * 4.将weiuser对象进行数据库的添加操作
					 */
					userInfoService.userInfo(fromUserName);


					respContent = "欢迎关注微信公众号";
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = requestMap.get("EventKey");

					if (eventKey.equals("11")) {
						List<Article> articles=new ArrayList<>();
						Article article=new Article();
						//通过openid(fromUser)来判断是否登录
						User user=userService.selectUserByOpenId(fromUserName);
						if (user==null){
							//没有绑定-->跳到登录页面
							Weiuser weiuser=weiuserService.selectByOpenId(fromUserName);
							article.setUrl(MenuManager.REAL_URL+"/user/toLogin/?wid="+weiuser.getId());
							article.setTitle("您还没有登录,请先去登录");
							article.setDescription("该功能需要登录");
							article.setPicUrl("http://5b0988e595225.cdn.sohucs.com/q_70,c_zoom,w_640/images/20190728/b9423646472f42c3ad8d1a5dd1a145e3.jpeg");

						}else{
							if (user.getRid()==1){
								//已绑定,发单组-->跳到无权限页面
								article.setUrl(MenuManager.REAL_URL+"/user/toUnauth");
								article.setTitle(user.getName()+"您是发单组,无法操作此功能");
								article.setDescription("该功能只能抢单角色可以访问");
								article.setPicUrl("http://pic2.zhimg.com/50/v2-b0b1f217decef38ee7206d2a3ab9e86e_hd.jpg");

							}else {
								//已绑定,抢单组-->跳到抢单页面
								article.setUrl(MenuManager.REAL_URL+"/user/toMeetingGrab/?uid="+user.getId());
								article.setTitle(user.getName()+"欢迎您,进行抢单功能");
								article.setDescription("抢单功能是什么?>>>>使用教程");
								article.setPicUrl("http://img2.imgtn.bdimg.com/it/u=184803235,1555534009&fm=11&gp=0.jpg");

							}

						}
						articles.add(article);
						// 设置图文消息个数
						newsMessage.setArticleCount(articles.size());
						// 设置图文消息
						newsMessage.setArticles(articles);
						// 将图文消息对象转换为XML字符串
						respMessage = MessageUtil.newsMessageToXml(newsMessage);

						return respMessage;


					}else if (eventKey.equals("31")) {
						respContent = "联系我们被点击！";

					}else if (eventKey.equals("34")) {
						 String i= hitokotoUtil.sendMessage();
						respContent=i;

					}
					else if (eventKey.equals("70")) {

						List<Article> articleList = new ArrayList<Article>();
						
						// 单图文消息
						Article article = new Article();
						article.setTitle("标题");
						article.setDescription("描述内容");
						article.setPicUrl(
								"图片");
						article.setUrl("跳转连接");
					    articleList.add(article);
						// 设置图文消息个数
						newsMessage.setArticleCount(articleList.size());
						// 设置图文消息
						newsMessage.setArticles(articleList);
						// 将图文消息对象转换为XML字符串
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
						return respMessage;

						

					}
					
				}

			}

			// 组装要返回的文本消息对象；
			textMessage.setContent(respContent);
			// 调用消息工具类MessageUtil将要返回的文本消息对象TextMessage转化成xml格式的字符串；
			respMessage = MessageUtil.textMessageToXml(textMessage);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}

}
