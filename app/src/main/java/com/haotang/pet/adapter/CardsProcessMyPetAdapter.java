package com.haotang.pet.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.haotang.pet.R;
import com.haotang.pet.entity.MyPet;
import com.haotang.pet.view.CommonAdapter;
import com.haotang.pet.view.ViewHolder;

public class CardsProcessMyPetAdapter<T> extends CommonAdapter<T>{

	public CardsProcessMyPetAdapter(Activity mContext, List<T> mDatas) {
		super(mContext, mDatas);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.item_cards_mypet_process_detail, position);
		MyPet myPet = (MyPet) mDatas.get(position);
		if (myPet.selected==1) {
			viewHolder.setBackgroundResource(R.id.img_is_choose, R.drawable.complaint_reason);
		}else if (myPet.selected==0) {
			viewHolder.setBackgroundResource(R.id.img_is_choose, R.drawable.complaint_reason_disable);
		}
		viewHolder.setText(R.id.textview_pet_nickname, myPet.nickname);
		viewHolder.setText(R.id.textview_petname, myPet.petName);
		return viewHolder.getConvertView();
	}

}
