package com.haotang.pet.entity;

import java.io.Serializable;
import java.util.List;

public class AppointMentNewPetInfo implements Serializable{
	private static final long serialVersionUID = 11928469L;
	private String avatar;
	private String petName;
	private int petId;
	private int myPetId;
	private String nickname;
	private String levelName;//对应套餐等级name
	private double price;//对应基础服务价格
	private boolean isUseCard;//是否使用次卡
	private int serviceId;// 区分猫狗服务id 狗 1 洗澡、2 美容 猫 3 洗澡、4美容
	private int petKind;
	private int serviceType;// 服务类目
	private int level;//服务级别1、2、3 
	private List<ExtraItem> ExtraItems;

	private int serviceLoc;// 服务方式
	private int addressId;// 地址id
	private String address;// 宠物地址
	private String shopName;// 门店地址
	private int shopId;// 门店id
	private String petExtra;//单项拼接
	private String shopAddress;//单项拼接
	private String addServiceIds;//单项id 1,2,3
	public String getAddServiceIds() {
		return addServiceIds;
	}

	public void setAddServiceIds(String addServiceIds) {
		this.addServiceIds = addServiceIds;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	private boolean isSelect;

	public int getServiceLoc() {
		return serviceLoc;
	}

	public void setServiceLoc(int serviceLoc) {
		this.serviceLoc = serviceLoc;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public String getPetExtra() {
		return petExtra;
	}

	public void setPetExtra(String petExtra) {
		this.petExtra = petExtra;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	public int getServiceType() {
		return serviceType;
	}

	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPetKind() {
		return petKind;
	}

	public void setPetKind(int petKind) {
		this.petKind = petKind;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public int getMyPetId() {
		return myPetId;
	}

	public void setMyPetId(int myPetId) {
		this.myPetId = myPetId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isUseCard() {
		return isUseCard;
	}

	public void setUseCard(boolean isUseCard) {
		this.isUseCard = isUseCard;
	}

	public List<ExtraItem> getExtraItems() {
		return ExtraItems;
	}

	public void setExtraItems(List<ExtraItem> extraItems) {
		ExtraItems = extraItems;
	}

	public AppointMentNewPetInfo(String avatar, String petName, int petId,
			int myPetId, String nickname, String levelName, double price,
			boolean isUseCard, int serviceId, List<ExtraItem> extraItems,
			int petKind, int serviceType, int level, int serviceLoc,
			int addressId, String address, String shopName, int shopId,String shopAddress) {
		super();
		this.avatar = avatar;
		this.petName = petName;
		this.petId = petId;
		this.myPetId = myPetId;
		this.nickname = nickname;
		this.levelName = levelName;
		this.price = price;
		this.isUseCard = isUseCard;
		this.serviceId = serviceId;
		this.ExtraItems = extraItems;
		this.petKind = petKind;
		this.serviceType = serviceType;
		this.level = level;
		this.serviceLoc = serviceLoc;
		this.addressId = addressId;
		this.address = address;
		this.shopName = shopName;
		this.shopId = shopId;
		this.shopAddress = shopAddress;
	}

	@Override
	public String toString() {
		return "AppointMentNewPetInfo [avatar=" + avatar + ", petName="
				+ petName + ", petId=" + petId + ", myPetId=" + myPetId
				+ ", nickname=" + nickname + ", levelName=" + levelName
				+ ", price=" + price + ", isUseCard=" + isUseCard
				+ ", serviceId=" + serviceId + ", petKind=" + petKind
				+ ", serviceType=" + serviceType + ", level=" + level
				+ ", ExtraItems=" + ExtraItems + ", serviceLoc=" + serviceLoc
				+ ", addressId=" + addressId + ", address=" + address
				+ ", shopName=" + shopName + ", shopId=" + shopId
				+ ", petExtra=" + petExtra + ", shopAddress=" + shopAddress
				+ ", isSelect=" + isSelect + ", getShopAddress()="
				+ getShopAddress() + ", getServiceLoc()=" + getServiceLoc()
				+ ", getAddressId()=" + getAddressId() + ", getAddress()="
				+ getAddress() + ", getShopName()=" + getShopName()
				+ ", getShopId()=" + getShopId() + ", getPetExtra()="
				+ getPetExtra() + ", isSelect()=" + isSelect()
				+ ", getServiceType()=" + getServiceType() + ", getLevel()="
				+ getLevel() + ", getPetKind()=" + getPetKind()
				+ ", getServiceId()=" + getServiceId() + ", getAvatar()="
				+ getAvatar() + ", getPetName()=" + getPetName()
				+ ", getPetId()=" + getPetId() + ", getMyPetId()="
				+ getMyPetId() + ", getNickname()=" + getNickname()
				+ ", getLevelName()=" + getLevelName() + ", getPrice()="
				+ getPrice() + ", isUseCard()=" + isUseCard()
				+ ", getExtraItems()=" + getExtraItems() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	public AppointMentNewPetInfo() {
		super();
	}

}
