package me.thinkjet.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

public class DiscussInterceptor implements Interceptor{
	public void intercept(ActionInvocation ai) {
	ai.invoke();
	}
}
