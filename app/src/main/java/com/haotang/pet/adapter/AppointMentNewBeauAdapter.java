package com.haotang.pet.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haotang.pet.R;
import com.haotang.pet.entity.AppointNewWorker.DataBean.WorkersBean.Workers;
import com.haotang.pet.util.GlideUtil;
import com.haotang.pet.util.ImageLoaderUtil;
import com.haotang.pet.util.Utils;
import com.haotang.pet.view.CommonAdapter;
import com.haotang.pet.view.GlideCircleTransform;
import com.haotang.pet.view.ViewHolder;

public class AppointMentNewBeauAdapter<T> extends CommonAdapter<T> {
	private int index;

	public AppointMentNewBeauAdapter(Activity mContext, List<T> mDatas) {
		super(mContext, mDatas);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent,
				R.layout.item_appointmentnew_beau, position);
		RelativeLayout rl_item_appointmentnew_beauroot = viewHolder
				.getView(R.id.rl_item_appointmentnew_beauroot);
		TextView tv_item_appointmentnew_beau_des = viewHolder
				.getView(R.id.tv_item_appointmentnew_beau_des);
		ImageView iv_item_appointmentnew_beau_img = viewHolder
				.getView(R.id.iv_item_appointmentnew_beau_img);
		TextView tv_item_appointmentnew_beau_name = viewHolder
				.getView(R.id.tv_item_appointmentnew_beau_name);
		TextView tv_item_appointmentnew_beau_select = viewHolder
				.getView(R.id.tv_item_appointmentnew_beau_select);
		Workers workers = (Workers) mDatas.get(position);
		if (workers != null) {
			GlideUtil.loadCircleImg(mContext, workers.getAvatar(),
					iv_item_appointmentnew_beau_img,
					R.drawable.user_icon_unnet_circle);
			Utils.setText(tv_item_appointmentnew_beau_des,
					workers.getDesc_bottom(), "", View.VISIBLE, View.INVISIBLE);
			Utils.setText(tv_item_appointmentnew_beau_name,
					workers.getRealname(), "", View.VISIBLE, View.GONE);
			if (workers.getIsAvail() == 1) {// 可约
				tv_item_appointmentnew_beau_select.setText("选我");
				tv_item_appointmentnew_beau_name.setTextColor(mContext
						.getResources().getColor(R.color.a333333));
				if (index == position) {// 选中
					rl_item_appointmentnew_beauroot.setBackgroundColor(mContext
							.getResources().getColor(R.color.aF8F1E8));
					tv_item_appointmentnew_beau_select.setTextColor(mContext
							.getResources().getColor(R.color.white));
					tv_item_appointmentnew_beau_select
							.setBackgroundResource(R.drawable.tv_redselect_selector);
				} else {// 未选中
					rl_item_appointmentnew_beauroot.setBackgroundColor(mContext
							.getResources().getColor(R.color.af8f8f8));
					tv_item_appointmentnew_beau_select.setTextColor(mContext
							.getResources().getColor(R.color.aD1494F));
					tv_item_appointmentnew_beau_select
							.setBackgroundResource(R.drawable.tv_redborder_selector);
				}
			} else if (workers.getIsAvail() == 0) {// 约满
				tv_item_appointmentnew_beau_select.setText("已约满");
				rl_item_appointmentnew_beauroot.setBackgroundColor(mContext
						.getResources().getColor(R.color.af8f8f8));
				tv_item_appointmentnew_beau_name.setTextColor(mContext
						.getResources().getColor(R.color.a999999));
				tv_item_appointmentnew_beau_select.setTextColor(mContext
						.getResources().getColor(R.color.aBBBBBB));
				tv_item_appointmentnew_beau_select
						.setBackgroundResource(R.drawable.tv_yueman_selector);
			}
		}
		return viewHolder.getConvertView();
	}

	public void setIndex(int index) {
		this.index = index;
		notifyDataSetChanged();
	}

}
