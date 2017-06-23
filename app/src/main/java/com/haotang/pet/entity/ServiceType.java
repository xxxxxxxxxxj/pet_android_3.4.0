package com.haotang.pet.entity;

import org.json.JSONObject;

public class ServiceType {
	public String name;//"到店服务", //服务名称
	public int isHot;// 1, //是否显示热门tag
	public int serviceLoc;// 上门 / 到店
	public String icon;//"/static/image/shop_service_pic.png", //服务icon
	public String desc_bottom;//"宠物家生活馆欢迎您", //底部显示文案
	public int price; //"¥75起", //右侧显示的最低价格文案
	public String btn_txt; //服务方式
	public String disabled_tip;//"抱歉，大型犬暂不支持上门服务" //服务不可用时的提示文案,可用时该key不存在
	
	public static ServiceType json2Entity(JSONObject json){
		ServiceType  serviceType = new ServiceType();
		try {
			if (json.has("name")&&!json.isNull("name")) {
				serviceType.name = json.getString("name");
			}
			if (json.has("isHot")&&!json.isNull("isHot")) {
				serviceType.isHot = json.getInt("isHot");
			}
			if (json.has("serviceLoc")&&!json.isNull("serviceLoc")) {
				serviceType.serviceLoc = json.getInt("serviceLoc");
			}
			if (json.has("icon")&&!json.isNull("icon")) {
				serviceType.icon = json.getString("icon");
			}
			if (json.has("desc_bottom")&&!json.isNull("desc_bottom")) {
				serviceType.desc_bottom = json.getString("desc_bottom");
			}
			if (json.has("price")&&!json.isNull("price")) {
				serviceType.price = json.getInt("price");
			}
			if (json.has("btn_txt")&&!json.isNull("btn_txt")) {
				serviceType.btn_txt = json.getString("btn_txt");
			}
			if (json.has("disabled_tip")&&!json.isNull("disabled_tip")) {
				serviceType.disabled_tip = json.getString("disabled_tip");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return serviceType;
		
	}
}
