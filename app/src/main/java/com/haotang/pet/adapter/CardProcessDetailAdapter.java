package com.haotang.pet.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Paint;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haotang.pet.R;
import com.haotang.pet.entity.CardsBuyMyPet;
import com.haotang.pet.util.Utils;
import com.haotang.pet.view.CommonAdapter;
import com.haotang.pet.view.MListview;
import com.haotang.pet.view.ViewHolder;

public class CardProcessDetailAdapter<T> extends CommonAdapter<T>{

	public CardProcessDetailAdapter(Activity mContext, List<T> mDatas) {
		super(mContext, mDatas);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("rawtypes")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		CardsBuyMyPet cardsBuyMyPet = (CardsBuyMyPet) mDatas.get(position);
		ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent,R.layout.item_card_process_detail, position);
		viewHolder.setText(R.id.item_name, cardsBuyMyPet.title);
		viewHolder.setText(R.id.item_price_content, cardsBuyMyPet.priceContent);
		viewHolder.setText(R.id.old_price, "¥"+cardsBuyMyPet.listPrice);
		TextView old_price = viewHolder.getView(R.id.old_price);
		TextPaint paint = old_price.getPaint();
		paint.setAntiAlias(true);
		paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 中划线
		viewHolder.setText(R.id.item_old_price_cutdown, "¥"+Utils.formatDouble(cardsBuyMyPet.price));
		MListview mListview = viewHolder.getView(R.id.mListview_process_detail);
		CardEveryDetailAdapter cardEveryDetailAdapter = new CardEveryDetailAdapter<CardsBuyMyPet.Content>(mContext, cardsBuyMyPet.contentList);
		mListview.setAdapter(cardEveryDetailAdapter);
		mListview.setFocusable(false);
		Utils.setListHeight(mListview);
		return viewHolder.getConvertView();
	}

}
