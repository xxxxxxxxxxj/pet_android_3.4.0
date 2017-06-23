package com.haotang.pet.entity;

import java.io.Serializable;

import org.json.JSONObject;

public class ExtraItem implements Serializable {
	private static final long serialVersionUID = 141234L;
	private String availablePets;
	private String desc;
	private int itemId;
	private String itemName;
	private double listPrice;// 原价
	private double price;// 现价

	public static ExtraItem json2Entity(JSONObject json) {
		ExtraItem extraItem = new ExtraItem();
		try {
			if (json.has("availablePets") && !json.isNull("availablePets")) {
				extraItem.availablePets = json.getString("availablePets");
			}
			if (json.has("desc") && !json.isNull("desc")) {
				extraItem.desc = json.getString("desc");
			}
			if (json.has("itemId") && !json.isNull("itemId")) {
				extraItem.itemId = json.getInt("itemId");
			}
			if (json.has("itemName") && !json.isNull("itemName")) {
				extraItem.itemName = json.getString("itemName");
			}
			if (json.has("listPrice") && !json.isNull("listPrice")) {
				extraItem.listPrice = json.getDouble("listPrice");
			}
			if (json.has("price") && !json.isNull("price")) {
				extraItem.price = json.getDouble("price");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return extraItem;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAvailablePets() {
		return availablePets;
	}

	public void setAvailablePets(String availablePets) {
		this.availablePets = availablePets;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getListPrice() {
		return listPrice;
	}

	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ExtraItem [availablePets=" + availablePets + ", desc=" + desc
				+ ", itemId=" + itemId + ", itemName=" + itemName
				+ ", listPrice=" + listPrice + ", price=" + price
				+ ", getAvailablePets()=" + getAvailablePets() + ", getDesc()="
				+ getDesc() + ", getItemId()=" + getItemId()
				+ ", getItemName()=" + getItemName() + ", getListPrice()="
				+ getListPrice() + ", getPrice()=" + getPrice()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	public ExtraItem(String availablePets, String desc, int itemId,
			String itemName, double listPrice, double price) {
		super();
		this.availablePets = availablePets;
		this.desc = desc;
		this.itemId = itemId;
		this.itemName = itemName;
		this.listPrice = listPrice;
		this.price = price;
	}

	public ExtraItem() {
		super();
	}
}
