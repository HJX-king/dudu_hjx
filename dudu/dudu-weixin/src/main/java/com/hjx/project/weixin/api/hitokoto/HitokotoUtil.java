package com.hjx.project.weixin.api.hitokoto;

import com.hjx.project.weixin.util.WeixinUtil;
import lombok.Data;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Company:
 * @Auther:xin  https://v1.hitokoto.cn/
 * @Date:2019/11/21
 * @Time:23:16
 */
@Service
public class HitokotoUtil {
    private static  final String HITOKOTO_API_URL="https://v1.hitokoto.cn/";
    public String sendMessage(){
        JSONObject jsonObject= WeixinUtil.httpRequest(HITOKOTO_API_URL,"GET",null);
        String result =(String)jsonObject.get("hitokoto");
        return result;
    }

}
