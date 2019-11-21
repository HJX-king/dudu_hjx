package com.hjx.project.weixin.api.tuling;

import com.hjx.project.weixin.api.tuling.bean.InputText;
import com.hjx.project.weixin.api.tuling.bean.Perception;
import com.hjx.project.weixin.api.tuling.bean.TulingBean;
import com.hjx.project.weixin.api.tuling.bean.UserInfo;
import com.hjx.project.weixin.util.WeixinUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Company:
 * @Auther:xin
 * @Date:2019/11/21
 * @Time:17:49
 */
@Service
public class TulingUtil {
    private static final String TULING_API_URL="http://openapi.tuling123.com/openapi/api/v2";

    /**接收用户发送的消息和响应消息
     * @InputParam msg 用户发送的文本
     * @returnParam resulr 响应的文本
     */
    public  String sendMessage(String msg , String api){

        //生成入参格式的json
        JSONObject jsonObject=sendJSONObject(msg,api);
        JSONObject object= WeixinUtil.httpRequest(TULING_API_URL,"POST",jsonObject.toString());
        //System.out.println("返回的数据"+object.toString());
        //获取返回的文本信息
        //对应的是数组
        JSONArray jsonArray= (JSONArray) object.get("results");
        JSONObject obj= (JSONObject) jsonArray.get(0);
        JSONObject obj1= (JSONObject)obj.get("values");
        String result=(String)obj1.get("text");
        System.out.println("最终返回结果"+result);

        return result;
    }

    /**
     * 按规则生成入参的json对象
     * @param msg 用户发送过来的文本
     * @param apiKey 机器人的标识(apiKey)
     * @return
     */
    public JSONObject sendJSONObject(String msg,String apiKey){
        //json格式数据入参
        InputText inputText=new InputText();
        inputText.setText(msg);

        Perception perception=new Perception();
        perception.setInputText(inputText);

        UserInfo userInfo= new UserInfo();
        //机器人标识
        userInfo.setApiKey(apiKey);
        //用户唯一标识
        userInfo.setUserId("hjx");

        TulingBean tulingBean= new TulingBean();
        tulingBean.setPerception(perception);
        tulingBean.setUserInfo(userInfo);

        JSONObject jsonObject=JSONObject.fromObject(tulingBean);
        //System.out.println("请求的数据"+jsonObject.toString());
        return jsonObject;
    }







    public static void main(String[] args) {

        /*String api="http://openapi.tuling123.com/openapi/api/v2";
        //用户输入的文本
        String msg="绕口令";

        //json格式数据入参
        InputText inputText=new InputText();
        inputText.setText(msg);

        Perception perception=new Perception();
        perception.setInputText(inputText);

        UserInfo userInfo= new UserInfo();
        //机器人标识
        userInfo.setApiKey("0d44699bde544431acecdfc79881a028");
        //用户唯一标识
        userInfo.setUserId("hjx");

        TulingBean tulingBean= new TulingBean();
        tulingBean.setPerception(perception);
        tulingBean.setUserInfo(userInfo);

        JSONObject jsonObject=JSONObject.fromObject(tulingBean);
        System.out.println("请求的数据"+jsonObject.toString());

        //二,对指定的API 地址 发送post请求 (传入入参json对象)
       JSONObject object= WeixinUtil.httpRequest(api,"POST",jsonObject.toString());
        System.out.println("返回的数据"+object.toString());
        //对应的是数组
        JSONArray jsonArray= (JSONArray) object.get("results");
        JSONObject obj= (JSONObject) jsonArray.get(0);
        JSONObject obj1= (JSONObject)obj.get("values");
        String result=(String)obj1.get("text");
        System.out.println("最终返回结果"+result);*/





    }
}
