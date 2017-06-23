package com.haotang.pet.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haotang.pet.R;
import com.haotang.pet.entity.AppointNewWorker.DataBean.ServicesBean.LevelsBean;
import com.haotang.pet.entity.AppointNewWorker.DataBean.WorkersBean;
import com.haotang.pet.entity.AppointNewWorker.DataBean.WorkersBean.Workers;
import com.haotang.pet.util.Utils;
import com.haotang.pet.view.CommonAdapter;
import com.haotang.pet.view.ViewHolder;

public class AppointMentNewLevelAdapter<T> extends CommonAdapter<T> {
	private int index;
	private List<WorkersBean> workers;

	public AppointMentNewLevelAdapter(Activity mContext, List<T> mDatas) {
		super(mContext, mDatas);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent,
				R.layout.item_appointmentnew_level, position);
		RelativeLayout rl_item_appointmentnewlevel_root = viewHolder
				.getView(R.id.rl_item_appointmentnewlevel_root);
		TextView tv_item_appointmentnewlevel_name = viewHolder
				.getView(R.id.tv_item_appointmentnewlevel_name);
		RelativeLayout rl_item_appointmentnewlevel = viewHolder
				.getView(R.id.rl_item_appointmentnewlevel);
		TextView tv_item_appointmentnewlevel_des = viewHolder
				.getView(R.id.tv_item_appointmentnewlevel_des);
		View vw_item_appointmentnewlevel = viewHolder
				.getView(R.id.vw_item_appointmentnewlevel);
		View vw_item_appointmentnewlevel_right = viewHolder
				.getView(R.id.vw_item_appointmentnewlevel_right);
		if (position == mDatas.size() - 1) {
			vw_item_appointmentnewlevel_right.setVisibility(View.GONE);
		} else {
			vw_item_appointmentnewlevel_right.setVisibility(View.VISIBLE);
		}
		LevelsBean levelsBean = (LevelsBean) mDatas.get(position);
		if (levelsBean != null) {
			if (Utils.isStrNull(levelsBean.getTip())) {
				rl_item_appointmentnewlevel.setVisibility(View.VISIBLE);
			} else {
				rl_item_appointmentnewlevel.setVisibility(View.GONE);
			}
			Utils.setText(tv_item_appointmentnewlevel_name,
					levelsBean.getTitle(), "", View.VISIBLE, View.GONE);
			Utils.setText(tv_item_appointmentnewlevel_des,
					levelsBean.getDesc(), "", View.VISIBLE, View.GONE);
			if (workers != null && workers.size() > 0) {
				WorkersBean workersBean = workers.get(position);
				if (workersBean != null) {
					List<Workers> workers2 = workersBean.getWorkers();
					if (workers2 != null && workers2.size() > 0) {
						if (index == position) {
							vw_item_appointmentnewlevel
									.setVisibility(View.VISIBLE);
							tv_item_appointmentnewlevel_name
									.setTextColor(mContext.getResources()
											.getColor(R.color.aD1494F));
							tv_item_appointmentnewlevel_des
									.setTextColor(mContext.getResources()
											.getColor(R.color.aD1494F));
						} else {
							vw_item_appointmentnewlevel
									.setVisibility(View.INVISIBLE);
							tv_item_appointmentnewlevel_name
									.setTextColor(mContext.getResources()
											.getColor(R.color.a333333));
							tv_item_appointmentnewlevel_des
									.setTextColor(mContext.getResources()
											.getColor(R.color.a666666));
						}
					} else {
						vw_item_appointmentnewlevel
								.setVisibility(View.INVISIBLE);
						tv_item_appointmentnewlevel_name.setTextColor(mContext
								.getResources().getColor(R.color.acccccc));
						tv_item_appointmentnewlevel_des.setTextColor(mContext
								.getResources().getColor(R.color.acccccc));
					}
				} else {
					vw_item_appointmentnewlevel.setVisibility(View.INVISIBLE);
					tv_item_appointmentnewlevel_name.setTextColor(mContext
							.getResources().getColor(R.color.acccccc));
					tv_item_appointmentnewlevel_des.setTextColor(mContext
							.getResources().getColor(R.color.acccccc));
				}
			} else {
				vw_item_appointmentnewlevel.setVisibility(View.INVISIBLE);
				tv_item_appointmentnewlevel_name.setTextColor(mContext
						.getResources().getColor(R.color.acccccc));
				tv_item_appointmentnewlevel_des.setTextColor(mContext
						.getResources().getColor(R.color.acccccc));
			}
			rl_item_appointmentnewlevel_root
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							if (onItemNewLevelClickListener != null) {
								onItemNewLevelClickListener
										.OnItemNewLevelClick(position);
							}
						}
					});
			rl_item_appointmentnewlevel
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							if (onNewLevelWenHaoClickListener != null) {
								onNewLevelWenHaoClickListener
										.OnNewLevelWenHaoClick(position);
							}
						}
					});
		}
		return viewHolder.getConvertView();
	}

	public void setWorkers(List<WorkersBean> workers) {
		this.workers = workers;
		notifyDataSetChanged();
	}

	public void setIndex(int index) {
		this.index = index;
		notifyDataSetChanged();
	}

	public OnItemNewLevelClickListener onItemNewLevelClickListener = null;

	public interface OnItemNewLevelClickListener {
		public void OnItemNewLevelClick(int position);
	}

	public void setOnItemNewLevelClickListener(
			OnItemNewLevelClickListener onItemNewLevelClickListener) {
		this.onItemNewLevelClickListener = onItemNewLevelClickListener;
	}

	public OnNewLevelWenHaoClickListener onNewLevelWenHaoClickListener = null;

	public interface OnNewLevelWenHaoClickListener {
		public void OnNewLevelWenHaoClick(int position);
	}

	public void setOnNewLevelWenHaoClickListener(
			OnNewLevelWenHaoClickListener onNewLevelWenHaoClickListener) {
		this.onNewLevelWenHaoClickListener = onNewLevelWenHaoClickListener;
	}

}
