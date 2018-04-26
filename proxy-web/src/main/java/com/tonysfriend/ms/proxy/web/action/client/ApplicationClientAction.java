package com.tonysfriend.ms.proxy.web.action.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tonysfriend.ms.bean.InstanceId;
import com.tonysfriend.ms.bean.Result;
import com.tonysfriend.ms.bean.http.Header;
import com.tonysfriend.ms.bean.http.Param;
import com.tonysfriend.ms.impl.ApplicationClientServiceImpl;
import com.tonysfriend.ms.proxy.core.BaseAction;
import com.tonysfriend.ms.proxy.core.annotation.Namespace;
import com.tonysfriend.ms.proxy.core.annotation.Read;
import com.tonysfriend.ms.proxy.core.ret.Render;
import com.tonysfriend.ms.proxy.core.ret.RenderType;
import com.tonysfriend.ms.proxy.web.action.service.ApplicationServiceAction;
import com.tonysfriend.ms.util.HttpClient;
import com.tonysfriend.ms.util.PropertiesUtil;
import com.tonysfriend.ms.util.RestTemplate;
import com.tonysfriend.ms.util.StringUtil;
import org.slf4j.LoggerFactory;
import java.util.Map;

/**
 * @Author: tony.lu
 * @Date: 2018-04-23 下午 03:16
 */
public class ApplicationClientAction extends BaseAction {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ApplicationServiceAction.class);

    //使用 @Namespace 注解
    @Namespace("/proxy/client/")
    public Render call(@Read(key = "msapiurl", defaultValue = "http://computer-service:8888/api/group/xxx") String msapiurl,
                       @Read(key = "method") String method,
                       @Read(key = "contentType") String contentType,
                       @Read(key = "headers", sampleValue = "{\"tenantId\":0}") String headers,
                       @Read(key = "params") String params) {

        Result invokeResult = new Result();
        String resultJson = "";

        try {
            LOGGER.info("Receive parameters: msapiurl=" + msapiurl + ", method=" + method);
            //parse msapi --> get app (ZIOT.DMS.1.0.0.DAILY)
            //parse vipAddress
            int pos1 = msapiurl.indexOf("http://") + 7;
            String url1 = msapiurl.substring(pos1);
            int pos2 = url1.indexOf("/");
            String u_r_i = url1.substring(pos2);
            String vipAddress = url1.substring(0, pos2);
            System.out.println("vipAddress:" + vipAddress);

            //get eureka/apps host port check health status choose a nb 的host
            ApplicationClientServiceImpl client = new ApplicationClientServiceImpl();
            String eureka = PropertiesUtil.getProperty("eureka.client.serviceUrl.defaultZone");

            Result<InstanceId> instanceResult = client.getInstance(eureka + "/apps/" + vipAddress, 5 * 1000);
            InstanceId instanceId = instanceResult.getData();
            String factUrl = "http://" + instanceId.getHost() + ":" + instanceId.getPort() + u_r_i;

            //
            RestTemplate restTemplate = new RestTemplate();
            //String url, String method, String contentType, Header header, Param param, int timeout

            //
            Header header = new Header();
            if (StringUtil.isNotEmpty(headers)) {
                JSONObject jsonObjectHeader = JSON.parseObject(headers);
                for (Map.Entry<String, Object> entry : jsonObjectHeader.entrySet()) {
                    header.put(entry.getKey(), entry.getValue().toString());
                }
            }

            //
            Param param = new Param();
            if (StringUtil.isNotEmpty(params)) {
                JSONObject jsonObjectParam = JSON.parseObject(params);
                for (Map.Entry<String, Object> entry : jsonObjectParam.entrySet()) {
                    param.put(entry.getKey(), entry.getValue().toString());
                }
            }

            invokeResult = restTemplate.invokeForEntity(factUrl, method, contentType, header, param, 10 * 1000);

        } catch (Exception e) {
            LOGGER.error("call ms fail:{}", e);
            invokeResult = new Result();
            invokeResult.setData(e.getMessage() + "");

        } finally {
            resultJson = JSON.toJSONString(invokeResult);
        }

        return new Render(RenderType.JSON, resultJson);
    }

}