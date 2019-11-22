package com.hjx.project.weixin.api.accessToken;

import com.hjx.project.weixin.api.accessToken.AccessTokenThread;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @Description:
 * @Company:
 * @Auther:xin
 * @Date:2019/11/22
 * @Time:19:38
 */
@WebListener
public class AccessTokenListener implements ServletContextListener {
   @Override
   public void contextInitialized(ServletContextEvent sce) {
      //new AccessTokenThread().start();

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
