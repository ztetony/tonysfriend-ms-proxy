package com.tonysfriend.ms.proxy.web.action.service;

import com.alibaba.fastjson.JSON;
import com.tonysfriend.ms.bean.InstanceId;
import com.tonysfriend.ms.bean.Result;
import com.tonysfriend.ms.constant.Constants;
import com.tonysfriend.ms.impl.ApplicationClientServiceImpl;
import com.tonysfriend.ms.proxy.core.BaseAction;
import com.tonysfriend.ms.proxy.core.DataHolder;
import com.tonysfriend.ms.proxy.core.annotation.Get;
import com.tonysfriend.ms.proxy.core.annotation.Namespace;
import com.tonysfriend.ms.proxy.core.annotation.Read;
import com.tonysfriend.ms.proxy.core.ret.Render;
import com.tonysfriend.ms.proxy.core.ret.RenderType;
import com.tonysfriend.ms.util.PropertiesUtil;
import io.netty.handler.codec.http.HttpRequest;
import org.slf4j.LoggerFactory;

/**
 * @Author: tony.lu
 * @Date: 2018-04-23 下午 03:16
 */
public class ApplicationServiceAction extends BaseAction {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ApplicationServiceAction.class);

    //注册
    //使用 @Namespace 注解
    @Namespace("/service/register/")
    public Render register(@Read(key = "host") String host,
                           @Read(key = "app") String app,
                           @Read(key = "port", defaultValue = "1900") Integer port,
                           @Read(key = "timeout") int timeout) {

        LOGGER.info("Receive parameters: port=" + port + ",host=" + host + ",app=" + app);
        HttpRequest request = (HttpRequest) DataHolder.getRequest();
        String uri = request.uri();

        Result registResult = new Result();

        InstanceId instanceId = new InstanceId();
        try {
            //do register
            ApplicationClientServiceImpl client = new ApplicationClientServiceImpl();

            String eureka = PropertiesUtil.getProperty("eureka.client.serviceUrl.defaultZone");
            String REGISTER_INSTANCE_URL = eureka + "/apps/" + app;

            instanceId.setHost(host);
            instanceId.setPort(port);
            instanceId.setName(app);

            registResult = client.registerAppInstance(REGISTER_INSTANCE_URL, "POST", "application/xml", instanceId, timeout);

        } catch (Exception e) {
            LOGGER.error("reg fail : {}", e);
        }

        String result = "port:" + port + ", app:" + app + ", instanceId:" + instanceId;

        return new Render(RenderType.TEXT, JSON.toJSONString(registResult));
    }

    //心跳
    //使用 @Namespace 注解
    @Namespace("/service/heartbeat/")
    public Render heartbeat(@Read(key = "host") String host,
                            @Read(key = "app") String app,
                            @Read(key = "port", defaultValue = "1900") Integer port,
                            @Read(key = "timeout") int timeout) {

        LOGGER.info("Receive parameters: port=" + port + " ,host=" + host + " ,app=" + app + " ,timeout=" + timeout);
        HttpRequest request = (HttpRequest) DataHolder.getRequest();
        String uri = request.uri();

        Result registerResult = new Result();
        try {
            //do register
            ApplicationClientServiceImpl client = new ApplicationClientServiceImpl();

            String eureka = PropertiesUtil.getProperty("eureka.client.serviceUrl.defaultZone");
            String REGISTER_INSTANCE_URL = eureka + "/apps/" + app + "/" + host + ":" + app + ":" + port;

            registerResult = client.sendHeartbeat(REGISTER_INSTANCE_URL, "PUT", "text/plain", "", timeout);

            LOGGER.info("heartbeat result:{}", registerResult);

        } catch (Exception e) {
            LOGGER.error("heartbeat fail: {}", e);
        }

        String result = "port:" + port + ", app:" + app + ", host:" + host;

        return new Render(RenderType.TEXT, JSON.toJSONString(registerResult));
    }

}