package com.haotang.pet.entity;

import org.json.JSONObject;

public class AppointNewCard {
	public int cardId;
	public String cardImg;
	public String descTop;

	public static AppointNewCard json2Entity(JSONObject json) {
		AppointNewCard appointNewCard = new AppointNewCard();
		try {
			if (json.has("cardId") && !json.isNull("cardId")) {
				appointNewCard.cardId = json.getInt("cardId");
			}
			if (json.has("cardImg") && !json.isNull("cardImg")) {
				appointNewCard.cardImg = json.getString("cardImg");
			}
			if (json.has("descTop") && !json.isNull("descTop")) {
				appointNewCard.descTop = json.getString("descTop");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appointNewCard;
	}
}
