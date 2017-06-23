package com.haotang.pet.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haotang.pet.R;
import com.haotang.pet.entity.AppointMentNewPetInfo;
import com.haotang.pet.util.GlideUtil;
import com.haotang.pet.util.ImageLoaderUtil;
import com.haotang.pet.view.CircleImageView;
import com.haotang.pet.view.CommonAdapter;
import com.haotang.pet.view.ViewHolder;

public class OrderMyPetAdapter<T> extends CommonAdapter<T>{

	public OrderMyPetAdapter(Activity mContext, List<T> mDatas) {
		super(mContext, mDatas);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AppointMentNewPetInfo petInfo =  (AppointMentNewPetInfo) mDatas.get(position);
		ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.item_order_mypet_new, position);
		viewHolder.setImageGlideCircle(mContext,R.id.item_order_peticon, petInfo.getAvatar(), R.drawable.user_icon_unnet_circle);
		viewHolder.setText(R.id.textview_item_petnickname, petInfo.getNickname());
		viewHolder.setText(R.id.textview_item_petkind, petInfo.getPetName());
		viewHolder.setText(R.id.textview_appointstyle, petInfo.getLevelName());
		
		double totalMoney = 0;
		double extraPricaTotal = 0;
		double itemLastPrice = 0;
		String addServiceIds="";
//		totalMoney = totalMoney+petInfo.getPrice();
		StringBuilder builder = new StringBuilder();
		StringBuilder builderIds = new StringBuilder();
		if (petInfo.getExtraItems()!=null) {
			if (petInfo.getExtraItems().size()>0) {
				for (int i = 0; i < petInfo.getExtraItems().size(); i++) {
					extraPricaTotal+=petInfo.getExtraItems().get(i).getPrice();
					builder.append(petInfo.getExtraItems().get(i).getItemName()+" ¥"+petInfo.getExtraItems().get(i).getPrice()+"  ");
					builderIds.append(petInfo.getExtraItems().get(i).getItemId()+",");
				}
			}
		}
		if (!TextUtils.isEmpty(builderIds)) {
			if (builderIds.length()>0) {
				addServiceIds = builderIds.substring(0, builderIds.length()-1);
			}
			petInfo.setAddServiceIds(addServiceIds);
		}else {
			petInfo.setAddServiceIds(null);
		}
		petInfo.setPetExtra(builder.toString());
		totalMoney = petInfo.getPrice()+extraPricaTotal;
		
		if (petInfo.isUseCard()) {
			itemLastPrice = totalMoney-petInfo.getPrice();
			viewHolder.setText(R.id.textview_appoinstyle_price, "¥"+petInfo.getPrice());
			TextView textview_appoinstyle_price = viewHolder.getView(R.id.textview_appoinstyle_price);
			TextPaint paint = textview_appoinstyle_price.getPaint();
			paint.setAntiAlias(true);
			paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 中划线
			viewHolder.setText(R.id.textview_apponitstyle_last_price, "¥"+0);
			viewHolder.getView(R.id.textview_apponitstyle_last_price).setVisibility(View.VISIBLE);
			viewHolder.getView(R.id.textview_apponitstyle_is_used_card).setVisibility(View.VISIBLE);
			viewHolder.setText(R.id.textview_item_pet_price, itemLastPrice+"");
		}else {
			viewHolder.setText(R.id.textview_appoinstyle_price, "¥"+petInfo.getPrice());
			viewHolder.getView(R.id.textview_apponitstyle_last_price).setVisibility(View.GONE);
			viewHolder.getView(R.id.textview_apponitstyle_is_used_card).setVisibility(View.GONE);
			viewHolder.setText(R.id.textview_item_pet_price, totalMoney+"");
		}
		if (TextUtils.isEmpty(petInfo.getPetExtra())) {
			viewHolder.getView(R.id.textview_signle).setVisibility(View.GONE);
		}else {
			viewHolder.getView(R.id.textview_signle).setVisibility(View.VISIBLE);
			viewHolder.setText(R.id.textview_signle, petInfo.getPetExtra());
		}
		if (position==mDatas.size()-1) {
			viewHolder.getView(R.id.view_bottom_line).setVisibility(View.GONE);
		}else {
			viewHolder.getView(R.id.view_bottom_line).setVisibility(View.VISIBLE);
		}
		return viewHolder.getConvertView();
	}

}
