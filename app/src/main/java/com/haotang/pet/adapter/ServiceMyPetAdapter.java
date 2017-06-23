package com.haotang.pet.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.haotang.pet.R;
import com.haotang.pet.entity.MyPet;
import com.haotang.pet.util.GlideUtil;
import com.haotang.pet.view.CommonAdapter;
import com.haotang.pet.view.ViewHolder;
/**
 * ServiceNewActivity 界面我的宠物
 * @author Administrator
 *
 * @param <T>
 */
public class ServiceMyPetAdapter<T> extends CommonAdapter<T>{

	public ServiceMyPetAdapter(Activity mContext, List<T> mDatas) {
		super(mContext, mDatas);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder =ViewHolder.get(mContext, convertView, parent, R.layout.item_servicemypet, position);
		MyPet myPet = (MyPet) mDatas.get(position);
		viewHolder.setText(R.id.item_service_mypet_name,myPet.nickname);
		viewHolder.setText(R.id.item_service_mypet_kind_name,myPet.petName);
		ImageView view = viewHolder.getView(R.id.cir_pet_icon);
		if (myPet.selected==0) {
			viewHolder.setBackgroundResource(R.id.img_is_choose, R.drawable.complaint_reason_disable);
		}else if (myPet.selected==1) {
			viewHolder.setBackgroundResource(R.id.img_is_choose, R.drawable.complaint_reason);
		}
		GlideUtil.loadCircleImg(mContext, myPet.avatar, view, R.drawable.user_icon_unnet_circle);
		if (position==(mDatas.size()-1)) {
			viewHolder.getView(R.id.view_bottom_line).setVisibility(View.GONE);
		}else {
			viewHolder.getView(R.id.view_bottom_line).setVisibility(View.VISIBLE);
		}
		return viewHolder.getConvertView();
	}

}
