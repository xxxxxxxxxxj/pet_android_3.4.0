package com.haotang.pet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rx.Scheduler.Worker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.UserDictionary.Words;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haotang.base.SuperActivity;
import com.haotang.pet.adapter.OrderMyPetAdapter;
import com.haotang.pet.entity.AppointMentNewPetInfo;
import com.haotang.pet.entity.Coupon;
import com.haotang.pet.entity.AppointNewWorker.DataBean.WorkersBean.Workers;
import com.haotang.pet.entity.ExtraItem;
import com.haotang.pet.net.AsyncHttpResponseHandler;
import com.haotang.pet.util.CommUtil;
import com.haotang.pet.util.Global;
import com.haotang.pet.util.ToastUtil;
import com.haotang.pet.util.Utils;
import com.haotang.pet.view.MListview;

public class OrderDetailNewActivity extends SuperActivity implements OnClickListener{

	public static OrderDetailNewActivity act;
	private ImageButton ib_titlebar_back;
	private TextView tv_titlebar_title;
	
	private LinearLayout layout_remark
	,layout_is_pickup
	,layout_line_home_service_price
	,layout_line_order_total_price
	,layout_line_coupon_price
	,layout_line_homecutdown_price
	,layout_line_lastpay_price
	,layout_notice
	,layout_daohang
	;
	private RelativeLayout relayout_to_pay
	,relayout_show_address_detail
	;
	private TextView textview_username_phone
	,ordernew_appointtime
	,ordernew_appointbeau
	,ordernew_appointServiceType_title
	,ordernew_appointServiceType
	,ordernew_appointServiceType_address
	,textvie_pickupnums
	,textview_orderserviceprice_title
	,textview_orderserviceprice
	,textview_ordertotalprice
	,textview_ordercouponprice
	,textview_homecutdown_title
	,textview_homecutdown
	,textview_lastpay_title
	,textview_lastpay
	,text_all_order_notice
	,textview_to_pay
	,textview_remark
	;
	private ImageView img_go_pick_notice
	,img_is_choose_pick
	,img_daohang
	;
	private MListview listview_my_pets;
	
	private int serviceLoc
	,addressId
	,shopId
	,pickupResult 
	,level
	,couponId
	,paytype
	,orderNo
	,pickup//是否接送 0：不接送 1：接送
	;
	private double lat
	,lng
	,lastPrice
	,totalMoney
	,needMoney
	,pickupPrice
	,extraFee
	,reductionFee
	,couponAmount
	,shopLat
	,shopLng
	;
	private String appointment
	,cname
	,cphone
	;
	private Workers selectedWorker = null;
	private List<AppointMentNewPetInfo> appointMentNewPetInfoList = null;
	private OrderMyPetAdapter orderMyPetAdapter;
	private String address
	,shopName
	,pickupTip
	,restAmount
	,strp
	,note
	,shopAddress
	;
	private UUID uuid;
	private String [] notices = null;
	public static ArrayList<Coupon> couponList = new ArrayList<Coupon>();
	private boolean isChoosePickup =false;
	private double orderTotalPrice = 0;
	private double addservicefee=0;
	private boolean isUseCoupon = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail_new);
		act = this;
		MApplication.listAppoint.add(act);
		getDataLocal();
		getIntenData();
		findView();
		setData();
		setView();
		getIsCanPickNormal();
		getNotice();
	}
	private void getNotice() {
		CommUtil.confirmOrderPrompt(cphone, mContext, 1,serviceLoc,strp,selectedWorker.getWorkerId(),level,appointment,null,confirmOrderPrompt);
	}
	private void getIsCanPickNormal() {
		CommUtil.canBePickup(mContext,cphone,0,shopId,addressId,null,appointment,serviceLoc,lat,lng,IsCanPickNormalHandler);
	}
	private void getDataLocal() {
		couponList.clear();
		cname = spUtil.getString("username", "");
		cphone = spUtil.getString("cellphone", "");
	}
	private void setView() {
		tv_titlebar_title.setText("订单确认");
		textview_username_phone.setText(cname+" "+cphone);
		ordernew_appointtime.setText(appointment);
		try {
			ordernew_appointbeau.setText(selectedWorker.getRealname()+"-"+selectedWorker.getLevelName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		serviceLoc = appointMentNewPetInfoList.get(0).getServiceLoc();
		addressId = appointMentNewPetInfoList.get(0).getAddressId();
		shopId = appointMentNewPetInfoList.get(0).getShopId();
		shopName = appointMentNewPetInfoList.get(0).getShopName();
		shopAddress = appointMentNewPetInfoList.get(0).getShopAddress();
		level = appointMentNewPetInfoList.get(0).getLevel();
		address = appointMentNewPetInfoList.get(0).getAddress();
		for (int i = 0; i < appointMentNewPetInfoList.size(); i++) {
			orderTotalPrice+=appointMentNewPetInfoList.get(i).getPrice();
			if (appointMentNewPetInfoList.get(i).getExtraItems()!=null) {
				List<ExtraItem> extraItems = appointMentNewPetInfoList.get(i).getExtraItems();
				if (extraItems!=null) {
					for (int j = 0; j < extraItems.size(); j++) {
						orderTotalPrice+=extraItems.get(j).getPrice();
						addservicefee = extraItems.get(j).getPrice();
					}
				}
			}
		}
		Utils.mLogError("==-->orderTotalPrice "+orderTotalPrice);
		if (serviceLoc==1) {//到店
			ordernew_appointServiceType_title.setText("到店服务：");
			ordernew_appointServiceType.setText(shopName);
			ordernew_appointServiceType_address.setText(shopAddress);
			relayout_show_address_detail.setVisibility(View.VISIBLE);
		}else if (serviceLoc==2) {//上门
			ordernew_appointServiceType_title.setText("上门服务：");
			ordernew_appointServiceType.setText(address);
			relayout_show_address_detail.setVisibility(View.GONE);
			layout_is_pickup.setVisibility(View.GONE);
		}
		if (TextUtils.isEmpty(note)) {
			textview_remark.setText("暂无备注");
		}else {
			textview_remark.setText("查看备注");
		}
		setLastPrice();
	}
	private void setData() {
		orderMyPetAdapter = new OrderMyPetAdapter<AppointMentNewPetInfo>(mContext, appointMentNewPetInfoList);
		listview_my_pets.setAdapter(orderMyPetAdapter);
	}
	private void getIntenData() {
		lat = getIntent().getDoubleExtra("lat", 0);
		lng = getIntent().getDoubleExtra("lng", 0);
		shopLat = getIntent().getDoubleExtra("shopLat", 0);
		shopLng = getIntent().getDoubleExtra("shopLng", 0);
		orderNo = getIntent().getIntExtra("orderid", 0);
		appointment=getIntent().getStringExtra("appointment");
		strp=getIntent().getStringExtra("strp");
		lastPrice = getIntent().getDoubleExtra("lastPrice", 0);
		selectedWorker = (Workers) getIntent().getSerializableExtra("selectedWorker");
		appointMentNewPetInfoList = (List<AppointMentNewPetInfo>) getIntent().getSerializableExtra("appointMentNewPetInfoList");
		String addServiceIds=null;
		StringBuilder builderIds = new StringBuilder();
		for (int i = 0; i < appointMentNewPetInfoList.size(); i++) {
			if (appointMentNewPetInfoList.get(i).getExtraItems()!=null) {
				List<ExtraItem> extraItems =  appointMentNewPetInfoList.get(i).getExtraItems();
				for (int j = 0; j < extraItems.size(); j++) {
					builderIds.append(extraItems.get(j).getItemId()+",");
				}
				addServiceIds = builderIds.substring(0, builderIds.length()-1);
				appointMentNewPetInfoList.get(i).setAddServiceIds(addServiceIds);
			}else {
				appointMentNewPetInfoList.get(i).setAddServiceIds(addServiceIds);
			}
		}
		totalMoney = lastPrice;
		needMoney = lastPrice;
		if (needMoney<=0) {
			paytype = 7;
		}
	}
	private void findView() {
		uuid = UUID.randomUUID();
		ib_titlebar_back = (ImageButton) findViewById(R.id.ib_titlebar_back);
		tv_titlebar_title = (TextView) findViewById(R.id.tv_titlebar_title);
		textview_username_phone = (TextView) findViewById(R.id.textview_username_phone);
		layout_remark = (LinearLayout) findViewById(R.id.layout_remark);
		ordernew_appointtime = (TextView) findViewById(R.id.ordernew_appointtime);
		ordernew_appointbeau = (TextView) findViewById(R.id.ordernew_appointbeau);
		ordernew_appointServiceType_title = (TextView) findViewById(R.id.ordernew_appointServiceType_title);
		ordernew_appointServiceType = (TextView) findViewById(R.id.ordernew_appointServiceType);
		ordernew_appointServiceType_address = (TextView) findViewById(R.id.ordernew_appointServiceType_address);
		layout_is_pickup = (LinearLayout) findViewById(R.id.layout_is_pickup);
		img_go_pick_notice = (ImageView) findViewById(R.id.img_go_pick_notice);
		textvie_pickupnums = (TextView) findViewById(R.id.textvie_pickupnums);
		img_is_choose_pick = (ImageView) findViewById(R.id.img_is_choose_pick);
		img_daohang = (ImageView) findViewById(R.id.img_daohang);
		listview_my_pets = (MListview) findViewById(R.id.listview_my_pets);
		layout_line_home_service_price = (LinearLayout) findViewById(R.id.layout_line_home_service_price);
		textview_orderserviceprice_title = (TextView) findViewById(R.id.textview_orderserviceprice_title);
		textview_orderserviceprice = (TextView) findViewById(R.id.textview_orderserviceprice);
		layout_line_order_total_price = (LinearLayout) findViewById(R.id.layout_line_order_total_price);
		textview_ordertotalprice = (TextView) findViewById(R.id.textview_ordertotalprice);
		layout_line_coupon_price = (LinearLayout) findViewById(R.id.layout_line_coupon_price);
		textview_ordercouponprice = (TextView) findViewById(R.id.textview_ordercouponprice);
		layout_line_homecutdown_price = (LinearLayout) findViewById(R.id.layout_line_homecutdown_price);
		textview_homecutdown_title = (TextView) findViewById(R.id.textview_homecutdown_title);
		textview_homecutdown = (TextView) findViewById(R.id.textview_homecutdown);
		layout_line_lastpay_price = (LinearLayout) findViewById(R.id.layout_line_lastpay_price);
		textview_lastpay_title = (TextView) findViewById(R.id.textview_lastpay_title);
		textview_lastpay = (TextView) findViewById(R.id.textview_lastpay);
		layout_notice = (LinearLayout) findViewById(R.id.layout_notice);
		layout_daohang = (LinearLayout) findViewById(R.id.layout_daohang);
		text_all_order_notice = (TextView) findViewById(R.id.text_all_order_notice);
		textview_to_pay = (TextView) findViewById(R.id.textview_to_pay);
		textview_remark = (TextView) findViewById(R.id.textview_remark);
		relayout_show_address_detail = (RelativeLayout) findViewById(R.id.relayout_show_address_detail);
		relayout_to_pay = (RelativeLayout) findViewById(R.id.relayout_to_pay);
		layout_remark = (LinearLayout) findViewById(R.id.layout_remark);
		ib_titlebar_back.setOnClickListener(this);
		layout_remark.setOnClickListener(this);
		layout_line_coupon_price.setOnClickListener(this);
		img_go_pick_notice.setOnClickListener(this);
		img_is_choose_pick.setOnClickListener(this);
		img_daohang.setOnClickListener(this);
		layout_daohang.setOnClickListener(this);
		relayout_to_pay.setOnClickListener(this);
	}
	private AsyncHttpResponseHandler IsCanPickNormalHandler = new AsyncHttpResponseHandler() {
		
		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
			try {
				JSONObject object = new JSONObject(new String(responseBody));
				int code = object.getInt("code");
				if (code==0) {
					if (object.has("data")&&!object.isNull("data")) {
						JSONObject objectData  = object.getJSONObject("data");
						if (objectData.has("pickupResult")&&!objectData.isNull("pickupResult")) {
							pickupResult = objectData.getInt("pickupResult");
						}
						if (objectData.has("pickupTip")&&!objectData.isNull("pickupTip")) {
							pickupTip = objectData.getString("pickupTip");
						}
						if (objectData.has("restAmount")&&!objectData.isNull("restAmount")) {
							restAmount = objectData.getString("restAmount");
						}
						if (objectData.has("pickupPrice")&&!objectData.isNull("pickupPrice")) {
							pickupPrice = Utils.formatDouble(objectData.getDouble("pickupPrice"));
						}
						if (objectData.has("bottomTips")&&!objectData.isNull("bottomTips")) {
							text_all_order_notice.setVisibility(View.VISIBLE);
							try {
								JSONArray objectTip = objectData.getJSONArray("bottomTips");
								ArrayList<String> list = new ArrayList<String>();
								for (int i = 0; i < objectTip.length(); i++) {
									list.add(objectTip.getString(i));
								}
								text_all_order_notice.setText(list.get(0));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else {
							text_all_order_notice.setVisibility(View.GONE);
						}
						setPickShow(pickupResult);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			// TODO Auto-generated method stub
			
		}
	};
	private AsyncHttpResponseHandler confirmOrderPrompt = new AsyncHttpResponseHandler() {
		
		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
			// TODO Auto-generated method stub
			Utils.mLogError("==-->新增免责声明：="+new String(responseBody));
			try {
				JSONObject object = new JSONObject(new String(responseBody));
				int code = object.getInt("code");
				if (code == 0 ) {
					if (object.has("data")&&!object.isNull("data")) {
						JSONObject objectData = object.getJSONObject("data");
						layout_notice.removeAllViews();
						if (objectData.has("prompt")&&!objectData.isNull("prompt")) {
							String prompt = objectData.getString("prompt");
							TextView textView = new TextView(mContext);
							textView.setTextColor(getResources().getColor(R.color.a666666));
							textView.setText(prompt);
							layout_notice.addView(textView);
						}
						if (objectData.has("fee_tips")&&!objectData.isNull("fee_tips")) {
							JSONArray array = objectData.getJSONArray("fee_tips");
							if (array.length()>0) {
								notices = new String[array.length()];
								for (int i = 0; i < array.length(); i++) {
									notices[i] = array.getString(i);
								}
							}
						}
						if (serviceLoc==2) {
							layout_line_home_service_price.setVisibility(View.VISIBLE);
							layout_line_homecutdown_price.setVisibility(View.VISIBLE);
							if (objectData.has("extraFeeTag")&&!objectData.isNull("extraFeeTag")) {
								String extraFeeTag = objectData.getString("extraFeeTag");
								textview_orderserviceprice_title.setText(extraFeeTag);
							}
							if (objectData.has("extraFee")&&!objectData.isNull("extraFee")) {
								layout_line_home_service_price.setVisibility(View.VISIBLE);
								extraFee = objectData.getDouble("extraFee");
								textview_orderserviceprice.setText("¥ "+extraFee);
								if (extraFee<=0) {
									extraFee = 0;
									layout_line_home_service_price.setVisibility(View.GONE);
								}
							}else {
								layout_line_home_service_price.setVisibility(View.GONE);
							}
							
							if (objectData.has("reductionFeeTag")&&!objectData.isNull("reductionFeeTag")) {
								String reductionFeeTag = objectData.getString("reductionFeeTag");
								textview_homecutdown_title.setText(reductionFeeTag);
							}
							if (objectData.has("reductionFee")&&!objectData.isNull("reductionFee")) {
								layout_line_homecutdown_price.setVisibility(View.VISIBLE);
								reductionFee = objectData.getDouble("reductionFee");
								textview_homecutdown.setText("-¥ "+reductionFee);
								if (reductionFee<=0) {
									reductionFee = 0;
									layout_line_homecutdown_price.setVisibility(View.GONE);
								}
							}else {
								layout_line_homecutdown_price.setVisibility(View.GONE);
							}
						}else {
							layout_line_home_service_price.setVisibility(View.GONE);
							layout_line_homecutdown_price.setVisibility(View.GONE);
						}
						if (!isChoosePickup) {
							needMoney = totalMoney+extraFee-reductionFee;	
						}else {
							needMoney = totalMoney+extraFee-reductionFee+pickupPrice;	
						}
						setLastPrice();
					}
					if (needMoney<=0) {
						isUseCoupon = false;
						textview_ordercouponprice.setText("无须使用优惠券");
					}else {
						getCoupon();
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			// TODO Auto-generated method stub
			
		}
	};
	/**
	 * 获取可用的优惠券
	 */
	private void getCoupon(){
		mPDialog.showDialog();
		couponList.clear();
		couponId = 0;
		CommUtil.getAvailableCoupon(this,spUtil.getString("cellphone", ""), Global.getIMEI(this),
				Global.getCurrentVersion(this), appointment, 1, serviceLoc,
				selectedWorker.getWorkerId(),pickup,cname,cphone,addressId,address, lat, lng,
				getPetID_ServiceId_MyPetId_ItemIds(appointMentNewPetInfoList),0,Utils.formatDouble(needMoney),shopId,
				null,couponHanler);
		Utils.mLogError("==-->strp "+strp);
		Utils.mLogError("==-->getPetID_ServiceId_MyPetId_ItemIds(appointMentNewPetInfoList) "+getPetID_ServiceId_MyPetId_ItemIds(appointMentNewPetInfoList));
	}
	
	private AsyncHttpResponseHandler couponHanler = new AsyncHttpResponseHandler() {
		
		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
			// TODO Auto-generated method stub
			Utils.mLogError("可用的优惠券："+new String(responseBody));
			mPDialog.closeDialog();
			try {
				JSONObject jobj = new JSONObject(new String(responseBody));
				int resultCode = jobj.getInt("code");
				if (resultCode==0) {
					if (jobj.has("data")&&!jobj.isNull("data")) {
						JSONArray jData = jobj.getJSONArray("data");
						for(int i = 0; i < jData.length(); i++){
							JSONObject jcoupon = jData.getJSONObject(i);
							couponList.add(Coupon.jsonToEntity(jcoupon));
						}
						if(couponList.size() > 0){
							couponId = couponList.get(0).id;
							textview_ordercouponprice.setText("-"+couponList.get(0).amount+"("+couponList.get(0).name+")");
							String camount = couponList.get(0).amount == null || "".equals(couponList.get(0).amount)?"0":couponList.get(0).amount;
							couponAmount = Utils.formatDouble(Double.parseDouble(camount));
							if (!isChoosePickup) {
								getLastNeedMoneyisNotPickUp();
							}else {
								getLastNeedMoneyChoosePickUp();
							}
							textview_ordercouponprice.setTextColor(Color.parseColor("#D1494F"));
							
						}else {
							textview_ordercouponprice.setTextColor(Color.parseColor("#666666"));
							if (!isChoosePickup) {
								needMoney = totalMoney+extraFee-reductionFee-couponAmount;
							}else {
								needMoney = totalMoney+extraFee-reductionFee-couponAmount+pickupPrice;
							}
							textview_ordercouponprice.setText("无可用优惠券");
							setLastPrice();
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			// TODO Auto-generated method stub
			
		}
	};
	private void setPickShow(int pickupResult) {
		/*
		 * 接送结果pickupResult返回值定义：
		 * 0或是无：不支持接送，提示文案取pickupTip
		 * 1:可接送，价格字段：pickupPrice（0表示免费） 
		 * 2:接送已满，提示文案取pickupTip
		 * 3:不再接送范围，提示文案取pickupTip
		 */
		if (serviceLoc==1) {//到店
			layout_is_pickup.setVisibility(View.VISIBLE);
			if (pickupResult==1) {
				if (pickupPrice==0) {
//					textvie_pickupnums.setText(Utils.getTextAndColorFour(
//							"#BB996C", "免费", 
//							"#333333", "(剩余:", 
//							"#BB996C", restAmount, 
//							"#333333", "个)"));
					textvie_pickupnums.setText(Utils.getTextAndColorFour(
							"#D1494F", "免费", 
							"#D1494F", "(剩余:", 
							"#D1494F", restAmount, 
							"#D1494F", "个)"));
					img_is_choose_pick.setVisibility(View.VISIBLE);
				}else {
//					textvie_pickupnums.setText(Utils.getTextAndColorFour(
//							"#BB996C", "¥"+Utils.formatDouble(pickupPrice), 
//							"#333333", "(剩余:", 
//							"#BB996C", restAmount, 
//							"#333333", "个)"));
					textvie_pickupnums.setText(Utils.getTextAndColorFour(
							"#D1494F", "¥"+Utils.formatDouble(pickupPrice), 
							"#D1494F", "(剩余:", 
							"#D1494F", restAmount, 
							"#D1494F", "个)"));
					img_is_choose_pick.setVisibility(View.VISIBLE);
				}
			}else if (pickupResult==0||pickupResult==2||pickupResult==3) {
				textvie_pickupnums.setText(pickupTip);
				textvie_pickupnums.setTextColor(Color.parseColor("#D1494F"));
				img_is_choose_pick.setVisibility(View.GONE);
			}
		}else {
			layout_is_pickup.setVisibility(View.GONE);
		}
	}
	private void setLastPrice(){
		if (needMoney<=0) {
			needMoney=0;
		}
		textview_lastpay.setText("¥"+needMoney);
		textview_to_pay.setText(needMoney+"");
	}
	private void goBack(){
		MApplication.listAppoint.remove(this);
		Intent data = new Intent();
		data.putExtra("orderid", orderNo);
		setResult(Global.RESULT_OK,data);
		finishWithAnimation();
	}
	private void goNextForData(Class clazz, int requestcode,int previous){
		Intent intent = new Intent(this, clazz);
		intent.putExtra(Global.ANIM_DIRECTION(), Global.ANIM_COVER_FROM_RIGHT());
		getIntent().putExtra(Global.ANIM_DIRECTION(), Global.ANIM_COVER_FROM_LEFT());
		intent.putExtra("previous", previous);
		intent.putExtra("cname", cname);
		intent.putExtra("cphone", cphone);
		intent.putExtra("remark", note);
		intent.putExtra("couponId", couponId);
		intent.putExtra("shopname",shopName);
		intent.putExtra("shopaddr", shopAddress);
		intent.putExtra("shoplat", shopLat);
		intent.putExtra("shoplng", shopLng);
		Utils.mLogError("==-->shopLat "+shopLat+"    shopLng "+shopLng);
		Utils.mLogError("==-->shlat "+lat+"    lng "+lng);
		startActivityForResult(intent, requestcode);
	}
	private void goPayResult(){
		Intent intent = new Intent(mContext, PaySuccessActivity.class);
		intent.putExtra("orderId",orderNo);
		intent.putExtra("type", 1);
		startActivity(intent);
		finishWithAnimation();
	}
	private void goNext(Class clazz){
		Intent intent = new Intent(this, clazz);
		intent.putExtra("previous", Global.PRE_ORDERDETAILACTIVITY_TO_AVAILABLECOUPONACTIVITY);
		intent.putExtra("notices", notices);
		startActivity(intent);
		this.overridePendingTransition(R.anim.center_in, R.anim.center_out);
	}
	private void goOrderPayActivity() {
		Intent intent = new Intent(mContext, OrderPayActivity.class);
		intent.putExtra(Global.ANIM_DIRECTION(), Global.ANIM_COVER_FROM_RIGHT());
		getIntent().putExtra(Global.ANIM_DIRECTION(), Global.ANIM_COVER_FROM_LEFT());
		intent.putExtra("previous", Global.BATHBEAY_NEW_TO_ORDERPAY);
		intent.putExtra("appointMentNewPetInfoList", (Serializable)appointMentNewPetInfoList);
		intent.putExtra("selectedWorker", selectedWorker);
		intent.putExtra("couponid", couponId);
		intent.putExtra("pickup", pickup);
		intent.putExtra("couponamount", couponAmount);
		intent.putExtra("payfee", needMoney);
		intent.putExtra("totalfee", orderTotalPrice);
		intent.putExtra("cname", cname);
		intent.putExtra("cphone", cphone);
		intent.putExtra("orderid", orderNo);
		intent.putExtra("uuid", uuid.toString());
		intent.putExtra("remark",note);
		intent.putExtra("clicksort",level);
		intent.putExtra("serviceloc",serviceLoc);
		intent.putExtra("addr_lat",lat);
		intent.putExtra("addr_lng",lng);
		intent.putExtra("addressid",addressId);
		intent.putExtra("address",address);
		intent.putExtra("shopId",shopId);
		intent.putExtra("date",appointment);
		intent.putExtra("listIds",getListIds(getIntent().getIntegerArrayListExtra("listIds")));
		startActivityForResult(intent, Global.BATHBEAY_NEW_TO_ORDERPAY);
	}
	private String getListIds(ArrayList<Integer> listIds){
		StringBuilder builder  = new StringBuilder();
		if (listIds.size()>0) {
			for (int i = 0; i < listIds.size(); i++) {
				builder.append(listIds.get(i)+",");
			}
			return builder.substring(0, builder.length()-1);
		}else {
			return null;
		}
	}
	private void changepay(){
		mPDialog.showDialog();
		if(needMoney<0)
			needMoney=0;
		CommUtil.changeOrderPayMethodV2(null,spUtil.getString("cellphone", ""),
				Global.getIMEI(this), this, orderNo, 
				spUtil.getInt("userid", 0),cname,cphone,note,
				pickup,-1,Utils.formatDouble(addservicefee),Utils.formatDouble(needMoney),paytype,
				couponId,-1,null,false,getListIds(getIntent().getIntegerArrayListExtra("listIds")),appointment,getPetID_ServiceId_MyPetId_ItemIds(appointMentNewPetInfoList),changeHanler);
	}
	private AsyncHttpResponseHandler changeHanler = new AsyncHttpResponseHandler()
	{
		
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] responseBody) {
			mPDialog.closeDialog();
			Utils.mLogError("重新支付："+new String(responseBody));
			try {
				JSONObject jobj = new JSONObject(new String(responseBody));
				int resultCode = jobj.getInt("code");
				String msg = jobj.getString("msg");
				if(0 == resultCode&&jobj.has("data")&&!jobj.isNull("data")){
					JSONObject jdata = jobj.getJSONObject("data");
					if(jdata.has("orderPet")&&!jdata.isNull("orderPet")){
						JSONArray jarr = jdata.getJSONArray("orderPet");
					}
					if (needMoney==0||paytype==3) {
						//直接跳转到支付成功
						goPayResult();
					}
				}else{
					ToastUtil.showToastShort(mContext, msg);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			mPDialog.closeDialog();
		}
		
	};
	private String getPetID_ServiceId_MyPetId_ItemIds(
			List<AppointMentNewPetInfo> list) {
		if (list == null)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			AppointMentNewPetInfo mps = list.get(i);
			if (i < list.size() - 1) {
				sb.append(mps.getPetId());
				sb.append("_");
				sb.append(mps.getServiceId());
				sb.append("_");
				sb.append(mps.getMyPetId());
				sb.append("_");
				if (mps.getAddServiceIds() != null && !"".equals(mps.getAddServiceIds()))
					sb.append(mps.getAddServiceIds());
				else
					sb.append("0");
				sb.append("_");
				
			} else {
				sb.append(mps.getPetId());
				sb.append("_");
				sb.append(mps.getServiceId());
				sb.append("_");
				sb.append(mps.getMyPetId());
				sb.append("_");
				if (mps.getAddServiceIds() != null && !"".equals(mps.getAddServiceIds()))
					sb.append(mps.getAddServiceIds());
				else
					sb.append("0");
			}
		}
		return sb.toString();
	}
	private void sendOrder(){
		mPDialog.showDialog();
		if(needMoney<0)
			needMoney=0;
		
		CommUtil.newOrder(this,spUtil.getString("cellphone", ""), Global.getIMEI(this),
				Global.getCurrentVersion(this), 
				shopId,0, couponId, selectedWorker.getWorkerId(), addressId, cname, 
				cphone, address, lat, lng, appointment, Utils.formatDouble(orderTotalPrice),
				Utils.formatDouble(needMoney),
				paytype,note,
				serviceLoc,pickup,-1,
				getPetID_ServiceId_MyPetId_ItemIds(appointMentNewPetInfoList),-1,
				null,getListIds(getIntent().getIntegerArrayListExtra("listIds")),orderHanler);
	}
	private AsyncHttpResponseHandler orderHanler = new AsyncHttpResponseHandler()
	{
		
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] responseBody) {
			mPDialog.closeDialog();
			Utils.mLogError("生成新订单："+new String(responseBody));
			try {
				JSONObject jobj = new JSONObject(new String(responseBody));
				int resultCode = jobj.getInt("code");
				
				if(0 == resultCode&&jobj.has("data")&&!jobj.isNull("data")){
					spUtil.saveInt("payway", paytype);
					JSONObject jData = jobj.getJSONObject("data");
					if(jData.has("orderId")&&!jData.isNull("orderId")){
						orderNo = Integer.parseInt(jData.getString("orderId"));
						Utils.mLogError("==-->orderNo "+orderNo);
					}
					
					if(jData.has("orderPet")&&!jData.isNull("orderPet")){
						JSONArray jarr = jData.getJSONArray("orderPet");
					}
					if(needMoney == 0||paytype==3){
						//直接跳转到支付成功
						goPayResult();
					}else{
						Utils.mLogError("用第三方支付"+needMoney);
					}
				}else if(109 == resultCode){
					//重复提交，不做处理
				}else{
					if(jobj.has("msg")&&!jobj.isNull("msg"))
						ToastUtil.showToastShort(mContext, jobj.getString("msg"));
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mPDialog.closeDialog();
			}
			
		}
		
		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			mPDialog.closeDialog();
		}
		
	};
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
			goBack();
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode==Global.RESULT_OK) {
			if (requestCode==Global.ORDERDETAILREQUESTCODE_NOTE) {
				note = data.getStringExtra("note");
				cname = data.getStringExtra("cname");
				cphone = data.getStringExtra("cphone");
				textview_username_phone.setText(cname+" "+cphone);
				if (TextUtils.isEmpty(note)) {
					textview_remark.setText("暂无备注");
				}else {
					textview_remark.setText("查看备注");
				}
			}else if (requestCode==Global.ORDERDETAILREQUESTCODE_COUPON) {
				couponId = data.getIntExtra("couponid", 0);
				String couponcontent = data.getStringExtra("content");
				String camount = data.getStringExtra("amount") == null || "".equals(data.getStringExtra("amount"))?"0":data.getStringExtra("amount");
				couponAmount = Utils.formatDouble(Double.parseDouble(camount));
				textview_ordercouponprice.setText(couponcontent);
				if (!isChoosePickup) {
					getLastNeedMoneyisNotPickUp();
				}else {
					getLastNeedMoneyChoosePickUp();
				}
			}else if(requestCode == Global.BATHBEAY_NEW_TO_ORDERPAY){
				orderNo = data.getIntExtra("orderid", 0);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	private void getLastNeedMoneyChoosePickUp() {
		if(couponAmount >= totalMoney+extraFee-reductionFee+pickupPrice){
			needMoney = 0;
			paytype = 3;
			setLastPrice();
		}else{
			needMoney = totalMoney +extraFee-reductionFee - couponAmount+pickupPrice;
			setLastPrice();
		}
	}
	private void getLastNeedMoneyisNotPickUp() {
		if(couponAmount >= totalMoney+extraFee-reductionFee){
			needMoney = 0;
			paytype = 3;
			setLastPrice();
		}else{
			needMoney = totalMoney +extraFee-reductionFee - couponAmount;
			setLastPrice();
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_titlebar_back:
			goBack();
			break;
		case R.id.layout_remark:
			//填写留言
			goNextForData(NoteActivity.class, Global.ORDERDETAILREQUESTCODE_NOTE,Global.ORDERDDETAIL_NEW_TO_CHOOSE_REMARK);
			break;
		case R.id.img_go_pick_notice:
			notices=null;
			goNext(PickupHintActivity.class);
			break;
		case R.id.img_is_choose_pick:
			if (!isChoosePickup) {
				isChoosePickup = true;
				pickup = 1;
				img_is_choose_pick.setImageResource(R.drawable.complaint_reason);
				needMoney = totalMoney+extraFee-reductionFee+pickupPrice;
			}else {
				isChoosePickup = false;
				pickup = 0;
				img_is_choose_pick.setImageResource(R.drawable.complaint_reason_disable);
				needMoney = totalMoney+extraFee-reductionFee;
			}		
			setLastPrice();
			if (needMoney<=0) {
				isUseCoupon = false;
				textview_ordercouponprice.setText("无须使用优惠券");
			}else {
				getCoupon();
			}
			break;
		case R.id.layout_line_coupon_price:
			//选择优惠券
			if(couponList.size()>0){
				goNextForData(AvailableCouponActivity.class, Global.ORDERDETAILREQUESTCODE_COUPON,Global.ORDERDDETAIL_NEW_TO_CHOOSE_COUPON);
			}else{
				ToastUtil.showToastShortCenter(mContext, textview_ordercouponprice.getText().toString());
			}
			break;
		case R.id.img_daohang:
		case R.id.layout_daohang:
			goNextForData(ShopLocationActivity.class, 0, 0);
			break;
		case R.id.relayout_to_pay:
			if (needMoney<=0) {
				if (orderNo>0) {
					changepay();
				}else {
					sendOrder();
				}
			}else {
				goOrderPayActivity();
			}
			break;
		default:
			break;
		}
	}
	
}
