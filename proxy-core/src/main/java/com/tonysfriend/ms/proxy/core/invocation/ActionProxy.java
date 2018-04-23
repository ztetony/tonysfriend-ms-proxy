package com.tonysfriend.ms.proxy.core.invocation;

import java.lang.reflect.Method;

import com.tonysfriend.ms.proxy.core.BaseAction;
import com.tonysfriend.ms.proxy.core.Return;

/**
 * Action代理，本身不做action调用的工作，只是调用Invocation本身。
 * @author yunfeng.cheng
 * @create 2016-08-08
 */
public class ActionProxy extends BaseAction {
	
	private BaseAction actionObject;
	private ActionInvocation invocation;
	private String methodName;
	private Method method;
	
	public ActionProxy(){
		
	}
	
	public Return execute() throws Exception{
		return invocation.invoke();
	}
	
	public void setActionObject(BaseAction actionObject) {
		this.actionObject = actionObject;
	}
	
	public BaseAction getActionObject() {
		return actionObject;
	}
	
	public void setInvocation(ActionInvocation invocation) {
		this.invocation = invocation;
	}
	
	public ActionInvocation getInvocation() {
		return invocation;
	}
	
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public String getMethodName() {
		return methodName;
	}
	
	public void setMethod(Method method) {
		this.method = method;
	}
	
	public Method getMethod() {
		return method;
	}

}
