package me.thinkjet.controller;

import me.thinkjet.auth.AuthManager;
import me.thinkjet.interceptor.DiscussInterceptor;
import me.thinkjet.interceptor.DiscussRecordInterceptor;
import me.thinkjet.model.Discuss;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.plugin.sqlinxml.SqlKit;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheName;

@ControllerBind(controllerKey = "/discuss", viewPath = "discuss")
@Before(DiscussInterceptor.class)
public class DiscussController extends Controller {

	@Before(CacheInterceptor.class)
	@CacheName("discuss-index")
	public void index() {
		setAttr("discusslist",
				Discuss.dao.findByCache("discuss-index", "discuss",
						SqlKit.sql("discuss.findListForDiscussIndexByViews")));
	}

	// 返回用户发布的所有招聘
	public void findByUser() {
		setAttr("discusslist",
				Discuss.dao.find(SqlKit.sql("discuss.findByUser"), AuthManager
						.getSession(this).getUser().getLong("id")));
		render("discuss-user.html");
	}

	// 发布招聘
	public void add() {
		render("add.html");
	}

	// 提交发布招聘
	public void create() {
		Discuss discuss = getModel(Discuss.class);
		discuss.set("author", 1/*AuthManager.getSession(this).getUser().getLong("id")*/);
		discuss.save();
		render("index.html");
	}

	// 提交修改
	public void update() {
		Discuss discuss = getModel(Discuss.class);
		discuss.update();
	}

	// 显示单条记录
	@Before(DiscussRecordInterceptor.class)
	public void show() {
		setAttr("discuss",
				Discuss.dao.findFirst(SqlKit.sql("discuss.findOneById"), getPara("id")));
	}

	// 修改
	public void edit() {
		setAttr("discuss", Discuss.dao.findById(getPara("id")));
	}

	// 搜索岗位
	public void search() {
		StringBuffer sql = new StringBuffer(
				"select J.*,R.views,R.comments from discuss J left join discuss_record R on J.id=R.id where 1=1");
		if (this.getPara("name") != null && !this.getPara("name").equals(""))
			sql.append(" and J.name like '%" + this.getPara("name") + "%'");
		if (this.getPara("type") != null && !this.getPara("type").equals(""))
			sql.append(" and J.type=" + this.getPara("type"));
		if (this.getPara("salary") != null
				&& !this.getPara("salary").equals(""))
			sql.append(" and J.salary=" + this.getPara("salary"));
		sql.append(" order by J.id asc limit 0,10");
		setAttr("discusslist", Discuss.dao.find(sql.toString()));
		render("index.html");
	}
}
