package com.haotang.pet.entity;

import org.json.JSONObject;

public class Banner {
	public String pic;
	public int type;
	public int cwjtest;
	public String url;
	public String backup;
	public int point;
	
	public static Banner json2Entity(JSONObject jobj){
		Banner b = new Banner();
		try {
			if(jobj.has("url")&&!jobj.isNull("url")){
				b.url=jobj.getString("url");
			}
			if(jobj.has("pic")&&!jobj.isNull("pic")){
				b.pic=jobj.getString("pic");
			}
			if(jobj.has("type")&&!jobj.isNull("type")){
				b.type=jobj.getInt("type");
			}
			if(jobj.has("cwjtest")&&!jobj.isNull("cwjtest")){
				b.cwjtest=jobj.getInt("cwjtest");
			}
			if(jobj.has("backup")&&!jobj.isNull("backup")){
				b.backup=jobj.getString("backup");
			}
			if(jobj.has("point")&&!jobj.isNull("point")){
				b.point=jobj.getInt("point");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
}
