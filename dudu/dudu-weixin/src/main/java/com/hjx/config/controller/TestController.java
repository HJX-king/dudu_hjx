package com.hjx.config.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Company:
 * @Auther:xin
 * @Date:2019/11/22
 * @Time:20:38
 */
@RestController
public class TestController {
    @Autowired
    private RedisTemplate<String,Object>redisTemplate;

    @RequestMapping("info")
    public String info(){
        System.out.println("redis对象"+redisTemplate);
        return "info....";
    }
}
