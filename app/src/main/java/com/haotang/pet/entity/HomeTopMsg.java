package com.haotang.pet.entity;

import org.json.JSONObject;

public class HomeTopMsg {
	public String tag;
	public String title;
	public String url;
	public int type;
	public String pic;
	public int hotOrNew;// 1:new 2:hot

	@Override
	public String toString() {
		return "HomeTopMsg [tag=" + tag + ", title=" + title + ", url=" + url
				+ ", type=" + type + ", pic=" + pic + ", hotOrNew=" + hotOrNew
				+ "]";
	}

	public static HomeTopMsg json2Entity(JSONObject jobj, int flag) {
		HomeTopMsg topmsg = new HomeTopMsg();
		try {
			topmsg.hotOrNew = flag;
			if (jobj.has("tag") && !jobj.isNull("tag")) {
				topmsg.tag = jobj.getString("tag");
			}
			if (jobj.has("title") && !jobj.isNull("title")) {
				topmsg.title = jobj.getString("title");
			}
			if (jobj.has("url") && !jobj.isNull("url")) {
				topmsg.url = jobj.getString("url");
			}
			if (jobj.has("type") && !jobj.isNull("type")) {
				topmsg.type = jobj.getInt("type");
			}
			if (jobj.has("pic") && !jobj.isNull("pic")) {
				topmsg.pic = jobj.getString("pic");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return topmsg;
	}
}
