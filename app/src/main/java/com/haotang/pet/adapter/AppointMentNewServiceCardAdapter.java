package com.haotang.pet.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haotang.pet.R;
import com.haotang.pet.entity.AppointNewCard;
import com.haotang.pet.util.GlideUtil;
import com.haotang.pet.util.ImageLoaderUtil;
import com.haotang.pet.util.Utils;
import com.haotang.pet.view.CommonAdapter;
import com.haotang.pet.view.ViewHolder;

public class AppointMentNewServiceCardAdapter<T> extends CommonAdapter<T> {

	public AppointMentNewServiceCardAdapter(Activity mContext, List<T> mDatas) {
		super(mContext, mDatas);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent,
				R.layout.item_appointmentnewservicecard, position);
		ImageView iv_item_appointmentnewservicecard = viewHolder
				.getView(R.id.iv_item_appointmentnewservicecard);
		TextView tv_item_appointmentnewservicecard = viewHolder
				.getView(R.id.tv_item_appointmentnewservicecard);
		AppointNewCard appointNewCard = (AppointNewCard) mDatas.get(position);
		if (appointNewCard != null) {
			GlideUtil.loadImg(mContext,appointNewCard.cardImg,
					iv_item_appointmentnewservicecard,
					R.drawable.icon_production_default);
			Utils.setText(tv_item_appointmentnewservicecard,
					appointNewCard.descTop, "", View.VISIBLE, View.GONE);
		}
		return viewHolder.getConvertView();
	}

}
