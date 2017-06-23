package com.haotang.pet;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.haotang.base.SuperActivity;
import com.haotang.pet.adapter.CardEveryDetailAdapter;
import com.haotang.pet.adapter.CardProcessDetailAdapter;
import com.haotang.pet.adapter.CardsProcessMyPetAdapter;
import com.haotang.pet.adapter.JoinWorkAdapter;
import com.haotang.pet.entity.CardsBuyMyPet;
import com.haotang.pet.entity.MyPet;
import com.haotang.pet.net.AsyncHttpResponseHandler;
import com.haotang.pet.util.CommUtil;
import com.haotang.pet.util.GlideUtil;
import com.haotang.pet.util.Global;
import com.haotang.pet.util.ImageLoaderUtil;
import com.haotang.pet.util.ToastUtil;
import com.haotang.pet.util.Utils;
import com.haotang.pet.view.MListview;
/**
 * 新版次卡
 * @author Administrator
 *
 */
public class CardsDetailNewActivity extends SuperActivity implements OnClickListener{

	public static CardsDetailNewActivity cardsDetailNewActivity;
	private ImageButton ib_titlebar_back;
	private TextView tv_titlebar_title;
	private RelativeLayout layout_top_set_back;
	private ImageView imageview_show_back_img;
	private MListview mListview_my_pet;
	private TextView textview_is_not_support;
	private MListview mListview_card_list;
	private MListview cards_scaling_image;
	private int id;
	private String strp;
	private int tid;
	private int petId;
	private int packageId;
	private String petName;
	private String nickName;
	private int CustomerPetId;
	private ArrayList<String> Imagelist = new ArrayList<String>();
	private ArrayList<CardsBuyMyPet> ListOutEverys = new ArrayList<CardsBuyMyPet>();
	private ArrayList<MyPet> myPets = new ArrayList<MyPet>();
	private CardsProcessMyPetAdapter myPetAdapter;//我的宠物
	private CardProcessDetailAdapter cardProcessDetailAdapter;//item
	private CardEveryDetailAdapter cardEveryDetailAdapter;//item内部描述
	private JoinWorkAdapter joinWorkAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardsdetail_new);
		cardsDetailNewActivity = this;
		MApplication.listAppoint.add(cardsDetailNewActivity);
		getIntentData();
		findView();
		setData();
		setView();
		getData();
	}
	private void setView() {
		// TODO Auto-generated method stub
		tv_titlebar_title.setText("服务卡介绍");
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.getDisplayMetrics((Activity)mContext)[0]*255/716);
		params.leftMargin=10;
		params.rightMargin=10;
		params.topMargin=10;
		layout_top_set_back.setLayoutParams(params);
	}
	private void setData() {
		myPetAdapter = new CardsProcessMyPetAdapter<MyPet>(mContext, myPets);
		mListview_my_pet.setAdapter(myPetAdapter);
		
		
		cardProcessDetailAdapter = new CardProcessDetailAdapter<CardsBuyMyPet>(mContext, ListOutEverys);
		mListview_card_list.setAdapter(cardProcessDetailAdapter);
		
		joinWorkAdapter = new JoinWorkAdapter<String>(this,Imagelist);
		cards_scaling_image.setAdapter(joinWorkAdapter);
		cards_scaling_image.setFocusable(false);
		
		
		mListview_my_pet.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MyPet myPet = (MyPet) parent.getItemAtPosition(position);
				if (myPet.selected==1) {
//					myPet.selected=0;
				}else if (myPet.selected==0) {
					for (int i = 0; i < myPets.size(); i++) {
						myPets.get(i).selected=0;
					}
					myPet.selected=1;
				}
				myPets.set(position, myPet);
				myPetAdapter.notifyDataSetChanged();
				strp = getMyPetId_ServiceType(myPets);
				getData();
			}
		});
		mListview_card_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CardsBuyMyPet cardsBuyMyPet = (CardsBuyMyPet) parent.getItemAtPosition(position);
				goNext(OrderPayActivity.class, cardsBuyMyPet,Global.CARDSDETAIL_NEW_TO_BUT_CARD);
			}
		});
	}
	private void getIntentData() {
		id = getIntent().getIntExtra("id", 0);
//		strp = getIntent().getStringExtra("strp");
		tid = getIntent().getIntExtra("tid", 0);
		myPets.clear();
		myPets = getIntent().getParcelableArrayListExtra("myPets");
		for (int i = 0; i < myPets.size(); i++) {
			myPets.get(i).selected=0;
		}
		myPets.get(0).selected=1;
		strp = myPets.get(0).myPetId+"_"+myPets.get(0).serviceType;
//		if (Utils.isLog) {//测试数据
//			id = 4;//次卡模板id
//			strp="562_1";//进来默认选中第一条宠物根据宠物信息匹配出strp
//			tid = 2;//美容师等级
//		}
	}
	private void getData() {
		mPDialog.showDialog();
		CommUtil.cardDetailForAppointment(mContext, id, strp, tid, handler);
	}
	private void findView() {
		ib_titlebar_back = (ImageButton) findViewById(R.id.ib_titlebar_back);
		tv_titlebar_title = (TextView) findViewById(R.id.tv_titlebar_title);
		layout_top_set_back = (RelativeLayout) findViewById(R.id.layout_top_set_back);
		imageview_show_back_img = (ImageView) findViewById(R.id.imageview_show_back_img);
		mListview_my_pet = (MListview) findViewById(R.id.mListview_my_pet);
		textview_is_not_support = (TextView) findViewById(R.id.textview_is_not_support);
		mListview_card_list = (MListview) findViewById(R.id.mListview_card_list);
		cards_scaling_image = (MListview) findViewById(R.id.cards_scaling_image);
		
		ib_titlebar_back.setOnClickListener(this);
	}
	private AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
		
		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
			try {
				mPDialog.closeDialog();
				JSONObject jsonObject = new JSONObject(new String(responseBody));
				int code = jsonObject.getInt("code");
				if (code==0) {
					if (jsonObject.has("data")&&!jsonObject.isNull("data")) {
						JSONObject objectData = jsonObject.getJSONObject("data");
						if (objectData.has("card")&&!objectData.isNull("card")) {
							JSONObject objectCard = objectData.getJSONObject("card");
							Imagelist.clear();
							if (objectCard.has("cardDetailImg")&&!objectCard.isNull("cardDetailImg")) {
								JSONArray arrayCardImgs = objectCard.getJSONArray("cardDetailImg");
								if (arrayCardImgs.length()>0) {
									for (int i = 0; i < arrayCardImgs.length(); i++) {
										if (arrayCardImgs.getString(i).startsWith("http")) {
											Imagelist.add(arrayCardImgs.getString(i));
										}else {
											Imagelist.add(CommUtil.getServiceNobacklash()+arrayCardImgs.getString(i));
										}
									}
								}
							}
							if (Imagelist.size()>0) {
								joinWorkAdapter.notifyDataSetChanged();
							}
							
							if (objectCard.has("petId")&&!objectCard.isNull("petId")) {
								String petIds = objectCard.getString("petId");
							}
//							if (objectCard.has("cardBgImg")&&!objectCard.isNull("cardBgImg")) {
//								String cardBgImg = objectCard.getString("cardBgImg");
//							}
							if (objectCard.has("cardImg")&&!objectCard.isNull("cardImg")) {
								String cardImg = objectCard.getString("cardImg");
								if (cardImg.contains("http")) {
									GlideUtil.loadImg(CardsDetailNewActivity.this,cardImg,imageview_show_back_img,  0);
								}else {
									GlideUtil.loadImg(CardsDetailNewActivity.this, CommUtil.getServiceBaseUrl()+cardImg,imageview_show_back_img, 0);
								}
							}
							if (objectCard.has("myPets")&&!objectCard.isNull("myPets")) {
								JSONArray array = objectCard.getJSONArray("myPets");
								if (array.length()>0) {
									for (int i = 0; i < array.length(); i++) {
										JSONObject objectMyPets = array.getJSONObject(i);
										if (objectMyPets.has("petId")&&!objectMyPets.isNull("petId")) {
											petId = objectMyPets.getInt("petId");
										}
										if (objectMyPets.has("petName")&&!objectMyPets.isNull("petName")) {
											petName = objectMyPets.getString("petName");
										}
										if (objectMyPets.has("nickName")&&!objectMyPets.isNull("nickName")) {
											nickName = objectMyPets.getString("nickName");
										}
										if (objectMyPets.has("CustomerPetId")&&!objectMyPets.isNull("CustomerPetId")) {
											CustomerPetId = objectMyPets.getInt("CustomerPetId");
										}
										ListOutEverys.clear();
										if (objectMyPets.has("packages")&&!objectMyPets.isNull("packages")) {
											JSONArray arrayPackage = objectMyPets.getJSONArray("packages");
											if (arrayPackage.length()>0) {
												for (int j = 0; j < arrayPackage.length(); j++) {
													CardsBuyMyPet buyMyPet = new CardsBuyMyPet();
													JSONObject objectPackage = arrayPackage.getJSONObject(j);
													buyMyPet.petId = petId;
													buyMyPet.petName = petName;
													buyMyPet.nickName = nickName;
													buyMyPet.CustomerPetId = CustomerPetId;
													if (objectPackage.has("price")&&!objectPackage.isNull("price")) {
														int price = objectPackage.getInt("price");
														buyMyPet.price = price;
													}
													if (objectPackage.has("listPrice")&&!objectPackage.isNull("listPrice")) {
														int listPrice = objectPackage.getInt("listPrice");
														buyMyPet.listPrice = listPrice;
													}
													if (objectPackage.has("priceContent")&&!objectPackage.isNull("priceContent")) {
														buyMyPet.priceContent = objectPackage.getString("priceContent");
													}
													if (objectPackage.has("discount")&&!objectPackage.isNull("discount")) {
														buyMyPet.discount = objectPackage.getString("discount");
													}
													if (objectPackage.has("title")&&!objectPackage.isNull("title")) {
														buyMyPet.title = objectPackage.getString("title");
													}
													if (objectPackage.has("content")&&!objectPackage.isNull("content")) {
														JSONArray arrayContent = objectPackage.getJSONArray("content");
														if (arrayContent.length()>0) {
															for (int k = 0; k < arrayContent.length(); k++) {
																CardsBuyMyPet.Content content = new CardsBuyMyPet().new Content();
																JSONObject objectContents = arrayContent.getJSONObject(k);
																if (objectContents.has("package")&&!objectContents.isNull("package")) {
																	content.packageId = objectContents.getInt("package");
																}
																if (objectContents.has("tpri")&&!objectContents.isNull("tpri")) {
																	content.tpri=objectContents.getInt("tpri");
																}
																if (objectContents.has("tlpri")&&!objectContents.isNull("tlpri")) {
																	content.tlpri=objectContents.getInt("tlpri");
																}
																if (objectContents.has("pri")&&!objectContents.isNull("pri")) {
																	content.pri=objectContents.getInt("pri");
																}
																if (objectContents.has("lpContent")&&!objectContents.isNull("lpContent")) {
																	content.lpContent=objectContents.getString("lpContent");
																}
																if (objectContents.has("count")&&!objectContents.isNull("count")) {
																	content.count=objectContents.getInt("count");
																}
																if (objectContents.has("lpri")&&!objectContents.isNull("lpri")) {
																	content.lpri=objectContents.getInt("lpri");
																}
																if (objectContents.has("packageTip")&&!objectContents.isNull("packageTip")) {
																	content.packageTip=objectContents.getString("packageTip");
																}
																if (objectContents.has("pContent")&&!objectContents.isNull("pContent")) {
																	content.pContent=objectContents.getString("pContent");
																}
																buyMyPet.contentList.add(content);
															}
														}
													}
													ListOutEverys.add(buyMyPet);
												}
											}
										}
									}
								}
								if (ListOutEverys.size()>0) {
									textview_is_not_support.setVisibility(View.GONE);
									mListview_card_list.setVisibility(View.VISIBLE);
									cardProcessDetailAdapter.notifyDataSetChanged();
									Utils.setListHeight(mListview_card_list);
								}else {
									textview_is_not_support.setVisibility(View.VISIBLE);
									mListview_card_list.setVisibility(View.GONE);
								}
							}
						}
					}
				}else {
					ToastUtil.showToastShortCenter(mContext, jsonObject.getString("msg"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ToastUtil.showToastLong(mContext, e.getMessage()+" "+e.getCause());
			}
		}
		
		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			// TODO Auto-generated method stub
			mPDialog.closeDialog();
		}
	};
	private String getMyPetId_ServiceType(ArrayList<MyPet> list){
		String strp=null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).selected==1) {
				strp = list.get(i).myPetId+"_"+list.get(i).serviceType;
			}
		}
		if (!TextUtils.isEmpty(strp)) {
			return strp;
		}else {
			return null;
		}
	}
	private void goNext(Class cls,CardsBuyMyPet cardsBuyMyPet,int previous){
		Intent intent = new Intent(mContext, cls);
		intent.putExtra("cardsBuyMyPet", cardsBuyMyPet);
		intent.putExtra("petid", petId);
		intent.putExtra("id", id);
		intent.putExtra("previous", previous);
		startActivity(intent);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ib_titlebar_back:
			finish();
			break;

		default:
			break;
		}
	}
}
