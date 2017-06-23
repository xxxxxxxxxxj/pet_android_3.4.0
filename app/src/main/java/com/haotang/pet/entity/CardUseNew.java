package com.haotang.pet.entity;

import org.json.JSONObject;

public class CardUseNew {
	public String title;
	public String content;
	public String time;
	public static CardUseNew json2Entity(JSONObject json){
		CardUseNew cardUseNew = new CardUseNew();
		return cardUseNew;
	}
}
