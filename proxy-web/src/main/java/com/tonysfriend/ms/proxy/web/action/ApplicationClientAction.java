package com.tonysfriend.ms.proxy.web.action;

import com.tonysfriend.ms.proxy.core.BaseAction;
import com.tonysfriend.ms.proxy.core.annotation.Read;
import com.tonysfriend.ms.proxy.core.ret.Render;
import com.tonysfriend.ms.proxy.core.ret.RenderType;

/**
 * @Author: tony.lu
 * @Date: 2018-04-23 下午 03:16
 */
public class ApplicationClientAction extends BaseAction {

    //client proxy
    public Render proxy(@Read(key = "msapi", defaultValue = "http://computer-service:8888/api/group/xxx") String msapi, @Read(key = "method") String method) {
        System.out.println("Receive parameters: msapi=" + msapi + ",method=" + method );
        //parse msapi --> get app (ZIOT.DMS.1.0.0.DAILY)

        //get eureka/apps host port check health status choose a nb 的host

        String result = "";
        return new Render(RenderType.TEXT, result);
    }

}
