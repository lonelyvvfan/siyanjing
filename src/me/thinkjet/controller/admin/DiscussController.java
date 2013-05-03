package me.thinkjet.controller.admin;

import me.thinkjet.model.Discuss;
import me.thinkjet.model.DiscussRecord;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.plugin.sqlinxml.SqlKit;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheName;
@ControllerBind(controllerKey = "/admin/discuss", viewPath = "admin/")
public class DiscussController extends Controller{
	@Before(CacheInterceptor.class)
	@CacheName("discuss-index")
	public void index() {
		setAttr("discuss",
				Discuss.dao.findByCache("discuss-index", "discuss",
						SqlKit.sql("discuss.findListForDiscussIndexByViews")));
	}
	
public void delete(){
	DiscussRecord.dao.deleteById(this.getPara("id"));
	Discuss.dao.deleteById(this.getPara("id"));
	render("index.html");
}
}
