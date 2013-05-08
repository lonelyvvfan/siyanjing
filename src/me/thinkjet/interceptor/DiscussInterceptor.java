package me.thinkjet.interceptor;

import me.thinkjet.model.Discuss;
import me.thinkjet.model.Job;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.ext.plugin.sqlinxml.SqlKit;

public class DiscussInterceptor implements Interceptor{
	public void intercept(ActionInvocation ai) {
		ai.getController().setAttr("hotdiscusstype",Discuss.dao.findByCache("hotdiscuss","hotdiscuss", SqlKit.sql("discuss.findHotDiscussTypeForDiscussIndexByCount")));
		String target="discuss_15";
		System.out.println();
		ai.invoke();
	}
}
