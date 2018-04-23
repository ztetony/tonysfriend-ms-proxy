package com.tonysfriend.ms.proxy.web.action;

        import com.tonysfriend.ms.proxy.core.BaseAction;
        import com.tonysfriend.ms.proxy.core.annotation.Read;
        import com.tonysfriend.ms.proxy.core.ret.Render;
        import com.tonysfriend.ms.proxy.core.ret.RenderType;

/**
 * @Author: tony.lu
 * @Date: 2018-04-23 下午 03:16
 */
public class ApplicationServiceAction extends BaseAction {

    //注册
    public Render register(@Read(key = "port", defaultValue = "1") Integer port, @Read(key = "host") String host, @Read(key = "app") String app, @Read(key = "instanceId") String instanceId, @Read(key = "health") String health) {
        System.out.println("Receive parameters: port=" + port + ",host=" + host + ",app=" + app + ",instanceId=" + instanceId + ",health=" + health);
        //do register
        String result = "";
        return new Render(RenderType.TEXT, result);
    }

}