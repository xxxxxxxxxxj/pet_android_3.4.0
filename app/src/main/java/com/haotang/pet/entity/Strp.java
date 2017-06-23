package com.haotang.pet.entity;

import java.util.List;

public class Strp {
	private int petId;
	private int myPetId;
	private int serviceId;
	private List<Integer> itemIds;
	private StringBuffer extraItemIds;

	public int getMyPetId() {
		return myPetId;
	}

	public void setMyPetId(int myPetId) {
		this.myPetId = myPetId;
	}

	public int getPetId() {
		return petId;
	}

	public StringBuffer getExtraItemIds() {
		return extraItemIds;
	}

	public void setExtraItemIds(StringBuffer extraItemIds) {
		this.extraItemIds = extraItemIds;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public List<Integer> getItemIds() {
		return itemIds;
	}

	public void setItemIds(List<Integer> itemIds) {
		this.itemIds = itemIds;
	}

	public Strp() {
		super();
	}

	public Strp(int petId, int myPetId, int serviceId, List<Integer> itemIds,
			StringBuffer extraItemIds) {
		super();
		this.petId = petId;
		this.myPetId = myPetId;
		this.serviceId = serviceId;
		this.itemIds = itemIds;
		this.extraItemIds = extraItemIds;
	}
}
