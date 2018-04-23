package com.tonysfriend.ms.proxy.core.ret;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.handler.codec.http.FullHttpResponse;

import com.tonysfriend.ms.proxy.core.Return;
import com.tonysfriend.ms.proxy.core.utils.HttpRenderUtil;

/**
 * 字符型的输出
 * @author yunfeng.cheng
 * @create 2016-08-08
 */
public class Render implements Return{
	
	Logger logger = LoggerFactory.getLogger(Render.class);
	
	private String data;
	public RenderType renderType;
	
	public Render(RenderType renderType, String data) {
		this.data = data;
		this.renderType = renderType;
	}
	
	public FullHttpResponse process() throws Exception {
		FullHttpResponse response;
		switch (renderType) {
		case JSON:
			response = HttpRenderUtil.renderJSON(data);
			break;
		case TEXT:
			response = HttpRenderUtil.renderText(data);
			break;
		case XML:
			response = HttpRenderUtil.renderXML(data);
			break;
		case HTML:
			response = HttpRenderUtil.renderHTML(data);
			break;
		default:
			response = HttpRenderUtil.getErroResponse();
			logger.error("unkown render type");
		}
		return response;
	}

}
