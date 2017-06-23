package com.haotang.pet.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.haotang.pet.R;
import com.haotang.pet.entity.ServiceType;
import com.haotang.pet.util.ImageLoaderUtil;
import com.haotang.pet.view.CommonAdapter;
import com.haotang.pet.view.ViewHolder;

public class ServiceTypeAdapter<T> extends CommonAdapter<T>{

	public ServiceTypeAdapter(Activity mContext, List<T> mDatas) {
		super(mContext, mDatas);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ServiceType serviceType = (ServiceType) mDatas.get(position);
		ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.item_service_type, position);
		viewHolder.setText(R.id.textview_go_shop_name_and_price,serviceType.name);
		viewHolder.setText(R.id.service_go_shop_show_detail,serviceType.desc_bottom);
		viewHolder.getView(R.id.item_layout_isshow).setVisibility(View.VISIBLE);
		viewHolder.getView(R.id.textview_service_text_price_from_go_shop).setVisibility(View.VISIBLE);
		viewHolder.getView(R.id.textview_service_price_mark_go_shop).setVisibility(View.VISIBLE);
		viewHolder.getView(R.id.textview_service_text_price_from_detail_go_shop).setVisibility(View.VISIBLE);
		viewHolder.setText(R.id.textview_service_text_price_from_go_shop,serviceType.price+"");
		if (serviceType.price<=0) {
			viewHolder.getView(R.id.textview_service_text_price_from_go_shop).setVisibility(View.GONE);
			viewHolder.getView(R.id.textview_service_price_mark_go_shop).setVisibility(View.GONE);
			viewHolder.getView(R.id.textview_service_text_price_from_detail_go_shop).setVisibility(View.GONE);
			viewHolder.getView(R.id.item_layout_isshow).setVisibility(View.GONE);
		}
		viewHolder.setText(R.id.button_go_apponit_shop,serviceType.btn_txt);
		viewHolder.setBackgroundCircle(R.id.imageview_go_shop_icon, serviceType.icon,0);
		viewHolder.getView(R.id.textview_serviceType).setVisibility(View.GONE);
		if (!TextUtils.isEmpty(serviceType.disabled_tip)) {
			viewHolder.getView(R.id.textview_serviceType).setVisibility(View.VISIBLE);
			viewHolder.setText(R.id.textview_serviceType, serviceType.disabled_tip);
			viewHolder.setBackgroundResource(R.id.button_go_apponit_shop, R.drawable.bg_button_service_new_unappoint);
		}else {
			viewHolder.setBackgroundResource(R.id.button_go_apponit_shop, R.drawable.bg_button_service_new_appoint_ok);
			viewHolder.getView(R.id.textview_serviceType).setVisibility(View.GONE);
		}
		RelativeLayout layout = viewHolder.getView(R.id.re_layout_go_shop);
		if (serviceType.serviceLoc==1) {
			layout.setBackgroundColor(Color.parseColor("#F8F4EB"));
		}else if (serviceType.serviceLoc==2) {
			layout.setBackgroundColor(Color.parseColor("#EFF4F7"));
		}
		return viewHolder.getConvertView();
	}

}
