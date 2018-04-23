package com.tonysfriend.ms.proxy.web.action;

import com.tonysfriend.ms.proxy.core.BaseAction;
import com.tonysfriend.ms.proxy.core.annotation.Namespace;
import com.tonysfriend.ms.proxy.core.annotation.Read;
import com.tonysfriend.ms.proxy.core.ret.Render;
import com.tonysfriend.ms.proxy.core.ret.RenderType;

/**
 * 基本类型参数 测试 demo
 * @author yunfeng.cheng
 * @create 2016-08-22
 */
public class DemoAction extends BaseAction{
	
	//测试基本参数类型
	public Render primTypeTest(@Read(key="id", defaultValue="1" ) Integer id, @Read(key="proj") String proj, @Read(key="author") String author){
		System.out.println("Receive parameters: id=" + id + ",proj=" + proj + ",author=" + author);
		return new Render(RenderType.TEXT, "Received your primTypeTest request.[from primTypeTest]");
	}
	
	//使用 @Namespace 注解
	@Namespace("/nettp/pri/")
	public Render primTypeTestWithNamespace(@Read(key="id") Integer id, @Read(key="proj") String proj, @Read(key="author") String author){
		System.out.println("Receive parameters: id=" + id + ",proj=" + proj + ",author=" + author);
		return new Render(RenderType.TEXT, "Received your primTypeTestWithNamespace request.[from primTypeTestWithNamespace]");
	}
	
	
	
}
