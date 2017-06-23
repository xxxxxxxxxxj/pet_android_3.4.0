package com.haotang.pet.adapter;

import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haotang.pet.R;
import com.haotang.pet.entity.AppointMentNewPetInfo;
import com.haotang.pet.util.Utils;
import com.haotang.pet.view.CommonAdapter;
import com.haotang.pet.view.ViewHolder;

public class AppointNewChangeBeautyDialogPetAdapter<T> extends CommonAdapter<T> {

	public AppointNewChangeBeautyDialogPetAdapter(Activity mContext,
			List<T> mDatas) {
		super(mContext, mDatas);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent,
				R.layout.item_appointmentnewdialogpet, position);
		TextView tv_item_appointmentnewdialogpet_petname = viewHolder
				.getView(R.id.tv_item_appointmentnewdialogpet_petname);
		TextView tv_item_appointmentnewdialogpet_name = viewHolder
				.getView(R.id.tv_item_appointmentnewdialogpet_name);
		ImageView iv_item_appointmentnewdialogpet = viewHolder
				.getView(R.id.iv_item_appointmentnewdialogpet);
		final AppointMentNewPetInfo appointMentNewPetInfo = (AppointMentNewPetInfo) mDatas
				.get(position);
		if (appointMentNewPetInfo != null) {
			Utils.setText(tv_item_appointmentnewdialogpet_petname,
					appointMentNewPetInfo.getPetName(), "", View.VISIBLE,
					View.GONE);
			Utils.setText(tv_item_appointmentnewdialogpet_name,
					appointMentNewPetInfo.getNickname(), "", View.VISIBLE,
					View.GONE);
			if (appointMentNewPetInfo.isSelect()) {
				iv_item_appointmentnewdialogpet
						.setBackgroundResource(R.drawable.icon_pay_selected);
			} else {
				iv_item_appointmentnewdialogpet
						.setBackgroundResource(R.drawable.icon_pay_normal);
			}
		}
		return viewHolder.getConvertView();
	}
}
