package com.haotang.pet.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haotang.pet.R;
import com.haotang.pet.entity.AppointMentNewPetInfo;
import com.haotang.pet.entity.ExtraItem;
import com.haotang.pet.util.GlideUtil;
import com.haotang.pet.util.ImageLoaderUtil;
import com.haotang.pet.util.Utils;
import com.haotang.pet.view.CommonAdapter;
import com.haotang.pet.view.MyGridView;
import com.haotang.pet.view.ViewHolder;

public class AppointMentNewPetAdapter<T> extends CommonAdapter<T> {

	public AppointMentNewPetAdapter(Activity mContext, List<T> mDatas) {
		super(mContext, mDatas);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent,
				R.layout.item_appointmentnewpet, position);
		ImageView iv_item_appointmentnewpet_petimg = viewHolder
				.getView(R.id.iv_item_appointmentnewpet_petimg);
		TextView tv_item_appointmentnewpet_price = viewHolder
				.getView(R.id.tv_item_appointmentnewpet_price);
		TextView tv_item_appointmentnewpet_nickname = viewHolder
				.getView(R.id.tv_item_appointmentnewpet_nickname);
		TextView tv_item_appointmentnewpet_petname = viewHolder
				.getView(R.id.tv_item_appointmentnewpet_petname);
		TextView tv_item_appointmentnewpet_servicelevel = viewHolder
				.getView(R.id.tv_item_appointmentnewpet_servicelevel);
		TextView tv_item_appointmentnewpet_yuanprice = viewHolder
				.getView(R.id.tv_item_appointmentnewpet_yuanprice);
		TextView tv_item_appointmentnewpet_xianprice = viewHolder
				.getView(R.id.tv_item_appointmentnewpet_xianprice);
		TextView tv_item_appointmentnewpet_servicecard = viewHolder
				.getView(R.id.tv_item_appointmentnewpet_servicecard);
		MyGridView mgv_item_appointmentnewpet = viewHolder
				.getView(R.id.mgv_item_appointmentnewpet);
		View vw_item_appointmentnewpet = viewHolder
				.getView(R.id.vw_item_appointmentnewpet);
		AppointMentNewPetInfo appointMentNewPetInfo = (AppointMentNewPetInfo) mDatas
				.get(position);
		if (appointMentNewPetInfo != null) {
			GlideUtil.loadCircleImg(mContext, appointMentNewPetInfo.getAvatar(),
					iv_item_appointmentnewpet_petimg,
					R.drawable.user_icon_unnet_circle);
			Utils.setText(tv_item_appointmentnewpet_nickname,
					appointMentNewPetInfo.getNickname(), "", View.VISIBLE,
					View.GONE);
			Utils.setText(tv_item_appointmentnewpet_petname,
					appointMentNewPetInfo.getPetName(), "", View.VISIBLE,
					View.GONE);
			Utils.setText(tv_item_appointmentnewpet_servicelevel,
					appointMentNewPetInfo.getLevelName(), "", View.VISIBLE,
					View.GONE);
			if (appointMentNewPetInfo.getPrice() > 0) {
				Utils.setText(tv_item_appointmentnewpet_yuanprice, "¥"
						+ Utils.formatDouble(appointMentNewPetInfo.getPrice()), "", View.VISIBLE,
						View.GONE);
			} else {
				tv_item_appointmentnewpet_yuanprice.setVisibility(View.GONE);
			}
			if (appointMentNewPetInfo.isUseCard()) {
				// 中间加横线
				tv_item_appointmentnewpet_yuanprice.getPaint().setFlags(
						Paint.STRIKE_THRU_TEXT_FLAG);
				tv_item_appointmentnewpet_xianprice.setVisibility(View.VISIBLE);
				tv_item_appointmentnewpet_servicecard
						.setVisibility(View.VISIBLE);
				if (appointMentNewPetInfo.getExtraItems() != null
						&& appointMentNewPetInfo.getExtraItems().size() > 0) {
					double payPrice = 0;
					for (int i = 0; i < appointMentNewPetInfo.getExtraItems()
							.size(); i++) {
						ExtraItem extraItem = appointMentNewPetInfo
								.getExtraItems().get(i);
						if (extraItem != null) {
							payPrice += extraItem.getPrice();
						}
					}
					Utils.setText(tv_item_appointmentnewpet_price, "¥"
							+ Utils.formatDouble(payPrice), "", View.VISIBLE, View.GONE);
				} else {
					Utils.setText(tv_item_appointmentnewpet_price, "¥0", "",
							View.VISIBLE, View.GONE);
				}
			} else {
				tv_item_appointmentnewpet_yuanprice.getPaint().setFlags(0);
				tv_item_appointmentnewpet_xianprice.setVisibility(View.GONE);
				tv_item_appointmentnewpet_servicecard.setVisibility(View.GONE);
				if (appointMentNewPetInfo.getExtraItems() != null
						&& appointMentNewPetInfo.getExtraItems().size() > 0) {
					double payPrice = 0;
					for (int i = 0; i < appointMentNewPetInfo.getExtraItems()
							.size(); i++) {
						ExtraItem extraItem = appointMentNewPetInfo
								.getExtraItems().get(i);
						if (extraItem != null) {
							payPrice += extraItem.getPrice();
						}
					}
					Utils.setText(tv_item_appointmentnewpet_price, "¥"
							+ Utils.formatDouble((payPrice + appointMentNewPetInfo.getPrice())),
							"", View.VISIBLE, View.GONE);
				} else {
					Utils.setText(tv_item_appointmentnewpet_price, "¥"
									+ Utils.formatDouble(appointMentNewPetInfo.getPrice()), "",
							View.VISIBLE, View.GONE);
				}
			}
			if (appointMentNewPetInfo.getExtraItems() != null
					&& appointMentNewPetInfo.getExtraItems().size() > 0) {
				vw_item_appointmentnewpet.setVisibility(View.VISIBLE);
				mgv_item_appointmentnewpet.setVisibility(View.VISIBLE);
				mgv_item_appointmentnewpet
						.setAdapter(new AppointMentNewPetExtraItemAdapter<ExtraItem>(
								mContext, appointMentNewPetInfo.getExtraItems(),appointMentNewPetInfo.getMyPetId()));
			} else {
				vw_item_appointmentnewpet.setVisibility(View.GONE);
				mgv_item_appointmentnewpet.setVisibility(View.GONE);
			}
		}
		return viewHolder.getConvertView();
	}
}
