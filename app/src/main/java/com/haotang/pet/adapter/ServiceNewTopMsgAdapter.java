package com.haotang.pet.adapter;

import java.util.List;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.haotang.pet.R;
import com.haotang.pet.entity.ServiceTypeTopMsg;
import com.haotang.pet.verticalbanner.BaseBannerAdapter;
import com.haotang.pet.verticalbanner.VerticalBannerView;

public class ServiceNewTopMsgAdapter extends BaseBannerAdapter<ServiceTypeTopMsg>{
	public int currentPosition;
	public ServiceNewTopMsgAdapter(List<ServiceTypeTopMsg> datas) {
		super(datas);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(VerticalBannerView parent) {
		// TODO Auto-generated method stub
		return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicenew_top_msg, null);
	}

	@Override
	public void setItem(View view, ServiceTypeTopMsg data) {
		// TODO Auto-generated method stub
		TextView textview_show_top_msg = (TextView) view.findViewById(R.id.textview_show_top_msg);
		if (data!=null) {
			textview_show_top_msg.setText(data.content);
		}
	}

//	@Override
//	public String getItem(int position) {
//		// TODO Auto-generated method stub
//		currentPosition = position;
//		return super.getItem(position);
//	}
//	@Override
//	public void setItem(View view, String data) {
//		// TODO Auto-generated method stub
//		TextView textview_show_top_msg = (TextView) view.findViewById(R.id.textview_show_top_msg);
//		if (!TextUtils.isEmpty(data)) {
//			textview_show_top_msg.setText(data);
//		}
//	}
	public int getPosition(){
		return currentPosition;
	}
	
}
