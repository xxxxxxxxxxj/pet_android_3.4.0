package com.haotang.pet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.haotang.base.SuperActivity;
import com.haotang.pet.adapter.ServiceAddPetAdapter;
import com.haotang.pet.entity.AddServiceItem;
import com.haotang.pet.entity.AppointMentNewPetInfo;
import com.haotang.pet.entity.ExtraItem;
import com.haotang.pet.net.AsyncHttpResponseHandler;
import com.haotang.pet.util.CommUtil;
import com.haotang.pet.util.Global;
import com.haotang.pet.util.ToastUtil;
import com.haotang.pet.util.Utils;
import com.haotang.pet.view.MyGridView;

/**
 * <p>
 * Title:ChangeServiceActivity
 * </p>
 * <p>
 * Description:切换服务界面
 * </p>
 * <p>
 * Company:北京昊唐科技有限公司
 * </p>
 * 
 * @author 徐俊
 * @date 2017-6-12 下午2:16:11
 */
public class ChangeServiceActivity extends SuperActivity implements
		OnClickListener {
	private int petId;
	private int shopId;
	private int servLoc;
	private String appTime;
	private double lat;
	private double lng;
	private int petKind;
	private int servicetype;
	private List<ExtraItem> extraItems;
	private AppointMentNewPetInfo appointMentNewPetInfo;
	private ImageButton ibBack;
	private TextView tvTitle;
	private RelativeLayout rlBath;
	private TextView tvBathPrice;
	private ImageView ivBath;
	private RelativeLayout rlBathInfo;
	private RelativeLayout rlBeauty;
	private TextView tvBeautyPrice;
	private ImageView ivBeauty;
	private RelativeLayout rlBeautyInfo;
	private Button btSubmit;
	private TextView tvMsg1;
	private Button btRefresh;
	private RelativeLayout rlNull;
	private ScrollView prsMain;
	private MyGridView gvBathAddService;
	private MyGridView gvBeautyAddService;
	private TextView tvBathTotalFee;
	private TextView tvBeautyTotalFee;
	private LinearLayout llBath;
	private LinearLayout llBeauty;
	private ServiceAddPetAdapter bathAddServiceAdapter;
	private ServiceAddPetAdapter beautyAddServiceAdapter;
	private ArrayList<AddServiceItem> serviceBathItemsList = new ArrayList<AddServiceItem>();
	private ArrayList<AddServiceItem> serviceBeautyItemsList = new ArrayList<AddServiceItem>();
	private int level;
	private int serviceid;
	private boolean isBathSelected;
	private boolean isBeautySelected;
	private double addserviceprice;
	private double bathtotalfee;
	private double beautytotalfee;
	private double basefee;
	private double bathPriceLevel1;
	private double bathPriceLevel2;
	private double bathPriceLevel3;
	private double beautyPriceLevel1;
	private double beautyPriceLevel2;
	private double beautyPriceLevel3;
	private double basefeewithbeautician;
	private String servicename;
	private String bathname;
	private String beautyname;
	private boolean isBathAvailable;
	private boolean isBeautyAvailable;
	private int bathserviceid;
	private int beautyserviceid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
		initView();
		setView();
		setLinster();
		getData();
	}

	private void initData() {
		Intent intent = getIntent();
		appointMentNewPetInfo = (AppointMentNewPetInfo) intent
				.getSerializableExtra("appointMentNewPetInfo");
		if (appointMentNewPetInfo != null) {
			petId = appointMentNewPetInfo.getPetId();
			shopId = appointMentNewPetInfo.getShopId();
			servLoc = appointMentNewPetInfo.getServiceLoc();
			appTime = intent.getStringExtra("time");
			lat = intent.getDoubleExtra("lat", 0);
			lng = intent.getDoubleExtra("lng", 0);
			petKind = appointMentNewPetInfo.getPetKind();
			servicetype = appointMentNewPetInfo.getServiceType();
			extraItems = appointMentNewPetInfo.getExtraItems();
			level = appointMentNewPetInfo.getLevel();
		}
	}

	private void initView() {
		setContentView(R.layout.activity_change_service);
		ibBack = (ImageButton) findViewById(R.id.ib_titlebar_back);
		tvTitle = (TextView) findViewById(R.id.tv_titlebar_title);
		rlBath = (RelativeLayout) findViewById(R.id.rl_mulpetservice_bath);
		tvBathPrice = (TextView) findViewById(R.id.tv_mulpetservice_bathprice);
		ivBath = (ImageView) findViewById(R.id.iv_mulpetservice_bath);
		rlBathInfo = (RelativeLayout) findViewById(R.id.rl_mulpetservice_bathinfo);
		rlBeauty = (RelativeLayout) findViewById(R.id.rl_mulpetservice_beauty);
		tvBeautyPrice = (TextView) findViewById(R.id.tv_mulpetservice_beautyprice);
		ivBeauty = (ImageView) findViewById(R.id.iv_mulpetservice_beauty);
		rlBeautyInfo = (RelativeLayout) findViewById(R.id.rl_mulpetservice_beautyinfo);
		btSubmit = (Button) findViewById(R.id.bt_mulpetservice_submit);
		tvMsg1 = (TextView) findViewById(R.id.tv_null_msg1);
		btRefresh = (Button) findViewById(R.id.bt_null_refresh);
		rlNull = (RelativeLayout) findViewById(R.id.rl_null);
		prsMain = (ScrollView) findViewById(R.id.prs_mulpetservice_scrollview);
		gvBathAddService = (MyGridView) findViewById(R.id.gv_mulpetservice_bath_addservice);
		tvBathTotalFee = (TextView) findViewById(R.id.tv_mulpetservice_total_bathprice);
		gvBeautyAddService = (MyGridView) findViewById(R.id.gv_mulpetservice_beauty_addservice);
		tvBeautyTotalFee = (TextView) findViewById(R.id.tv_mulpetservice_total_beautyprice);
		llBath = (LinearLayout) findViewById(R.id.ll_mulpetservice_bath);
		llBeauty = (LinearLayout) findViewById(R.id.ll_mulpetservice_beauty);
	}

	private void setView() {
		tvTitle.setText("选择服务项目");
		gvBathAddService.setSelector(new ColorDrawable(Color.TRANSPARENT));
		bathAddServiceAdapter = new ServiceAddPetAdapter(this,
				serviceBathItemsList);
		beautyAddServiceAdapter = new ServiceAddPetAdapter(this,
				serviceBeautyItemsList);
		gvBathAddService.setAdapter(bathAddServiceAdapter);
		gvBeautyAddService.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gvBeautyAddService.setAdapter(beautyAddServiceAdapter);
	}

	private int isItemSelected(AddServiceItem item) {
		for (int i = 0; i < extraItems.size(); i++) {
			if (item.id == extraItems.get(i).getItemId()) {
				return i;
			}
		}
		return -1;
	}

	private void setLinster() {
		ibBack.setOnClickListener(this);
		rlBath.setOnClickListener(this);
		rlBeauty.setOnClickListener(this);
		btRefresh.setOnClickListener(this);
		btSubmit.setOnClickListener(this);
		gvBathAddService.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (isBathSelected) {
					isBathSelected = true;
					isBeautySelected = false;
					servicetype = 1;
					serviceid = bathserviceid;
					if (serviceBathItemsList.get(position).isChecked) {
						serviceBathItemsList.get(position).isChecked = false;
						addserviceprice -= serviceBathItemsList.get(position).price;
						if (extraItems != null && extraItems.size() > 0) {
							int selectedIndex = isItemSelected(serviceBathItemsList
									.get(position));
							if (selectedIndex >= 0) {
								extraItems.remove(selectedIndex);
							}
						}
						appointMentNewPetInfo.setExtraItems(extraItems);
						bathtotalfee -= serviceBathItemsList.get(position).price;
					} else {
						serviceBathItemsList.get(position).isChecked = true;
						addserviceprice += serviceBathItemsList.get(position).price;
						if (extraItems == null) {
							extraItems = new ArrayList<ExtraItem>();
						}
						extraItems.add(new ExtraItem("", serviceBathItemsList
								.get(position).tip, serviceBathItemsList
								.get(position).id, serviceBathItemsList
								.get(position).name, serviceBathItemsList
								.get(position).listPrice, serviceBathItemsList
								.get(position).price));
						appointMentNewPetInfo.setExtraItems(extraItems);
						bathtotalfee += serviceBathItemsList.get(position).price;
					}
				} else if (isBeautySelected) {
					isBathSelected = true;
					isBeautySelected = false;
					servicetype = 1;
					serviceid = bathserviceid;
					ivBath.setBackgroundResource(R.drawable.icon_pay_selected);
					ivBeauty.setBackgroundResource(R.drawable.icon_pay_normal);
					addserviceprice = 0;
					bathtotalfee = 0;
					beautytotalfee = 0;
					if (level == 1) {
						basefee = bathPriceLevel1;
						basefeewithbeautician = bathPriceLevel1;
					} else if (level == 2) {
						basefee = bathPriceLevel2;
						basefeewithbeautician = bathPriceLevel2;
					} else {
						basefee = bathPriceLevel3;
						basefeewithbeautician = bathPriceLevel3;
					}
					bathtotalfee = basefeewithbeautician;
					serviceBathItemsList.get(position).isChecked = true;
					addserviceprice += serviceBathItemsList.get(position).price;
					if (extraItems == null) {
						extraItems = new ArrayList<ExtraItem>();
					}
					extraItems.clear();
					extraItems.add(new ExtraItem("", serviceBathItemsList
							.get(position).tip, serviceBathItemsList
							.get(position).id, serviceBathItemsList
							.get(position).name, serviceBathItemsList
							.get(position).listPrice, serviceBathItemsList
							.get(position).price));
					appointMentNewPetInfo.setExtraItems(extraItems);
					bathtotalfee += serviceBathItemsList.get(position).price;
					for (int i = 0; i < serviceBeautyItemsList.size(); i++)
						serviceBeautyItemsList.get(i).isChecked = false;
					beautyAddServiceAdapter.notifyDataSetChanged();
				}

				appointMentNewPetInfo.setServiceType(1);
				appointMentNewPetInfo.setUseCard(false);
				if (appointMentNewPetInfo.getPetKind() == 1) {// 狗
					if (appointMentNewPetInfo.getServiceType() == 1) {// 狗洗澡
						appointMentNewPetInfo.setServiceId(1);
					} else if (appointMentNewPetInfo.getServiceType() == 2) {// 狗美容
						appointMentNewPetInfo.setServiceId(2);
					}
				} else if (appointMentNewPetInfo.getPetKind() == 2) {// 猫
					if (appointMentNewPetInfo.getServiceType() == 1) {// 猫洗澡
						appointMentNewPetInfo.setServiceId(3);
					} else if (appointMentNewPetInfo.getServiceType() == 2) {// 猫美容
						appointMentNewPetInfo.setServiceId(4);
					}
				}

				bathAddServiceAdapter.notifyDataSetChanged();
				String text = "总价：¥" + Utils.formatDouble(bathtotalfee);
				SpannableString ss = new SpannableString(text);
				ss.setSpan(new ForegroundColorSpan(mContext.getResources()
						.getColor(R.color.black)), 0, 3,
						Spanned.SPAN_INCLUSIVE_INCLUSIVE);
				ss.setSpan(new TextAppearanceSpan(mContext, R.style.style3), 0,
						4, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
				tvBathTotalFee.setText(ss);
				tvBathTotalFee.setVisibility(View.VISIBLE);
				tvBeautyTotalFee.setVisibility(View.GONE);
			}
		});
		gvBeautyAddService.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (isBeautySelected) {
					isBathSelected = false;
					isBeautySelected = true;
					servicetype = 2;
					serviceid = beautyserviceid;
					if (serviceBeautyItemsList.get(position).isChecked) {
						serviceBeautyItemsList.get(position).isChecked = false;
						addserviceprice -= serviceBeautyItemsList.get(position).price;
						if (extraItems != null && extraItems.size() > 0) {
							int selectedIndex = isItemSelected(serviceBeautyItemsList
									.get(position));
							if (selectedIndex >= 0) {
								extraItems.remove(selectedIndex);
							}
						}
						appointMentNewPetInfo.setExtraItems(extraItems);
						beautytotalfee -= serviceBeautyItemsList.get(position).price;
					} else {
						serviceBeautyItemsList.get(position).isChecked = true;
						addserviceprice += serviceBeautyItemsList.get(position).price;
						if (extraItems == null) {
							extraItems = new ArrayList<ExtraItem>();
						}
						extraItems.add(new ExtraItem("", serviceBeautyItemsList
								.get(position).tip, serviceBeautyItemsList
								.get(position).id, serviceBeautyItemsList
								.get(position).name, serviceBeautyItemsList
								.get(position).listPrice,
								serviceBeautyItemsList.get(position).price));
						appointMentNewPetInfo.setExtraItems(extraItems);
						beautytotalfee += serviceBeautyItemsList.get(position).price;
					}
				} else if (isBathSelected) {
					isBathSelected = false;
					isBeautySelected = true;
					servicetype = 2;
					serviceid = beautyserviceid;
					ivBeauty.setBackgroundResource(R.drawable.icon_pay_selected);
					ivBath.setBackgroundResource(R.drawable.icon_pay_normal);
					addserviceprice = 0;
					bathtotalfee = 0;
					beautytotalfee = 0;
					if (level == 1) {
						basefee = beautyPriceLevel1;
						basefeewithbeautician = beautyPriceLevel1;
					} else if (level == 2) {
						basefee = beautyPriceLevel2;
						basefeewithbeautician = beautyPriceLevel2;
					} else {
						basefee = beautyPriceLevel3;
						basefeewithbeautician = beautyPriceLevel3;
					}
					beautytotalfee = basefeewithbeautician;
					serviceBeautyItemsList.get(position).isChecked = true;
					addserviceprice += serviceBeautyItemsList.get(position).price;
					if (extraItems == null) {
						extraItems = new ArrayList<ExtraItem>();
					}
					extraItems.clear();
					extraItems.add(new ExtraItem("", serviceBeautyItemsList
							.get(position).tip, serviceBeautyItemsList
							.get(position).id, serviceBeautyItemsList
							.get(position).name, serviceBeautyItemsList
							.get(position).listPrice, serviceBeautyItemsList
							.get(position).price));
					appointMentNewPetInfo.setExtraItems(extraItems);
					beautytotalfee += serviceBeautyItemsList.get(position).price;
					for (int i = 0; i < serviceBathItemsList.size(); i++)
						serviceBathItemsList.get(i).isChecked = false;
					bathAddServiceAdapter.notifyDataSetChanged();
				}
				appointMentNewPetInfo.setServiceType(2);
				appointMentNewPetInfo.setUseCard(false);
				if (appointMentNewPetInfo.getPetKind() == 1) {// 狗
					if (appointMentNewPetInfo.getServiceType() == 1) {// 狗洗澡
						appointMentNewPetInfo.setServiceId(1);
					} else if (appointMentNewPetInfo.getServiceType() == 2) {// 狗美容
						appointMentNewPetInfo.setServiceId(2);
					}
				} else if (appointMentNewPetInfo.getPetKind() == 2) {// 猫
					if (appointMentNewPetInfo.getServiceType() == 1) {// 猫洗澡
						appointMentNewPetInfo.setServiceId(3);
					} else if (appointMentNewPetInfo.getServiceType() == 2) {// 猫美容
						appointMentNewPetInfo.setServiceId(4);
					}
				}
				beautyAddServiceAdapter.notifyDataSetChanged();
				String text = "总价：¥" + Utils.formatDouble(beautytotalfee);
				SpannableString ss = new SpannableString(text);
				ss.setSpan(new ForegroundColorSpan(mContext.getResources()
						.getColor(R.color.black)), 0, 3,
						Spanned.SPAN_INCLUSIVE_INCLUSIVE);
				ss.setSpan(new TextAppearanceSpan(mContext, R.style.style3), 0,
						4, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
				tvBeautyTotalFee.setText(ss);
				tvBathTotalFee.setVisibility(View.GONE);
				tvBeautyTotalFee.setVisibility(View.VISIBLE);
			}
		});
	}

	private void getData() {
		mPDialog.showDialog();
		CommUtil.getPetServiceByAddPet(this, petId, shopId, servLoc, appTime,
				lat, lng, dataHanler);
	}

	private AsyncHttpResponseHandler dataHanler = new AsyncHttpResponseHandler() {

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] responseBody) {
			mPDialog.closeDialog();
			try {
				JSONObject jobj = new JSONObject(new String(responseBody));
				int code = jobj.getInt("code");
				String msg = jobj.getString("msg");
				if (code == 0) {
					if (jobj.has("data") && !jobj.isNull("data")) {
						JSONArray jarr = jobj.getJSONArray("data");
						if (jarr.length() > 0) {
							for (int i = 0; i < jarr.length(); i++) {
								JSONObject jo = jarr.getJSONObject(i);
								if (jo.has("petServicePojo")
										&& !jo.isNull("petServicePojo")) {
									JSONObject jps = jo
											.getJSONObject("petServicePojo");
									if (jps.has("serviceType")
											&& !jps.isNull("serviceType")) {
										int st = jps.getInt("serviceType");
										if (st == 1) {
											// 洗澡
											isBathAvailable = true;
											if (jps.has("name")
													&& !jps.isNull("name")) {
												bathname = jps
														.getString("name");
											}
											if (jo.has("serviceId")
													&& !jo.isNull("serviceId")) {
												bathserviceid = jo
														.getInt("serviceId");
											}
											if (servLoc == 1) {
												// 到店
												if (jo.has("shopPrice10")
														&& !jo.isNull("shopPrice10")) {
													bathPriceLevel1 = jo
															.getDouble("shopPrice10");
												}
												if (jo.has("shopPrice20")
														&& !jo.isNull("shopPrice20")) {
													bathPriceLevel2 = jo
															.getDouble("shopPrice20");
												}
												if (jo.has("shopPrice30")
														&& !jo.isNull("shopPrice30")) {
													bathPriceLevel3 = jo
															.getDouble("shopPrice30");
												}
											} else {
												if (jo.has("price10")
														&& !jo.isNull("price10")) {
													bathPriceLevel1 = jo
															.getDouble("price10");
												}
												if (jo.has("price20")
														&& !jo.isNull("price20")) {
													bathPriceLevel2 = jo
															.getDouble("price20");
												}
												if (jo.has("price30")
														&& !jo.isNull("price30")) {
													bathPriceLevel3 = jo
															.getDouble("price30");
												}
											}
											if (jps.has("extraServiceItems")
													&& !jps.isNull("extraServiceItems")
													&& jps.getJSONArray(
															"extraServiceItems")
															.length() > 0) {
												JSONArray je = jps
														.getJSONArray("extraServiceItems");
												showServiceItems(true, 1);
												for (int j = 0; j < je.length(); j++) {
													serviceBathItemsList
															.add(AddServiceItem
																	.json2Entity(je
																			.getJSONObject(j)));
												}
												bathAddServiceAdapter
														.notifyDataSetChanged();
											} else {
												showServiceItems(false, 1);
											}
											if (level == 1) {
												basefeewithbeautician = bathPriceLevel1;
											} else if (level == 2) {
												basefeewithbeautician = bathPriceLevel2;
											} else {
												basefeewithbeautician = bathPriceLevel3;
											}
											bathtotalfee = basefeewithbeautician;
											tvBathPrice
													.setText("¥"
															+ Utils.formatDouble(basefeewithbeautician));
											String text = "总价：¥"
													+ Utils.formatDouble(basefeewithbeautician);
											SpannableString ss = new SpannableString(
													text);
											ss.setSpan(
													new ForegroundColorSpan(
															mContext.getResources()
																	.getColor(
																			R.color.black)),
													0,
													3,
													Spanned.SPAN_INCLUSIVE_INCLUSIVE);
											ss.setSpan(
													new TextAppearanceSpan(
															mContext,
															R.style.style3),
													0,
													4,
													Spanned.SPAN_INCLUSIVE_INCLUSIVE);
											tvBathTotalFee.setText(ss);
										} else if (st == 2) {
											// 美容
											isBeautyAvailable = true;
											if (jps.has("name")
													&& !jps.isNull("name")) {
												beautyname = jps
														.getString("name");
											}
											if (jo.has("serviceId")
													&& !jo.isNull("serviceId")) {
												beautyserviceid = jo
														.getInt("serviceId");
											}
											if (servLoc == 1) {
												// 到店
												if (jo.has("shopPrice10")
														&& !jo.isNull("shopPrice10")) {
													beautyPriceLevel1 = jo
															.getDouble("shopPrice10");
												}
												if (jo.has("shopPrice20")
														&& !jo.isNull("shopPrice20")) {
													beautyPriceLevel2 = jo
															.getDouble("shopPrice20");
												}
												if (jo.has("shopPrice30")
														&& !jo.isNull("shopPrice30")) {
													beautyPriceLevel3 = jo
															.getDouble("shopPrice30");
												}
											} else {
												if (jo.has("price10")
														&& !jo.isNull("price10")) {
													beautyPriceLevel1 = jo
															.getDouble("price10");
												}
												if (jo.has("price20")
														&& !jo.isNull("price20")) {
													beautyPriceLevel2 = jo
															.getDouble("price20");
												}
												if (jo.has("price30")
														&& !jo.isNull("price30")) {
													beautyPriceLevel3 = jo
															.getDouble("price30");
												}
											}
											if (jps.has("extraServiceItems")
													&& !jps.isNull("extraServiceItems")
													&& jps.getJSONArray(
															"extraServiceItems")
															.length() > 0) {
												JSONArray je = jps
														.getJSONArray("extraServiceItems");
												showServiceItems(true, 2);
												for (int j = 0; j < je.length(); j++) {
													serviceBeautyItemsList
															.add(AddServiceItem
																	.json2Entity(je
																			.getJSONObject(j)));
												}
												beautyAddServiceAdapter
														.notifyDataSetChanged();
											} else {
												showServiceItems(false, 2);
											}
											if (level == 1) {
												basefeewithbeautician = beautyPriceLevel1;
											} else if (level == 2) {
												basefeewithbeautician = beautyPriceLevel2;
											} else {
												basefeewithbeautician = beautyPriceLevel3;
											}
											beautytotalfee = basefeewithbeautician;
											tvBeautyPrice
													.setText("¥"
															+ Utils.formatDouble(basefeewithbeautician));
											String text = "总价：¥"
													+ Utils.formatDouble(basefeewithbeautician);
											SpannableString ss = new SpannableString(
													text);
											ss.setSpan(
													new ForegroundColorSpan(
															mContext.getResources()
																	.getColor(
																			R.color.black)),
													0,
													3,
													Spanned.SPAN_INCLUSIVE_INCLUSIVE);
											ss.setSpan(
													new TextAppearanceSpan(
															mContext,
															R.style.style3),
													0,
													4,
													Spanned.SPAN_INCLUSIVE_INCLUSIVE);
											tvBeautyTotalFee.setText(ss);
										}
									}
								}
							}
							if (servicetype == 1 && isBathAvailable
									|| (isBathAvailable && !isBeautyAvailable)) {
								isBathSelected = true;
								isBeautySelected = false;
								ivBath.setBackgroundResource(R.drawable.icon_pay_selected);
								ivBeauty.setBackgroundResource(R.drawable.icon_pay_normal);
								servicetype = 1;
								serviceid = bathserviceid;
								if (level == 1) {
									basefee = bathPriceLevel1;
									basefeewithbeautician = bathPriceLevel1;
								} else if (level == 2) {
									basefee = bathPriceLevel2;
									basefeewithbeautician = bathPriceLevel2;
								} else {
									basefee = bathPriceLevel3;
									basefeewithbeautician = bathPriceLevel3;
								}
								bathtotalfee = basefeewithbeautician;
								tvBathPrice
										.setText("¥"
												+ Utils.formatDouble(basefeewithbeautician));
								servicename = bathname;
								if (extraItems != null && extraItems.size() > 0
										&& serviceBathItemsList.size() > 0) {
									for (int i = 0; i < extraItems.size(); i++) {
										for (int j = 0; j < serviceBathItemsList
												.size(); j++) {
											if (extraItems.get(i).getItemId() == serviceBathItemsList
													.get(j).id) {
												serviceBathItemsList.get(j).isChecked = true;
												addserviceprice += serviceBathItemsList
														.get(j).price;
												break;
											}
										}
									}
									bathAddServiceAdapter
											.notifyDataSetChanged();
								}
								bathtotalfee = basefeewithbeautician
										+ addserviceprice;
								String text = "总价：¥"
										+ Utils.formatDouble(bathtotalfee);
								SpannableString ss = new SpannableString(text);
								ss.setSpan(
										new ForegroundColorSpan(mContext
												.getResources().getColor(
														R.color.black)), 0, 3,
										Spanned.SPAN_INCLUSIVE_INCLUSIVE);
								ss.setSpan(new TextAppearanceSpan(mContext,
										R.style.style3), 0, 4,
										Spanned.SPAN_INCLUSIVE_INCLUSIVE);
								tvBathTotalFee.setText(ss);
								tvBathTotalFee.setVisibility(View.VISIBLE);
								tvBeautyTotalFee.setVisibility(View.GONE);
							} else if (servicetype == 2 && isBeautyAvailable
									|| (!isBathAvailable && isBeautyAvailable)) {
								isBathSelected = false;
								isBeautySelected = true;
								ivBeauty.setBackgroundResource(R.drawable.icon_pay_selected);
								ivBath.setBackgroundResource(R.drawable.icon_pay_normal);
								servicetype = 2;
								serviceid = beautyserviceid;
								if (level == 1) {
									basefee = bathPriceLevel1;
									basefeewithbeautician = beautyPriceLevel1;
								} else if (level == 2) {
									basefee = bathPriceLevel2;
									basefeewithbeautician = beautyPriceLevel2;
								} else {
									basefee = bathPriceLevel3;
									basefeewithbeautician = beautyPriceLevel3;
								}
								beautytotalfee = basefeewithbeautician;
								servicename = beautyname;
								tvBeautyPrice
										.setText("¥"
												+ Utils.formatDouble(basefeewithbeautician));
								if (extraItems != null && extraItems.size() > 0
										&& serviceBeautyItemsList.size() > 0) {
									for (int i = 0; i < extraItems.size(); i++) {
										for (int j = 0; j < serviceBeautyItemsList
												.size(); j++) {
											if (extraItems.get(i).getItemId() == serviceBeautyItemsList
													.get(j).id) {
												serviceBeautyItemsList.get(j).isChecked = true;
												addserviceprice += serviceBeautyItemsList
														.get(j).price;
												break;
											}
										}
									}
									beautytotalfee = basefeewithbeautician
											+ addserviceprice;
									beautyAddServiceAdapter
											.notifyDataSetChanged();
								}
								String text = "总价：¥"
										+ Utils.formatDouble(beautytotalfee);
								SpannableString ss = new SpannableString(text);
								ss.setSpan(
										new ForegroundColorSpan(mContext
												.getResources().getColor(
														R.color.black)), 0, 3,
										Spanned.SPAN_INCLUSIVE_INCLUSIVE);
								ss.setSpan(new TextAppearanceSpan(mContext,
										R.style.style3), 0, 4,
										Spanned.SPAN_INCLUSIVE_INCLUSIVE);
								tvBeautyTotalFee.setText(ss);
								tvBathTotalFee.setVisibility(View.GONE);
								tvBeautyTotalFee.setVisibility(View.VISIBLE);
							} else if (!isBathAvailable && !isBeautyAvailable) {
								showMain(false);
							}
							if (isBathAvailable) {
								rlBath.setVisibility(View.VISIBLE);
								llBath.setVisibility(View.VISIBLE);
								if (level == 3) {
									if (bathPriceLevel3 > 0) {
										rlBath.setVisibility(View.VISIBLE);
										llBath.setVisibility(View.VISIBLE);
									} else {
										rlBath.setVisibility(View.GONE);
										llBath.setVisibility(View.GONE);
									}
								}
							} else {
								rlBath.setVisibility(View.GONE);
								llBath.setVisibility(View.GONE);
							}
							if (isBeautyAvailable) {
								rlBeauty.setVisibility(View.VISIBLE);
								llBeauty.setVisibility(View.VISIBLE);

							} else {
								rlBeauty.setVisibility(View.GONE);
								llBeauty.setVisibility(View.GONE);

							}
						}
					}
				} else {
					if (Utils.isStrNull(msg)) {
						ToastUtil.showToastShortBottom(
								ChangeServiceActivity.this, msg);
					}
				}
			} catch (Exception e) {
				Log.e("TAG", "getSingles()数据异常e = " + e.toString());
				ToastUtil.showToastShortBottom(ChangeServiceActivity.this,
						"数据异常");
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			ToastUtil.showToastShortBottom(ChangeServiceActivity.this, "请求失败");
			mPDialog.closeDialog();
		}
	};

	/**
	 * 显示单项
	 * 
	 * @param flag
	 *            是否显示
	 * @param type
	 *            显示类型 1：洗澡 2：美容
	 */
	private void showServiceItems(boolean flag, int type) {
		if (type == 1) {
			if (flag) {
				rlBathInfo.setVisibility(View.VISIBLE);
			} else {
				rlBathInfo.setVisibility(View.GONE);
			}
		} else {
			if (flag) {
				rlBeautyInfo.setVisibility(View.VISIBLE);
			} else {
				rlBeautyInfo.setVisibility(View.GONE);
			}
		}
	}

	private void showMain(boolean flag) {
		if (flag) {
			prsMain.setVisibility(View.VISIBLE);
			btSubmit.setVisibility(View.VISIBLE);
			rlNull.setVisibility(View.GONE);
		} else {
			prsMain.setVisibility(View.GONE);
			btSubmit.setVisibility(View.GONE);
			rlNull.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_titlebar_back:
			finish();
			break;
		case R.id.rl_mulpetservice_bath:
			if (!isBathSelected) {
				isBathSelected = true;
				isBeautySelected = false;
				ivBath.setBackgroundResource(R.drawable.icon_pay_selected);
				ivBeauty.setBackgroundResource(R.drawable.icon_pay_normal);
				tvBeautyTotalFee.setVisibility(View.GONE);
				tvBathTotalFee.setVisibility(View.VISIBLE);
				bathtotalfee = 0;
				beautytotalfee = 0;
				servicetype = 1;
				serviceid = bathserviceid;
				if (level == 1) {
					basefee = bathPriceLevel1;
					basefeewithbeautician = bathPriceLevel1;
				} else if (level == 2) {
					basefee = bathPriceLevel2;
					basefeewithbeautician = bathPriceLevel2;
				} else {
					basefee = bathPriceLevel3;
					basefeewithbeautician = bathPriceLevel3;
				}
				bathtotalfee = basefeewithbeautician;
				addserviceprice = 0;
				servicename = bathname;
				String text = "总价：¥" + Utils.formatDouble(bathtotalfee);
				SpannableString ss = new SpannableString(text);
				ss.setSpan(new ForegroundColorSpan(mContext.getResources()
						.getColor(R.color.black)), 0, 3,
						Spanned.SPAN_INCLUSIVE_INCLUSIVE);
				ss.setSpan(new TextAppearanceSpan(mContext, R.style.style3), 0,
						4, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
				tvBathTotalFee.setText(ss);
				for (int i = 0; i < serviceBeautyItemsList.size(); i++)
					serviceBeautyItemsList.get(i).isChecked = false;
				beautyAddServiceAdapter.notifyDataSetChanged();
				appointMentNewPetInfo.setServiceType(1);
				appointMentNewPetInfo.setUseCard(false);
				extraItems = null;
				appointMentNewPetInfo.setExtraItems(extraItems);
				if (appointMentNewPetInfo.getPetKind() == 1) {// 狗
					if (appointMentNewPetInfo.getServiceType() == 1) {// 狗洗澡
						appointMentNewPetInfo.setServiceId(1);
					} else if (appointMentNewPetInfo.getServiceType() == 2) {// 狗美容
						appointMentNewPetInfo.setServiceId(2);
					}
				} else if (appointMentNewPetInfo.getPetKind() == 2) {// 猫
					if (appointMentNewPetInfo.getServiceType() == 1) {// 猫洗澡
						appointMentNewPetInfo.setServiceId(3);
					} else if (appointMentNewPetInfo.getServiceType() == 2) {// 猫美容
						appointMentNewPetInfo.setServiceId(4);
					}
				}
			}
			break;
		case R.id.rl_mulpetservice_beauty:
			if (!isBeautySelected) {
				isBathSelected = false;
				isBeautySelected = true;
				ivBeauty.setBackgroundResource(R.drawable.icon_pay_selected);
				ivBath.setBackgroundResource(R.drawable.icon_pay_normal);
				tvBeautyTotalFee.setVisibility(View.VISIBLE);
				tvBathTotalFee.setVisibility(View.GONE);
				bathtotalfee = 0;
				beautytotalfee = 0;
				servicetype = 2;
				serviceid = beautyserviceid;
				if (level == 1) {
					basefee = beautyPriceLevel1;
					basefeewithbeautician = beautyPriceLevel1;
				} else if (level == 2) {
					basefee = beautyPriceLevel2;
					basefeewithbeautician = beautyPriceLevel2;
				} else {
					basefee = beautyPriceLevel3;
					basefeewithbeautician = beautyPriceLevel3;
				}
				beautytotalfee = basefeewithbeautician;
				addserviceprice = 0;
				servicename = beautyname;
				String text = "总价：¥" + Utils.formatDouble(beautytotalfee);
				SpannableString ss = new SpannableString(text);
				ss.setSpan(new ForegroundColorSpan(mContext.getResources()
						.getColor(R.color.black)), 0, 3,
						Spanned.SPAN_INCLUSIVE_INCLUSIVE);
				ss.setSpan(new TextAppearanceSpan(mContext, R.style.style3), 0,
						4, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
				tvBeautyTotalFee.setText(ss);
				for (int i = 0; i < serviceBathItemsList.size(); i++)
					serviceBathItemsList.get(i).isChecked = false;
				bathAddServiceAdapter.notifyDataSetChanged();

				appointMentNewPetInfo.setServiceType(2);
				appointMentNewPetInfo.setUseCard(false);
				extraItems = null;
				appointMentNewPetInfo.setExtraItems(extraItems);
				if (appointMentNewPetInfo.getPetKind() == 1) {// 狗
					if (appointMentNewPetInfo.getServiceType() == 1) {// 狗洗澡
						appointMentNewPetInfo.setServiceId(1);
					} else if (appointMentNewPetInfo.getServiceType() == 2) {// 狗美容
						appointMentNewPetInfo.setServiceId(2);
					}
				} else if (appointMentNewPetInfo.getPetKind() == 2) {// 猫
					if (appointMentNewPetInfo.getServiceType() == 1) {// 猫洗澡
						appointMentNewPetInfo.setServiceId(3);
					} else if (appointMentNewPetInfo.getServiceType() == 2) {// 猫美容
						appointMentNewPetInfo.setServiceId(4);
					}
				}
			}
			break;
		case R.id.bt_mulpetservice_submit:
			Intent data = new Intent();
			data.putExtra("appointMentNewPetInfo",
					(Serializable) appointMentNewPetInfo);
			setResult(Global.RESULT_OK, data);
			finishWithAnimation();
			break;
		case R.id.bt_null_refresh:
			getData();
			break;
		}
	}
}
