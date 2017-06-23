package com.haotang.pet.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.haotang.pet.R;
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
public class AppointNewVBAdapter extends BaseBannerAdapter<String> {
	private List<String> mDatas;

	public AppointNewVBAdapter(List<String> datas) {
		super(datas);
	}

	@Override
	public View getView(VerticalBannerView parent) {
		return LayoutInflater.from(parent.getContext()).inflate(
				R.layout.item_appointnewvb_adapter, null);
	}

	@Override
	public void setItem(final View view, final String data) {
		TextView tv_item_appointnewvb_adapter = (TextView) view
				.findViewById(R.id.tv_item_appointnewvb_adapter);
		if (Utils.isStrNull(data)) {
			Utils.setText(tv_item_appointnewvb_adapter, data, "", View.VISIBLE,
					View.GONE);
		}
	}

	public OnItemClickListener onItemClickListener = null;

	public interface OnItemClickListener {
		public void OnItemClick(String data);
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}
}
