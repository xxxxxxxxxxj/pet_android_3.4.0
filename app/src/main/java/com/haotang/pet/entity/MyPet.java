package com.haotang.pet.entity;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class MyPet implements Parcelable{
	public String avatar;
	public String petName;
	public int petId;
	public int myPetId;
	public String nickname;
	public int selected;
	public int petKind;
	//以下为单独新版预约选择美容师界面单独使用
	public int serviceId;//区分猫狗服务id 狗 1 洗澡、2 美容 猫 3 洗澡、4美容
	public int serviceType;//服务类目
	public int serviceLoc;//服务方式
	public int addressId;//地址id
	public String address;//宠物地址
	public String shopName;//门店名称
	public String shopAddress;//门店地址
	public int shopId;//门店id
	public List<ExtraItem> extraItems;
	
	public List<ExtraItem> getExtraItems() {
		return extraItems;
	}
	public void setExtraItems(List<ExtraItem> extraItems) {
		this.extraItems = extraItems;
	}
	public MyPet(){
		
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(avatar);
		dest.writeString(petName);
		dest.writeInt(petId);
		dest.writeInt(myPetId);
		dest.writeString(nickname);
		dest.writeInt(selected);
		dest.writeInt(petKind);
		dest.writeInt(serviceId);
		dest.writeInt(serviceType);
		dest.writeInt(serviceLoc);
		dest.writeInt(addressId);
		dest.writeString(address);
		dest.writeString(shopName);
		dest.writeString(shopAddress);
		dest.writeInt(shopId);
	}
	
	public static final Parcelable.Creator<MyPet> CREATOR = new Creator<MyPet>() {

		@Override
		public MyPet createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new MyPet(source);
		}

		@Override
		public MyPet[] newArray(int size) {
			// TODO Auto-generated method stub
			return new MyPet[size];
		}

	};
	public MyPet(Parcel in){
		avatar = in.readString();
		petName = in.readString();
		petId = in.readInt();
		myPetId = in.readInt();
		nickname = in.readString();
		selected = in.readInt();
		petKind = in.readInt();
		serviceId = in.readInt();
		serviceType = in.readInt();
		serviceLoc = in.readInt();
		addressId = in.readInt();
		address = in.readString();
		shopName = in.readString();
		shopAddress = in.readString();
		shopId = in.readInt();
	}

}
