package com.haotang.pet.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.haotang.pet.R;
import com.haotang.pet.entity.Recommendation;
import com.haotang.pet.util.Utils;
import com.haotang.pet.verticalbanner.BaseBannerAdapter;
import com.haotang.pet.verticalbanner.VerticalBannerView;

/**
 * <p>
 * Title:TopMsgAdapter
 * </p>
 * <p>
 * Description:顶部跑马灯
 * </p>
 * <p>
 * Company:北京昊唐科技有限公司
 * </p>
 * 
 * @author 徐俊
 * @date 2017-6-2 下午2:41:06
 */
public class TopMsgAdapter extends BaseBannerAdapter<Recommendation> {
	private List<Recommendation> mDatas;

	public TopMsgAdapter(List<Recommendation> datas) {
		super(datas);
	}

	@Override
	public View getView(VerticalBannerView parent) {
		return LayoutInflater.from(parent.getContext()).inflate(
				R.layout.item_top_msg, null);
	}

	@Override
	public void setItem(final View view, final Recommendation data) {
		TextView tv_item_topmsg = (TextView) view
				.findViewById(R.id.tv_item_topmsg);
		if (data != null) {
			Utils.setText(tv_item_topmsg, data.content, "", View.VISIBLE,
					View.GONE);
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (onItemClickListener != null) {
						onItemClickListener.OnItemClick(data);
					}
				}
			});
		}
	}

	public OnItemClickListener onItemClickListener = null;

	public interface OnItemClickListener {
		public void OnItemClick(Recommendation data);
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}
}
