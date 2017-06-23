package com.haotang.pet.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haotang.pet.R;
import com.haotang.pet.entity.AppointMentNewPetInfo;
import com.haotang.pet.entity.ExtraItem;
import com.haotang.pet.util.Global;
import com.haotang.pet.util.UmengStatistics;
import com.haotang.pet.util.Utils;
import com.haotang.pet.view.CommonAdapter;
import com.haotang.pet.view.ViewHolder;

public class AppointMentNewExtraItemsAdapter<T> extends CommonAdapter<T> {
	private PopupWindow pWin;
	public OnItemNewExtraItemsClickListener onItemNewExtraItemsClickListener = null;
	private List<AppointMentNewPetInfo> appointMentNewPetInfoList;

	public interface OnItemNewExtraItemsClickListener {
		public void OnItemNewExtraItemsClick(int position);
	}

	public void setOnItemNewExtraItemsClickListener(
			OnItemNewExtraItemsClickListener onItemNewExtraItemsClickListener) {
		this.onItemNewExtraItemsClickListener = onItemNewExtraItemsClickListener;
	}

	public AppointMentNewExtraItemsAdapter(Activity mContext, List<T> mDatas) {
		super(mContext, mDatas);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent,
				R.layout.item_appointmentnewextraitem, position);
		TextView tv_item_appointmentnewextraitem_name = viewHolder
				.getView(R.id.tv_item_appointmentnewextraitem_name);
		TextView tv_item_appointmentnewextraitem_yuanprice = viewHolder
				.getView(R.id.tv_item_appointmentnewextraitem_yuanprice);
		TextView tv_item_appointmentnewextraitem_xianprice = viewHolder
				.getView(R.id.tv_item_appointmentnewextraitem_xianprice);
		RelativeLayout rl_item_appointmentnewextraitem_wenhao = viewHolder
				.getView(R.id.rl_item_appointmentnewextraitem_wenhao);
		TextView tv_item_appointmentnewextraitem_qipao = viewHolder
				.getView(R.id.tv_item_appointmentnewextraitem_qipao);
		tv_item_appointmentnewextraitem_qipao.bringToFront();
		final ExtraItem extraItem = (ExtraItem) mDatas.get(position);
		if (extraItem != null) {
			int petNum = 0;
			if (appointMentNewPetInfoList != null
					&& appointMentNewPetInfoList.size() > 0) {
				for (int i = 0; i < appointMentNewPetInfoList.size(); i++) {
					AppointMentNewPetInfo appointMentNewPetInfo = appointMentNewPetInfoList
							.get(i);
					if (appointMentNewPetInfo != null) {
						List<ExtraItem> extraItems = appointMentNewPetInfo
								.getExtraItems();
						if (extraItems != null && extraItems.size() > 0) {
							for (int j = 0; j < extraItems.size(); j++) {
								ExtraItem extraItem2 = extraItems.get(j);
								if (extraItem2.getItemId() == extraItem
										.getItemId()) {
									petNum++;
								}
							}
						}
					}
				}
			}
			if (petNum > 0) {
				tv_item_appointmentnewextraitem_qipao.setText(String
						.valueOf(petNum));
				tv_item_appointmentnewextraitem_qipao
						.setVisibility(View.VISIBLE);
			} else {
				tv_item_appointmentnewextraitem_qipao.setVisibility(View.GONE);
			}
			if (Utils.isStrNull(extraItem.getDesc())) {
				rl_item_appointmentnewextraitem_wenhao
						.setVisibility(View.VISIBLE);
			} else {
				rl_item_appointmentnewextraitem_wenhao.setVisibility(View.GONE);
			}
			Utils.setText(tv_item_appointmentnewextraitem_name,
					extraItem.getItemName(), "", View.VISIBLE, View.GONE);
			Utils.setText(tv_item_appointmentnewextraitem_xianprice, "¥"
					+ Utils.formatDouble(extraItem.getPrice()), "",
					View.VISIBLE, View.GONE);
			if (extraItem.getListPrice() > 0) {
				tv_item_appointmentnewextraitem_xianprice.setTextColor(mContext
						.getResources().getColor(R.color.aD1494F));
				tv_item_appointmentnewextraitem_yuanprice
						.setVisibility(View.VISIBLE);
				// 中间加横线
				tv_item_appointmentnewextraitem_yuanprice.getPaint().setFlags(
						Paint.STRIKE_THRU_TEXT_FLAG);
				Utils.setText(tv_item_appointmentnewextraitem_yuanprice, "¥"
						+ Utils.formatDouble(extraItem.getListPrice()), "",
						View.VISIBLE, View.GONE);
			} else {
				tv_item_appointmentnewextraitem_xianprice.setTextColor(mContext
						.getResources().getColor(R.color.a666666));
				tv_item_appointmentnewextraitem_yuanprice
						.setVisibility(View.GONE);
				// 去掉横线
				tv_item_appointmentnewextraitem_yuanprice.getPaint()
						.setFlags(0);
			}
			rl_item_appointmentnewextraitem_wenhao
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							UmengStatistics.UmengEventStatistics(mContext,
									Global.UmengEventID.click_IndividualCorner);
							if (Utils.isStrNull(extraItem.getDesc())) {
								showWenHaoDialog(extraItem.getDesc());
							}
						}
					});
			viewHolder.getConvertView().setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							if (onItemNewExtraItemsClickListener != null) {
								onItemNewExtraItemsClickListener
										.OnItemNewExtraItemsClick(position);
							}
						}
					});
		}
		return viewHolder.getConvertView();
	}

	protected void showWenHaoDialog(String tip) {
		pWin = null;
		if (pWin == null) {
			final View view = View.inflate(mContext,
					R.layout.pw_appointnew_wenhao, null);
			ImageButton ib_pw_appointnewwenhao_close = (ImageButton) view
					.findViewById(R.id.ib_pw_appointnewwenhao_close);
			TextView tv_pw_appointnewwenhao_msg = (TextView) view
					.findViewById(R.id.tv_pw_appointnewwenhao_msg);
			tv_pw_appointnewwenhao_msg.setText(tip);
			pWin = new PopupWindow(view,
					android.view.ViewGroup.LayoutParams.MATCH_PARENT,
					android.view.ViewGroup.LayoutParams.MATCH_PARENT, true);
			pWin.setFocusable(true);
			pWin.setWidth(Utils.getDisplayMetrics(mContext)[0]);
			pWin.showAtLocation(view, Gravity.CENTER, 0, 0);
			ib_pw_appointnewwenhao_close
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							pWin.dismiss();
							pWin = null;
						}
					});
		}
	}

	public void setPetData(List<AppointMentNewPetInfo> appointMentNewPetInfoList) {
		this.appointMentNewPetInfoList = appointMentNewPetInfoList;
		notifyDataSetChanged();
	}
}
