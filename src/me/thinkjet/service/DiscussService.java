package me.thinkjet.service;

import me.thinkjet.model.Discuss;

public class DiscussService {
public void createDiscuss(Discuss discuss){
	discuss.save();
}
public void update(Discuss discuss){
	discuss.update();
}
}
