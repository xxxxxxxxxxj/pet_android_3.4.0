package com.haotang.pet.entity;

public class ServiceShopAdd {
//	public int addressId;
//	public String address;
//	public double addlat;
//	public double addlng;
	
	public int shopId;
	public String shopName;
	public String shopAddress;
	public double shoplat;
	public double shoplng;
	@Override
	public String toString() {
		return "ServiceShopAdd [shopId=" + shopId + ", shopName=" + shopName
				+ ", shopAddress=" + shopAddress + ", shoplat=" + shoplat
				+ ", shoplng=" + shoplng + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
}
