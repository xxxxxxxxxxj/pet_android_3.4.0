package com.haotang.pet.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haotang.pet.R;
import com.haotang.pet.entity.CardsBuyMyPet;
import com.haotang.pet.entity.CardsBuyMyPet.Content;
import com.haotang.pet.view.CommonAdapter;
import com.haotang.pet.view.ViewHolder;

public class CardEveryDetailAdapter<T> extends CommonAdapter<T>{

	public CardEveryDetailAdapter(Activity mContext, List<T> mDatas) {
		super(mContext, mDatas);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		CardsBuyMyPet.Content contents= (Content) mDatas.get(position);
		ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.item_card_every_detail, position);
		viewHolder.setText(R.id.item_every_card_name, contents.packageTip);
		viewHolder.setText(R.id.item_card_count, contents.count+"");
		viewHolder.setText(R.id.item_card_old_price_title, contents.lpContent);
		viewHolder.setText(R.id.item_card_old_price, "¥"+contents.tlpri);
		viewHolder.setText(R.id.item_card_cutdown_price, "¥"+contents.tpri);
		TextView item_card_old_price = viewHolder.getView(R.id.item_card_old_price);
		TextPaint paint = item_card_old_price.getPaint();
		paint.setAntiAlias(true);
		paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 中划线
		viewHolder.setText(R.id.item_card_cutdow_content,contents.pContent);
		return viewHolder.getConvertView();
	}

}
