package com.haotang.pet;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.haotang.base.SuperActivity;
import com.haotang.pet.adapter.BannerBathLoopAdapter;
import com.haotang.pet.adapter.JoinWorkAdapter;
import com.haotang.pet.adapter.ServiceMyPetAdapter;
import com.haotang.pet.adapter.ServiceNewTopMsgAdapter;
import com.haotang.pet.adapter.ServiceTypeAdapter;
import com.haotang.pet.entity.Beautician;
import com.haotang.pet.entity.MyPet;
import com.haotang.pet.entity.ServiceShopAdd;
import com.haotang.pet.entity.ServiceType;
import com.haotang.pet.entity.ServiceTypeBanner;
import com.haotang.pet.entity.ServiceTypeTopMsg;
import com.haotang.pet.net.AsyncHttpResponseHandler;
import com.haotang.pet.util.CommUtil;
import com.haotang.pet.util.Global;
import com.haotang.pet.util.MDialog;
import com.haotang.pet.util.ToastUtil;
import com.haotang.pet.util.UmengStatistics;
import com.haotang.pet.util.Utils;
import com.haotang.pet.verticalbanner.VerticalBannerView;
import com.haotang.pet.view.MListview;
import com.haotang.pet.view.rollviewpager.RollPagerView;

public class ServiceNewActivity extends SuperActivity implements OnClickListener{
	
	public static ServiceNewActivity act;
	private ImageButton ib_titlebar_back;
	private TextView tv_titlebar_title;
	private TextView tv_titlebar_other;
	private RelativeLayout rl_ppllayout_top;
	private RollPagerView rpvBanner;
	private RelativeLayout relay_topmsg;
	private TextView textview_serviceType_card_title;
	private VerticalBannerView atvTopMsg;
	private TextView textview_go_buy_cards;
	private RelativeLayout relay_choose_address;
	private ImageView address_right_img;
	private TextView address_detail;
	private RelativeLayout relay_choose_shop;
	private TextView shop_address_detail;
	private TextView shop_address_right_img;
	private MListview mListview_my_pet;
	private MListview mListview_service_type;
	private MListview service_scaling_image;
	private BannerBathLoopAdapter adapterBanner;
	private ServiceMyPetAdapter myPetAdapter;
	private ServiceTypeAdapter typeAdapter;
	private ServiceNewTopMsgAdapter topMsgAdapter;
	private JoinWorkAdapter joinWorkAdapter;
	private ArrayList<String> listBannerStr = new ArrayList<String>();
	private ArrayList<ServiceTypeBanner> listBanner = new ArrayList<ServiceTypeBanner>();
	private ArrayList<ServiceTypeTopMsg> listStrs = new ArrayList<ServiceTypeTopMsg>();
	private ArrayList<MyPet> listMyPets = new ArrayList<MyPet>();
	private ArrayList<ServiceType> listServiceType = new ArrayList<ServiceType>();
	private ArrayList<String> Imagelist = new ArrayList<String>();
	
	
	private LinearLayout layout_un_login_show;
	private ScrollView scrollView_service_new;
	
	private int serviceType;
	private String myPetIds;
	private int addressNetId;
	private int addressId;
	private String address;
	private int workerId;
	private String buyCardUrl=null;
	private int shopId;
	private String shopName;
	private int addressAmount;
	private double lat;
	private double lng;
	private ArrayList<MyPet> choosePets = new ArrayList<MyPet>();
	private int shopIdNet;
	private int myPetId;
	private int serviceLoc;
	private String shopTag;
	private String shopAddress;
	private double shopLat;
	private double shopLng;
	private Beautician beautician = null;
	private ServiceShopAdd LastShop = new ServiceShopAdd();
	private ServiceShopAdd now = new ServiceShopAdd();
	private boolean isCancle = false;
	private boolean isGoing = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_servicenew);
		act = this;
		MApplication.listAppoint.add(act);
		firstCleanList();
		getIntentData();
		findView();
		setData();
		setView();
		
	}
	private void firstCleanList() {
		listBannerStr.clear();
		listBanner.clear();
		listStrs.clear();
		listServiceType.clear();
		listMyPets.clear();
		Imagelist.clear();
	}
	private void getIntentData() {
		// TODO Auto-generated method stub
		serviceType = getIntent().getIntExtra("serviceType", 0);
		beautician = (Beautician) getIntent().getSerializableExtra("beautician");
		if (beautician!=null) {
			workerId = beautician.id;
			serviceType = beautician.BeauDetailServiceType;
		}
		myPetId = getIntent().getIntExtra("myPetId", 0);//从我的宠物过来的
	}
	private void setView() {
		if (serviceType == 1) {
			tv_titlebar_title.setText("洗澡");
		}else if (serviceType == 2) {
			tv_titlebar_title.setText("美容");
		}else if (serviceType ==3 ) {
			tv_titlebar_title.setText("特色服务");
		}
		tv_titlebar_other.setVisibility(View.VISIBLE);
		tv_titlebar_other.setText("添加宠物");
		tv_titlebar_other.setTextColor(getResources().getColor(R.color.white));
		
		service_scaling_image.setFocusable(false);
		
		
		getPetData();
	}
	private void setData() {
		adapterBanner = new BannerBathLoopAdapter(rpvBanner, listBannerStr);
		rpvBanner.setAdapter(adapterBanner);
		myPetAdapter = new ServiceMyPetAdapter<MyPet>(mContext, listMyPets);
		mListview_my_pet.setAdapter(myPetAdapter);
		typeAdapter = new ServiceTypeAdapter<ServiceType>(mContext, listServiceType);
		mListview_service_type.setAdapter(typeAdapter);
		joinWorkAdapter = new JoinWorkAdapter<String>(this, Imagelist);
		service_scaling_image.setAdapter(joinWorkAdapter);
		service_scaling_image.setFocusable(false);
		mListview_my_pet.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (isGoing) {
//					ToastUtil.showToastShortCenter(mContext, "请等待数据加载完");
					return;
				}
				MyPet myPet = (MyPet) parent.getItemAtPosition(position);
				if (myPet.selected==0) {
					int count = 0;
					for (int i = 0; i < listMyPets.size(); i++) {
						if (listMyPets.get(i).selected==1) {
							count++;
						}
					}
					if (count==5) {
						ToastUtil.showToastShortCenter(mContext, "最多选择5只宠物");
						return;
					}
					myPet.selected=1;
				}else if (myPet.selected==1) {
					myPet.selected=0;
				}
				listMyPets.set(position, myPet);
				myPetAdapter.notifyDataSetChanged();
				myPetIds = getMyPetIds(listMyPets);
				isCancle = true;
				isGoing = true;
				getServiceType2();
			}
		});
		mListview_service_type.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (!Utils.checkLogin(mContext)) {
					goNext(LoginActivity.class, Global.SERVICE_NEW_TO_LOGIN, Global.SERVICE_NEW_TO_LOGIN);
					return;
				}
				if (listMyPets.size()<=0) {
					ToastUtil.showToastShortCenter(mContext, "请您先添加宠物再下单哦~");
					return;
				}
				if (getChoosePets(listMyPets).size()<=0) {
					ToastUtil.showToastShortCenter(mContext, "请您先选择宠物再下单哦~");
					return;
				}
				ServiceType serviceType = (ServiceType) parent.getItemAtPosition(position);
				if (!TextUtils.isEmpty(serviceType.disabled_tip)) {
					ToastUtil.showToastShortCenter(mContext,serviceType.disabled_tip);
					return;
				}else {
					serviceLoc = serviceType.serviceLoc;
					if (serviceLoc==1) {//1 到店
						if (addressId<=0) {
							ToastUtil.showToastShortCenter(mContext, "请您先填写地址再下单哦~");
							return;
						}else if (shopId<=0) {
							if (shopIdNet<=0) {
								ToastUtil.showToastShortCenter(mContext, "请您先选择门店再下单哦~");
								return;
							}
						}
					}else if (serviceLoc==2) {//2上门
						if (addressId<=0) {
							ToastUtil.showToastShortCenter(mContext, "请您先填写地址再下单哦~");
							return;
						}else if (shopId<=0) {
							if (shopIdNet<=0) {
								ToastUtil.showToastShortCenter(mContext, "请您先选择门店再下单哦~");
								return;
							}
						}
					}
					goAppoint(AppointMentNewActivity.class);
				}
			}
		});
	}
	private void findView() {
		ib_titlebar_back = (ImageButton) findViewById(R.id.ib_titlebar_back);
		tv_titlebar_title = (TextView) findViewById(R.id.tv_titlebar_title);
		tv_titlebar_other = (TextView) findViewById(R.id.tv_titlebar_other);
		rl_ppllayout_top = (RelativeLayout) findViewById(R.id.rl_ppllayout_top);
		rpvBanner = (RollPagerView) findViewById(R.id.rpv_servicedetail_pet);
		relay_topmsg = (RelativeLayout) findViewById(R.id.relay_topmsg);
		textview_serviceType_card_title = (TextView) findViewById(R.id.textview_serviceType_card_title);
		atvTopMsg = (VerticalBannerView) findViewById(R.id.vbv_mainfragmentcontent_topmsg_msg);
		textview_go_buy_cards = (TextView) findViewById(R.id.textview_go_buy_cards);
		relay_choose_address = (RelativeLayout) findViewById(R.id.relay_choose_address);
		address_right_img = (ImageView) findViewById(R.id.address_right_img);
		address_detail = (TextView) findViewById(R.id.address_detail);
		relay_choose_shop = (RelativeLayout) findViewById(R.id.relay_choose_shop);
		shop_address_detail = (TextView) findViewById(R.id.shop_address_detail);
		shop_address_right_img = (TextView) findViewById(R.id.shop_address_right_img);
		mListview_my_pet = (MListview) findViewById(R.id.mListview_my_pet);
		mListview_service_type = (MListview) findViewById(R.id.mListview_service_type);
		service_scaling_image = (MListview) findViewById(R.id.service_scaling_image);
		scrollView_service_new = (ScrollView) findViewById(R.id.scrollView_service_new);
		scrollView_service_new.smoothScrollTo(0, 0);
		
		layout_un_login_show = (LinearLayout) findViewById(R.id.layout_un_login_show);
		
		
		ib_titlebar_back.setOnClickListener(this);
		tv_titlebar_other.setOnClickListener(this);
		layout_un_login_show.setOnClickListener(this);
		textview_go_buy_cards.setOnClickListener(this);
		relay_choose_shop.setOnClickListener(this);
		shop_address_right_img.setOnClickListener(this);
		relay_choose_address.setOnClickListener(this);
		address_right_img.setOnClickListener(this);
		
		if (workerId>0) {
			relay_choose_shop.setOnClickListener(null);
			shop_address_right_img.setVisibility(View.GONE);
		}
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		int mWidth = Utils.getDisplayMetrics(this)[0];
		LayoutParams layoutParams = rl_ppllayout_top.getLayoutParams();
		layoutParams.width = LayoutParams.MATCH_PARENT;
		layoutParams.height = mWidth * 1 / 2;
		rl_ppllayout_top.setLayoutParams(layoutParams);
		
		textview_go_buy_cards.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		textview_go_buy_cards.getPaint().setAntiAlias(true);
		textview_go_buy_cards.setText("立即抢购>");
		
	}
	private void getPetData() {
		mPDialog.showDialog();
		mPDialog.setCancelable(false);
		mPDialog.setCanceledOnTouchOutside(false);
		CommUtil.queryLastOrderInfo(mContext,serviceType,handler);
	}
	private AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
		
		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
			try {
				mPDialog.closeDialog();
				JSONObject object = new JSONObject(new String(responseBody));
				int code = object.getInt("code");
				if (code == 0 ) {
					if (object.has("data")&&!object.isNull("data")) {
						JSONObject objectData = object.getJSONObject("data");
						if (objectData.has("addressAmount")&&!objectData.isNull("addressAmount")) {
							addressAmount = objectData.getInt("addressAmount");
						}else {
							addressAmount = 0;
						}
						if (objectData.has("shopTag")&&!objectData.isNull("shopTag")) {
							shopTag = objectData.getString("shopTag");
						}
						listMyPets.clear();
						if (objectData.has("pets")&&!objectData.isNull("pets")) {
							JSONArray arrayPet = objectData.getJSONArray("pets");
							if (arrayPet.length()>0) {
								for (int i = 0; i < arrayPet.length(); i++) {
									MyPet myPet = new MyPet();
									JSONObject objectEva = arrayPet.getJSONObject(i);
									if (objectEva.has("avatar")&&!objectEva.isNull("avatar")) {
										myPet.avatar = objectEva.getString("avatar");
									}
									if (objectEva.has("petName")&&!objectEva.isNull("petName")) {
										myPet.petName = objectEva.getString("petName");
									}
									if (objectEva.has("petId")&&!objectEva.isNull("petId")) {
										myPet.petId = objectEva.getInt("petId");
									}
									if (objectEva.has("myPetId")&&!objectEva.isNull("myPetId")) {
										myPet.myPetId = objectEva.getInt("myPetId");
									}
									if (objectEva.has("nickname")&&!objectEva.isNull("nickname")) {
										myPet.nickname = objectEva.getString("nickname");
									}
									if (objectEva.has("petKind")&&!objectEva.isNull("petKind")) {
										myPet.petKind = objectEva.getInt("petKind");
									}
									if (objectEva.has("selected")&&!objectEva.isNull("selected")) {
										if (myPet.myPetId==myPetId) {
											myPet.selected = 1;
										}else {
											myPet.selected = objectEva.getInt("selected");
										}
									}
									listMyPets.add(myPet);
								}
							}
							if (listMyPets.size()>0) {
								layout_un_login_show.setVisibility(View.GONE);
								mListview_my_pet.setVisibility(View.VISIBLE);
								myPetAdapter.notifyDataSetChanged();
								if (choosePets.size()>0) {
									for (int i = 0; i < listMyPets.size(); i++) {
										for (int j = 0; j < choosePets.size(); j++) {
											if (listMyPets.get(i).myPetId==choosePets.get(j).myPetId) {
												listMyPets.get(i).selected=1;
											}
										}
									}
								}
								myPetIds = getMyPetIds(listMyPets);
							}else {
								layout_un_login_show.setVisibility(View.VISIBLE);
								mListview_my_pet.setVisibility(View.GONE);
							}
						}
						if (objectData.has("address")&&!objectData.isNull("address")) {
							JSONObject objectAddr = objectData.getJSONObject("address");
							if (objectAddr.has("addressId")&&!objectAddr.isNull("addressId")) {
								addressId = objectAddr.getInt("addressId");
								addressNetId = addressId;
							}
							if (objectAddr.has("address")&&!objectAddr.isNull("address")) {
								address = objectAddr.getString("address");
								address_detail.setText(address);
							}
							if (objectAddr.has("lat")&&!objectAddr.isNull("lat")) {
								lat = objectAddr.getDouble("lat");
							}
							if (objectAddr.has("lng")&&!objectAddr.isNull("lng")) {
								lng = objectAddr.getDouble("lng");
							}
						}else {
							if (addressAmount>0) {
								address_detail.setText("请选择宠物地址");
							}else {
								address_detail.setText("请填写宠物地址");
							}
						}
						if (objectData.has("shop")&&!objectData.isNull("shop")) {
							JSONObject objectShop = objectData.getJSONObject("shop");
							if (objectShop.has("shopId")&&!objectShop.isNull("shopId")) {
								shopId = objectShop.getInt("shopId");
								LastShop.shopId = shopId;
							}
							if (objectShop.has("shopName")&&!objectShop.isNull("shopName")) {
								shopName = objectShop.getString("shopName");
								shop_address_detail.setText(shopName);
								shop_address_right_img.setText("切换");
								LastShop.shopName=shopName;
							}
							if (objectShop.has("address")&&!objectShop.isNull("address")) {
								shopAddress = objectShop.getString("address");
								LastShop.shopAddress=shopAddress;
							}
							if (objectShop.has("lat")&&!objectShop.isNull("lat")) {
								shopLat = objectShop.getDouble("lat");
								LastShop.shoplat=shopLat;
							}
							if (objectShop.has("lng")&&!objectShop.isNull("lng")) {
								shopLng = objectShop.getDouble("lng");
								LastShop.shoplng=shopLng;
							}
						}else {//没有推荐门店取默认值
							shop_address_detail.setText(shopTag);
							shop_address_right_img.setText("查看");
						}
						if (!Utils.checkLogin(mContext)) {
							shop_address_detail.setText(shopTag);
							shop_address_right_img.setText("查看");
							
							relay_choose_address.setVisibility(View.GONE);
							layout_un_login_show.setVisibility(View.VISIBLE);
							mListview_my_pet.setVisibility(View.GONE);
						}else {
							layout_un_login_show.setVisibility(View.GONE);
							mListview_my_pet.setVisibility(View.VISIBLE);
							
							relay_choose_address.setVisibility(View.VISIBLE);
							if (listMyPets.size()<=0) {
								layout_un_login_show.setVisibility(View.VISIBLE);
								mListview_my_pet.setVisibility(View.GONE);
							}
							if (addressAmount<=0) {
								shop_address_detail.setText(shopTag);
								shop_address_right_img.setText("查看");
							}
						}
						listBanner.clear();
						listBannerStr.clear();
						if (objectData.has("banners")&&!objectData.isNull("banners")) {
							JSONArray array = objectData.getJSONArray("banners");
							if (array.length()>0) {
								for (int i = 0; i < array.length(); i++) {
									ServiceTypeBanner typeBanner = new ServiceTypeBanner();
									JSONObject objectEva = array.getJSONObject(i);
									if (objectEva.has("url")&&!objectEva.isNull("url")) {
										typeBanner.url = objectEva.getString("url");
									}
									if (objectEva.has("img")&&!objectEva.isNull("img")) {
										typeBanner.img = objectEva.getString("img");
										listBannerStr.add(typeBanner.img);
									}
									listBanner.add(typeBanner);
								}
							}
						}
						if (listBanner.size()>0||listBannerStr.size()>0) {
							adapterBanner.setServiceType(listBanner);
							adapterBanner.notifyDataSetChanged();
						}
						if (objectData.has("cardUseNews")&&!objectData.isNull("cardUseNews")) {
							JSONObject objectCardUse = objectData.getJSONObject("cardUseNews");
							if (objectCardUse.has("btn_txt")&&!objectCardUse.isNull("btn_txt")) {
								textview_go_buy_cards.setText(objectCardUse.getString("btn_txt"));
							}
							if (objectCardUse.has("url")&&!objectCardUse.isNull("url")) {
								buyCardUrl = objectCardUse.getString("url");
//								if (Utils.isLog) {
//									buyCardUrl = "http://test.chongwuhome.cn/static/content/html5/201703/cardList.html";
//								}
							}
							if (objectCardUse.has("title")&&!objectCardUse.isNull("title")) {
								String title = objectCardUse.getString("title");
								textview_serviceType_card_title.setText(title);
							}
							listStrs.clear();
							if (objectCardUse.has("data")&&!objectCardUse.isNull("data")) {
								JSONArray array = objectCardUse.getJSONArray("data");
								if (array.length()>0) {
									for (int i = 0; i < array.length(); i++) {
										listStrs.add(ServiceTypeTopMsg.json2Entity(array.getJSONObject(i)));
									}
								}
							}
							if (listStrs.size()>0) {
								relay_topmsg.setVisibility(View.VISIBLE);
								try {
									if (topMsgAdapter==null) {
										topMsgAdapter = new ServiceNewTopMsgAdapter(listStrs);
										atvTopMsg.setAdapter(topMsgAdapter);
										atvTopMsg.start();
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}else {
								relay_topmsg.setVisibility(View.GONE);
							}
						}
						Imagelist.clear();
						if (objectData.has("detailImgs")&&!objectData.isNull("detailImgs")) {
							JSONArray array = objectData.getJSONArray("detailImgs");
							if (array.length()>0) {
								for (int i = 0; i < array.length(); i++) {
									Imagelist.add(array.getString(i));
								}
							}
						}
						if (Imagelist.size()>0) {
							joinWorkAdapter.notifyDataSetChanged();
						}
					}
					getServiceType();
				}else {
					ToastUtil.showToastShort(mContext, object.getString("msg"));
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
	
	private String getMyPetIds(ArrayList<MyPet> list){
		StringBuilder builder = new StringBuilder();
		boolean isChoosePet = false;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).selected==1) {
				isChoosePet = true;
				builder.append(list.get(i).myPetId+",");
			}
		}
		if (!isChoosePet) {
			return null;
		}else {
			return builder.substring(0, builder.length()-1);
		}
	}
	private ArrayList<MyPet> getChoosePets(ArrayList<MyPet> list){
		ArrayList<MyPet> ChooseMyPets = new ArrayList<MyPet>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).selected==1) {
				list.get(i).serviceType = serviceType;
				list.get(i).serviceLoc = serviceLoc;
				list.get(i).addressId = addressId;
				list.get(i).address = address;
				Utils.mLogError("==-->222222 "+address +"  shopName "+LastShop.shopName+" shopAddress "+LastShop.shopAddress);
				list.get(i).shopName = LastShop.shopName;
				list.get(i).shopAddress = LastShop.shopAddress;
				list.get(i).shopId = LastShop.shopId;
				ChooseMyPets.add(list.get(i));
			}
		}
		return ChooseMyPets;
	}
	private String getPetID_ServiceId_Mypet_ServiceType_PetKind_ItemIds(){
		ArrayList<MyPet> MyChoosePets = getChoosePets(listMyPets);
		if (MyChoosePets.size()>0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < MyChoosePets.size(); i++) {
				MyPet myPet = MyChoosePets.get(i);
				if (i < MyChoosePets.size() - 1) {
					sb.append(myPet.petId);
					sb.append("_");
					sb.append("0");
					sb.append("_");
					sb.append(myPet.myPetId);
					sb.append("_");
					sb.append(serviceType);
					sb.append("_");
					sb.append(0);
					sb.append("_");
					sb.append("0");
					sb.append("_");
				}else {
					sb.append(myPet.petId);
					sb.append("_");
					sb.append("0");
					sb.append("_");
					sb.append(myPet.myPetId);
					sb.append("_");
					sb.append(serviceType);
					sb.append("_");
					sb.append("0");
					sb.append("_");
					sb.append("0");
				}
			}
			return sb.toString();
		}else {
			return null;
		}
		
	}
	private void showAddressNumZeroDialog(){
		MDialog mDialog = new MDialog.Builder(mContext)
//		.setTitle("先填写地址我们会帮您推荐附近店铺哦")
		.setType(MDialog.DIALOGTYPE_ALERT)
		.setMessage("先填写地址我们会帮您推荐附近店铺哦")
		.setCancelable(false)
		.setOKStr("去填写地址")
		.positiveListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goNext(AddServiceAddressActivity.class, Global.SERVICE_NEW_TO_ADD_ADDRESS_REQUESTCODE, Global.SERVICE_NEW_TO_ADD_ADDRESS);
			}
		}).build();
		mDialog.show();
	}
	
	
	
	private void goAppoint(Class cls){
		Intent intent = new Intent(mContext, cls);
		if (getChoosePets(listMyPets).size()<=0) {
			ToastUtil.showToastShortCenter(mContext, "请选择您的宠物");
			return;
		}
		intent.putParcelableArrayListExtra("myPets", getChoosePets(listMyPets));
		intent.putExtra("beautician", beautician);
		intent.putExtra("lat", lat);
		intent.putExtra("lng", lng);
		intent.putExtra("shopLat", LastShop.shoplat);
		intent.putExtra("shopLng", LastShop.shoplng);
		Utils.mLogError("==-->shlat "+lat+"    lng "+lng);
		startActivity(intent);
	}
	private void goNext(Class cls,int requestCode,int previous){
		Intent intent = new Intent(mContext, cls);
		intent.putExtra("previous", previous);
		intent.putExtra("strp_long", getPetID_ServiceId_Mypet_ServiceType_PetKind_ItemIds());//门店列表用
		intent.putExtra("lat", LastShop.shoplat);//门店列表用
		intent.putExtra("lng", LastShop.shoplng);//门店列表用
		intent.putExtra("addrid", addressId);
		startActivityForResult(intent, requestCode);
	}
	private void goNextUnBack(Class cls,int previous){
		Intent intent = new Intent(mContext, cls);
		intent.putExtra("previous", previous);
		intent.putExtra("url", buyCardUrl);
		startActivity(intent);
	}
	
	private void getServiceType() {
		if (!mPDialog.isShowing()) {
			mPDialog.showDialog();
			mPDialog.setCancelable(false);
		}
		if (shopId<=0) {
			CommUtil.queryServiceDetail(mContext, serviceType,myPetIds,addressId,workerId,shopIdNet,ServiceHandler);
		}else {
			CommUtil.queryServiceDetail(mContext, serviceType,myPetIds,addressId,workerId,shopId,ServiceHandler);
		}
	}
	private AsyncHttpResponseHandler ServiceHandler = new AsyncHttpResponseHandler() {
		
		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
			mPDialog.closeDialog();
			try {
				JSONObject object = new JSONObject(new String(responseBody));
				int code = object.getInt("code");
				if (code == 0 ) {
					if (object.has("data")&&!object.isNull("data")) {
							JSONObject obJsonObject = object.getJSONObject("data");
								if (obJsonObject.has("shop")&&!obJsonObject.isNull("shop")) {
									JSONObject objectShop  = obJsonObject.getJSONObject("shop");
									if (objectShop.has("shopId")&&!objectShop.isNull("shopId")) {
										shopId = objectShop.getInt("shopId");
										now.shopId = objectShop.getInt("shopId");
									}
									if (objectShop.has("shopName")&&!objectShop.isNull("shopName")) {
										shopName = objectShop.getString("shopName");
										now.shopName =  objectShop.getString("shopName");
									}
									if (objectShop.has("address")&&!objectShop.isNull("address")) {
										shopAddress = objectShop.getString("address");
										now.shopAddress =  objectShop.getString("address");
									}
									if (objectShop.has("lat")&&!objectShop.isNull("lat")) {
										shopLat = objectShop.getDouble("lat");
										now.shoplat=objectShop.getDouble("lat");
									}
									if (objectShop.has("lng")&&!objectShop.isNull("lng")) {
										shopLng = objectShop.getDouble("lng");
										now.shoplng=objectShop.getDouble("lng");
									}
								}
								if (addressId<=0) {
									LastShop.shopId = now.shopId;
									LastShop.shopAddress = now.shopAddress;
									LastShop.shoplat = now.shoplat;
									LastShop.shoplng = now.shoplng;
									LastShop.shopName = now.shopName;
									shop_address_detail.setText(LastShop.shopName);
								}
								if (workerId>0) {
									LastShop.shopId = now.shopId;
									LastShop.shopAddress = now.shopAddress;
									LastShop.shoplat = now.shoplat;
									LastShop.shoplng = now.shoplng;
									LastShop.shopName = now.shopName;
									shop_address_detail.setText(LastShop.shopName);
									shop_address_right_img.setVisibility(View.GONE);
								}
						listServiceType.clear();
						if (obJsonObject.has("services")&&!obJsonObject.isNull("services")) {
							JSONArray array = obJsonObject.getJSONArray("services");
							if (array.length()>0) {
								for (int i = 0; i < array.length(); i++) {
									listServiceType.add(ServiceType.json2Entity(array.getJSONObject(i)));
								}
							}
							if (listServiceType.size()>0) {
								mListview_service_type.setVisibility(View.VISIBLE);
								typeAdapter.notifyDataSetChanged();
							}else {
								mListview_service_type.setVisibility(View.GONE);
							}
						}
					}
				}else {
					ToastUtil.showToastShort(mContext, object.getString("msg"));
				}
				if (!Utils.checkLogin(act)) {
					shop_address_detail.setText(shopTag);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Utils.mLogError(e.getMessage()+" "+e.getCause());
			}
		}
		
		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			mPDialog.closeDialog();
		}
	};
	private void getServiceType2() {
		if (!mPDialog.isShowing()) {
			mPDialog.showDialog();
			mPDialog.setCancelable(false);
			mPDialog.setCanceledOnTouchOutside(false);
		}
		Utils.mLogError("==-->111111  shopid "+LastShop.shopId+" 222222 "+now.shopId+" 22222 "+shopId);
		if (shopId<=0) {
			CommUtil.queryServiceDetail(mContext, serviceType,myPetIds,addressId,workerId,shopIdNet,ServiceHandler2);
		}else {
			CommUtil.queryServiceDetail(mContext, serviceType,myPetIds,addressId,workerId,shopId,ServiceHandler2);
		}
	}
	private AsyncHttpResponseHandler ServiceHandler2 = new AsyncHttpResponseHandler() {
		
		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
			try {
				JSONObject object = new JSONObject(new String(responseBody));
				int code = object.getInt("code");
				if (code == 0 ) {
					if (object.has("data")&&!object.isNull("data")) {
						JSONObject obJsonObject = object.getJSONObject("data");
							if (obJsonObject.has("shop")&&!obJsonObject.isNull("shop")) {
								JSONObject objectShop  = obJsonObject.getJSONObject("shop");
								if (objectShop.has("shopId")&&!objectShop.isNull("shopId")) {
									now.shopId = objectShop.getInt("shopId");
								}
								if (objectShop.has("shopName")&&!objectShop.isNull("shopName")) {
									now.shopName = objectShop.getString("shopName");
								}
								if (objectShop.has("address")&&!objectShop.isNull("address")) {
									now.shopAddress = objectShop.getString("address");
								}
								if (objectShop.has("lat")&&!objectShop.isNull("lat")) {
									now.shoplat = objectShop.getDouble("lat");
								}
								if (objectShop.has("lng")&&!objectShop.isNull("lng")) {
									now.shoplng = objectShop.getDouble("lng");
								}
							}
						Utils.mLogError("==-->111111  shopid "+LastShop.shopId+" shopName "+LastShop.shopName +" 33333 "+now.shopId+" 33333 "+shopId);
						listServiceType.clear();
						if (obJsonObject.has("services")&&!obJsonObject.isNull("services")) {
							JSONArray array = obJsonObject.getJSONArray("services");
							if (array.length()>0) {
								for (int i = 0; i < array.length(); i++) {
									listServiceType.add(ServiceType.json2Entity(array.getJSONObject(i)));
								}
							}
							if (listServiceType.size()>0) {
								mListview_service_type.setVisibility(View.VISIBLE);
								typeAdapter.notifyDataSetChanged();
							}else {
								mListview_service_type.setVisibility(View.GONE);
							}
						}
					}
					Utils.mLogError("==-->111111  shopid "+LastShop.shopId+" shopName "+LastShop.shopName +" 44444 "+now.shopId+" 44444 "+shopId);
					if (isCancle) {
						isCancle = false;
					}else {
						if (LastShop.shopId!=now.shopId) {
							showCancleDetail(null,LastShop,now);
							try {
								Utils.setAttribute(mContext, pWinCancle);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else {
							LastShop.shopId = now.shopId;
							LastShop.shopAddress = now.shopAddress;
							LastShop.shoplat = now.shoplat;
							LastShop.shoplng = now.shoplng;
							LastShop.shopName = now.shopName;
							shopId = LastShop.shopId;
							shop_address_detail.setText(LastShop.shopName);
						}
						
					}
				}else {
					ToastUtil.showToastShort(mContext, object.getString("msg"));
				}
				isGoing = false;
				mPDialog.closeDialog();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Utils.mLogError(e.getMessage()+" "+e.getCause());
				isGoing = false;
			}
		}
		
		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			mPDialog.closeDialog();
			isGoing = false;
		}
	};
	
	
	
	private PopupWindow pWinCancle;
	private void showCancleDetail(final Intent data,final ServiceShopAdd last,final ServiceShopAdd now) {
		pWinCancle = null;
		if (pWinCancle == null) {
			View view = LayoutInflater.from(mContext).inflate(R.layout.mdialog, null);
			TextView tv_dialog_message=	(TextView) view.findViewById(R.id.tv_dialog_message);
			Button bt_dialog_cancel=	(Button) view.findViewById(R.id.bt_dialog_cancel);
			Button bt_dialog_ok=	(Button) view.findViewById(R.id.bt_dialog_ok);
			pWinCancle = new PopupWindow(view,
					android.view.ViewGroup.LayoutParams.MATCH_PARENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT, true);
			pWinCancle.setOutsideTouchable(false);  
			pWinCancle.setFocusable(true);
			bt_dialog_cancel.setText("取消");
			bt_dialog_ok.setText("确定");
			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			pWinCancle.setWidth(dm.widthPixels - 80);
			tv_dialog_message.setText("阿宠发现有距您更近的门店，确定切换到该门店？");
			pWinCancle.showAtLocation(view, Gravity.CENTER_VERTICAL, 0, 0);		
			pWinCancle.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss() {
					try {
						if (pWinCancle.isShowing()) {
							pWinCancle.dismiss();
							pWinCancle = null;
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						Utils.onDismiss(mContext);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
						lp.alpha = 1f;
						mContext.getWindow().setAttributes(lp);
						getWindow().setAttributes(lp);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			bt_dialog_cancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (data!=null) {
						shopId = data.getIntExtra("shopid", 0);
						shopName = data.getStringExtra("shopname");
						shopAddress = data.getStringExtra("shopaddr");
						shopLat = data.getDoubleExtra("lat", 0);
						shopLng = data.getDoubleExtra("lng", 0);
						LastShop.shopId = shopId;
						LastShop.shopName=shopName;
						LastShop.shoplat = shopLat;
						LastShop.shoplng=shopLng;
						LastShop.shopAddress = shopAddress;
					}
					Utils.mLogError("==-->111111  shopid "+LastShop.shopId+" 55555 "+now.shopId+" 55555 "+shopId);
					shop_address_detail.setText(LastShop.shopName);
					isCancle = true;
					getServiceType2();
					if (pWinCancle!=null) {
						if (pWinCancle.isShowing()) {
							pWinCancle.dismiss();
							pWinCancle = null;
						}
						try {
							Utils.onDismiss(mContext);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			bt_dialog_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					LastShop.shopId = now.shopId;
					LastShop.shopAddress = now.shopAddress;
					LastShop.shoplat = now.shoplat;
					LastShop.shoplng = now.shoplng;
					LastShop.shopName = now.shopName;
					shopId = LastShop.shopId;
					Utils.mLogError("==-->111111  shopid "+LastShop.shopId+" 66666 "+now.shopId+" 66666 "+shopId);
					shop_address_detail.setText(LastShop.shopName);
					getServiceType2();
					if (pWinCancle!=null) {
						if (pWinCancle.isShowing()) {
							pWinCancle.dismiss();
							pWinCancle = null;
						}
						try {
							Utils.onDismiss(mContext);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Global.RESULT_OK) {
			if (requestCode==Global.SERVICE_NEW_TO_LOGIN) {
				getPetData();
			}else if (requestCode==Global.SERVICE_NEW_TO_CHOOSE_SHOPLIST) {
				if (data.getIntExtra("shopid", 0)!=now.shopId) {
					showCancleDetail(data,LastShop,now);
					try {
						Utils.setAttribute(mContext, pWinCancle);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else {
					shopId = data.getIntExtra("shopid", 0);
					shopName = data.getStringExtra("shopname");
					shopAddress = data.getStringExtra("shopaddr");
					shopLat = data.getDoubleExtra("lat", 0);
					shopLng = data.getDoubleExtra("lng", 0);
					LastShop.shopId = shopId;
					LastShop.shopName=shopName;
					LastShop.shoplat = shopLat;
					LastShop.shoplng=shopLng;
					LastShop.shopAddress = shopAddress;
					shop_address_detail.setText(LastShop.shopName);
					getServiceType2();
				}
			}else if (requestCode==Global.SERVICE_NEW_TO_ADD_PET_REQUESTCODE) {
				getPetData();
			}else if (requestCode==Global.SERVICE_NEW_TO_ADD_ADDRESS_REQUESTCODE) {
				address = data.getStringExtra("addr");
				lat = data.getDoubleExtra("addr_lat", 0);
				lng = data.getDoubleExtra("addr_lng",0);
				addressId = data.getIntExtra("addr_id", 0);
				address_detail.setText(address);
				shopId = LastShop.shopId;
				Utils.mLogError("==-->111111  shopid "+LastShop.shopId+" 111111111 "+now.shopId+" 1111 "+shopId);
				if (workerId>0) {
					getServiceType();
				}else {
					getServiceType2();
				}
			}else if (requestCode==Global.SERVICE_NEW_TO_CHOOSEADDRESS) {
				address = data.getStringExtra("addr");
				lat = data.getDoubleExtra("addr_lat", 0);
				lng = data.getDoubleExtra("addr_lng",0);
				addressId = data.getIntExtra("addr_id", 0);
				address_detail.setText(address);
				shopId = LastShop.shopId;
//				Utils.mLogError("==-->111111  shopid "+LastShop.shopId+" 111111111 "+now.shopId+" 1111 "+shopId);
				if (workerId>0) {
					getServiceType();
				}else {
					getServiceType2();
				}
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_titlebar_back:
			finish();
			break;
		case R.id.tv_titlebar_other:
			if (!Utils.checkLogin(mContext)) {
				goNext(LoginActivity.class, Global.SERVICE_NEW_TO_LOGIN, Global.SERVICE_NEW_TO_LOGIN);
				return;
			}
			if (listMyPets.size()==20) {
				ToastUtil.showToastShortCenter(mContext, "最多支持添加20只宝贝呦");
			}else {
				UmengStatistics.UmengEventStatistics(mContext,Global.UmengEventID.click_AddPet);
				choosePets.clear();
				choosePets = getChoosePets(listMyPets);
				goNext(PetAddActivity.class, Global.SERVICE_NEW_TO_ADD_PET_REQUESTCODE, Global.SERVICE_NEW_TO_ADD_PET);
			}
			break;
		case R.id.layout_un_login_show:
			if (!Utils.checkLogin(mContext)) {
				goNext(LoginActivity.class, Global.SERVICE_NEW_TO_LOGIN, Global.SERVICE_NEW_TO_LOGIN);
			}else {
				if (listMyPets.size()<=0) {
					UmengStatistics.UmengEventStatistics(mContext,Global.UmengEventID.click_AddPet);
					goNext(PetAddActivity.class, Global.SERVICE_NEW_TO_ADD_PET_REQUESTCODE, Global.SERVICE_NEW_TO_ADD_PET);
				}
			}
			break;
		case R.id.textview_go_buy_cards:
			UmengStatistics.UmengEventStatistics(mContext,Global.UmengEventID.click_Purchase);
			if (!Utils.checkLogin(mContext)) {
				goNext(LoginActivity.class, Global.SERVICE_NEW_TO_LOGIN, Global.SERVICE_NEW_TO_LOGIN);
				return;
			}
			if (buyCardUrl!=null&&!TextUtils.isEmpty(buyCardUrl)) {
				goNextUnBack(ADActivity.class, Global.SERVICE_NEW_TO_BUYCARD);
			}
			break;
		case R.id.relay_choose_shop:
		case R.id.shop_address_right_img:
			if (Utils.checkLogin(mContext)) {
				if (addressAmount<=0) {
					showAddressNumZeroDialog();
				}else {
					goNext(ShopListActivity.class, Global.SERVICE_NEW_TO_CHOOSE_SHOPLIST,Global.SERVICE_NEW_TO_CHOOSE_SHOPLIST);
				}
			}else {
				goNext(ShopListActivity.class, Global.SERVICE_NEW_TO_CHOOSE_SHOPLIST,Global.SERVICE_NEW_TO_CHOOSE_SHOPLIST);
			}
			break;
		case R.id.address_right_img:
		case R.id.relay_choose_address:
			UmengStatistics.UmengEventStatistics(mContext,Global.UmengEventID.click_SelectPetAddress);
			if (addressAmount<=0) {
				goNext(AddServiceAddressActivity.class, Global.SERVICE_NEW_TO_ADD_ADDRESS_REQUESTCODE, Global.SERVICE_NEW_TO_ADD_ADDRESS);
			}else {
				goNext(CommonAddressActivity.class, Global.SERVICE_NEW_TO_CHOOSEADDRESS, Global.BOOKINGSERVICEREQUESTCODE_ADDR);
			}
			break;
		default:
			break;
		}
	}
}
