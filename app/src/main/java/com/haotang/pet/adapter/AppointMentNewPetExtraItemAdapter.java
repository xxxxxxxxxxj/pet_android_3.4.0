package com.haotang.pet.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haotang.pet.R;
import com.haotang.pet.entity.ExtraItem;
import com.haotang.pet.util.Global;
import com.haotang.pet.util.Utils;
import com.haotang.pet.view.CommonAdapter;
import com.haotang.pet.view.ViewHolder;

public class AppointMentNewPetExtraItemAdapter<T> extends CommonAdapter<T> {
	private int myPetId;

	public AppointMentNewPetExtraItemAdapter(Activity mContext, List<T> mDatas, int myPetId) {
		super(mContext, mDatas);
		this.myPetId = myPetId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent,
				R.layout.item_appointmentnewpetextraitem, position);
		TextView tv_item_appointmentnewpetextraitem_name = viewHolder
				.getView(R.id.tv_item_appointmentnewpetextraitem_name);
		TextView tv_item_appointmentnewpetextraitem_price = viewHolder
				.getView(R.id.tv_item_appointmentnewpetextraitem_price);
		ImageView iv_item_appointmentnewpetextraitem_delete = viewHolder
				.getView(R.id.iv_item_appointmentnewpetextraitem_delete);
		final ExtraItem extraItem = (ExtraItem) mDatas.get(position);
		if (extraItem != null) {
			Utils.setText(tv_item_appointmentnewpetextraitem_name,
					extraItem.getItemName(), "", View.VISIBLE, View.GONE);
			Utils.setText(tv_item_appointmentnewpetextraitem_price, "¥"
					+ Utils.formatDouble(extraItem.getPrice()), "", View.VISIBLE, View.GONE);
			iv_item_appointmentnewpetextraitem_delete
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent();
							Bundle bundle = new Bundle();
							intent.setAction("android.intent.action.AppointMentNewPetExtraItemAdapter");
							bundle.putInt("previous", Global.APPOINTNEW_DELETE_EXTRAITEM);
							bundle.putInt("itemId", extraItem.getItemId());
							bundle.putInt("myPetId", myPetId);
							intent.putExtras(bundle);
							mContext.sendBroadcast(intent);
						}
					});
		}
		return viewHolder.getConvertView();
	}
}
