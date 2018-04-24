package com.tonysfriend.ms.proxy.web.action;

import com.tonysfriend.ms.bean.Result;
import com.tonysfriend.ms.constant.Constants;
import com.tonysfriend.ms.impl.ApplicationClientServiceImpl;
import com.tonysfriend.ms.proxy.core.BaseAction;
import com.tonysfriend.ms.proxy.core.DataHolder;
import com.tonysfriend.ms.proxy.core.annotation.Namespace;
import com.tonysfriend.ms.proxy.core.annotation.Read;
import com.tonysfriend.ms.proxy.core.ret.Render;
import com.tonysfriend.ms.proxy.core.ret.RenderType;
import com.tonysfriend.ms.util.PropertiesUtil;
import io.netty.handler.codec.http.HttpRequest;

/**
 * @Author: tony.lu
 * @Date: 2018-04-23 下午 03:16
 */
public class ApplicationServiceAction extends BaseAction {

    //注册
    //使用 @Namespace 注解
    @Namespace("/nettp/proxy/")
    public Render register(@Read(key = "port", defaultValue = "1") Integer port, @Read(key = "host") String host, @Read(key = "app") String app, @Read(key = "instanceId") String instanceId, @Read(key = "health") String health) {
        System.out.println("Receive parameters: port=" + port + ",host=" + host + ",app=" + app + ",instanceId=" + instanceId + ",health=" + health);
        HttpRequest request = (HttpRequest) DataHolder.getRequest();
        String uri = request.uri();
        //do register
        ApplicationClientServiceImpl client = new ApplicationClientServiceImpl();

        String eureka = PropertiesUtil.getProperty("eureka.client.serviceUrl.defaultZone");
        String REGISTER_INSTANCE_URL = eureka + "apps/RIBBON-CONSUMER-1-2";

        Result registResult = client.registerAppInstance(Constants.REGISTER_INSTANCE_URL, "POST", "application/xml", Constants.REGISTER_XML_STRING, Constants.DEFAULT_TIMEOUT);
        System.out.println(registResult);

        String result = "port:"+port+", app:"+app+", instanceId:"+instanceId;

        return new Render(RenderType.TEXT, result);
    }

}