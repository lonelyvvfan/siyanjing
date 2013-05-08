package me.thinkjet.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import me.thinkjet.auth.AuthManager;
import me.thinkjet.interceptor.DiscussInterceptor;
import me.thinkjet.interceptor.DiscussRecordInterceptor;
import me.thinkjet.model.Discuss;
import me.thinkjet.model.Job;

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
		List<Discuss> list;
		int page = this.getParaToInt("page", 1);
		list=Discuss.dao.findByCache("discuss-index", "discuss",
				SqlKit.sql("discuss.findListForDiscussIndexByViews"));
		this.setAttr("page", page);
		this.setAttr("discuss_list", Discuss.dao.paginateByCache("discuss-index",
				"discuss" + "_" + page, page, 2,"select J.*,R.views,R.comments","from discuss J left join discuss_record R on J.id=R.id order by R.views desc" ));
		setAttr("discusslist",list);
		setAttr("count",list.size());
	}

	// 返回用户发布的所有帖子
	public void findByUser() {
		setAttr("discusslist",
				Discuss.dao.find(SqlKit.sql("discuss.findByUser"), AuthManager
						.getSession(this).getUser().getLong("id")));
		render("discuss-user.html");
	}

	// 发布帖子
	public void add() {
		render("add.html");
	}

	// 提交发布帖子
	public void create() {
		Discuss discuss = getModel(Discuss.class);
		discuss.set("author", 1/*AuthManager.getSession(this).getUser().getLong("id")*/);
		discuss.save();
		redirect("/discuss");
	}

	// 提交修改
	public void update() {
		Discuss discuss = getModel(Discuss.class);
		discuss.update();
		redirect("/discuss/show?id="+discuss.get("id"));
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

	// 搜索
	public void search() throws UnsupportedEncodingException {
		List<Discuss> list;
		int page = this.getParaToInt(1, 1);
		StringBuffer sql = new StringBuffer(
				" from discuss J left join discuss_record R on J.id=R.id where 1=1");
		if (this.getPara("type") != null && !this.getPara("type").equals(""))
			sql.append(" and J.type=" + this.getPara("type"));
		if (this.getPara("prjname") != null
				&& !this.getPara("prjname").equals(""))
			sql.append(" and J.prjname=" + this.getPara("prjname"));
		sql.append(" order by J.id asc ");
		list=Discuss.dao.find("select J.*,R.views"+sql.toString()+"limit 0,20");
		setAttr("discusslist", list);
		setAttr("count",list.size());
		this.setAttr("page", page);
		this.setAttr("discuss_list", Discuss.dao.paginate( page, 20,"select J.*,R.views",sql.toString() ));
		render("index.html");
	}
}
