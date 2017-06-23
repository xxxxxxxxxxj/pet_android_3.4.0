package com.haotang.pet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.ArrayMap;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haotang.base.SuperActivity;
import com.haotang.pet.adapter.AppointMentNewBeauAdapter;
import com.haotang.pet.adapter.AppointMentNewDialogPetAdapter;
import com.haotang.pet.adapter.AppointMentNewExtraItemsAdapter;
import com.haotang.pet.adapter.AppointMentNewExtraItemsAdapter.OnItemNewExtraItemsClickListener;
import com.haotang.pet.adapter.AppointMentNewLevelAdapter;
import com.haotang.pet.adapter.AppointMentNewLevelAdapter.OnItemNewLevelClickListener;
import com.haotang.pet.adapter.AppointMentNewLevelAdapter.OnNewLevelWenHaoClickListener;
import com.haotang.pet.adapter.AppointMentNewPetAdapter;
import com.haotang.pet.adapter.AppointMentNewServiceCardAdapter;
import com.haotang.pet.adapter.AppointNewChangeBeautyDialogPetAdapter;
import com.haotang.pet.entity.AppointMentNewPetInfo;
import com.haotang.pet.entity.AppointNewCard;
import com.haotang.pet.entity.AppointNewWorker;
import com.haotang.pet.entity.AppointNewWorker.DataBean;
import com.haotang.pet.entity.AppointNewWorker.DataBean.ServicesBean;
import com.haotang.pet.entity.AppointNewWorker.DataBean.ServicesBean.LevelsBean;
import com.haotang.pet.entity.AppointNewWorker.DataBean.WorkersBean;
import com.haotang.pet.entity.AppointNewWorker.DataBean.WorkersBean.LevelBean;
import com.haotang.pet.entity.AppointNewWorker.DataBean.WorkersBean.Workers;
import com.haotang.pet.entity.AppointNewWorker.DataBean.WorkersBean.Workers.PricesBean;
import com.haotang.pet.entity.Beautician;
import com.haotang.pet.entity.CardItems;
import com.haotang.pet.entity.CardsConfirmOrder;
import com.haotang.pet.entity.ExtraItem;
import com.haotang.pet.entity.MyPet;
import com.haotang.pet.entity.Strp;
import com.haotang.pet.net.AsyncHttpResponseHandler;
import com.haotang.pet.util.CommUtil;
import com.haotang.pet.util.Global;
import com.haotang.pet.util.ToastUtil;
import com.haotang.pet.util.UmengStatistics;
import com.haotang.pet.util.Utils;
import com.haotang.pet.view.AlertDialogListNavAndPost;
import com.haotang.pet.view.HorizontalListView;
import com.haotang.pet.view.MListview;
import com.haotang.pet.view.MarqueeView;
import com.haotang.pet.view.MyGridView;

/**
 * <p>
 * Title:AppointMentNewActivity
 * </p>
 * <p>
 * Description:洗美预约新界面
 * </p>
 * <p>
 * Company:北京昊唐科技有限公司
 * </p>
 * 
 * @author 徐俊
 * @date 2017-6-7 下午7:34:22
 */
public class AppointMentNewActivity extends SuperActivity implements
		OnClickListener {
	public static AppointMentNewActivity act;
	private ImageButton ibBack;
	private TextView tvTitle;
	private Button bt_titlebar_other;
	private RelativeLayout rl_appointmentnew_servicecard;
	private TextView tv_appointmentnew_servicecard;
	private TextView tv_appointmentnew_servicecarddes;
	private TextView tv_appointmentnew_payprice;
	private TextView tv_appointmentnew_paydes;
	private MListview mlv_appointmentnew_pet;
	private RelativeLayout rl_appointmentnew_servicetype;
	private TextView tv_appointmentnew_servicetype;
	private RelativeLayout rl_appointmentnew_choose_time;
	private TextView tv_appointmentnew_yuyuetime;
	private HorizontalListView hlv_appointmentnew_beautician;
	private MyGridView mgv_appointmentnew_addservice;
	private MarqueeView mv_appointmentnew_verticalbanner1;
	private HorizontalListView hlv_appointmentnew_servicecard;
	private int serviceType;
	private int serviceLoc;
	private String petAddress;
	private String shopName;
	private String appointment;
	private int shopId;
	private String workerIds;
	private int level = 1;
	private ArrayList<MyPet> myPets;
	private AppointMentNewPetAdapter<AppointMentNewPetInfo> appointMentNewPetAdapter;
	private AppointMentNewBeauAdapter<Workers> appointMentNewBeauAdapter;
	private AppointMentNewExtraItemsAdapter<ExtraItem> appointMentNewExtraItemsAdapter;
	private AppointMentNewServiceCardAdapter<AppointNewCard> appointMentNewServiceCardAdapter;
	private List<Strp> strpList = new ArrayList<Strp>();
	private List<AppointMentNewPetInfo> appointMentNewPetInfoList = new ArrayList<AppointMentNewPetInfo>();
	private List<ExtraItem> extraItemList = new ArrayList<ExtraItem>();
	private MyGridView mgv_appointmentnew_level;
	private List<LevelsBean> levelList = new ArrayList<LevelsBean>();
	private AppointMentNewLevelAdapter<LevelsBean> appointMentNewLevelAdapter;
	private List<Workers> workersList = new ArrayList<Workers>();
	private List<AppointNewCard> cardList = new ArrayList<AppointNewCard>();
	private List<String> noticesList = new ArrayList<String>();
	private AppointMentNewDialogPetAdapter<AppointMentNewPetInfo> appointMentNewDialogPetAdapter;
	private LinearLayout ll_appointmentnew_addservices;
	private LinearLayout ll_appointmentnew_servicecard;
	private int workerId;
	private ArrayList<CardsConfirmOrder> cardsConfirmOrders = new ArrayList<CardsConfirmOrder>();
	private double lat = 0;
	private double lng = 0;
	private int position;
	private int cardAmount;
	private boolean isLevelAndWorker;
	private ScrollView slv_appointmentnew;
	private double lastPrice = 0;
	private double favorablePrice = 0;
	private ArrayList<Integer> listIds = new ArrayList<Integer>();
	private String CardTag = "";
	private List<WorkersBean> workers;
	private List<ServicesBean> services;
	private AppointNewChangeBeautyDialogPetAdapter<AppointMentNewPetInfo> appointNewChangeBeautyDialogPetAdapter;
	private String levelName;
	private Beautician beautician;
	private ArrayMap<Integer, ArrayList<Workers>> workersMap = new ArrayMap<Integer, ArrayList<Workers>>();
	private MyReceiver receiver;
	private PopupWindow pWin;
	protected boolean isClear;
	private AppointMentNewPetInfo beforeAppointMentNewPetInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
		initView();
		setView();
		setLinster();
		initReceiver();
		getData();
	}

	private void initReceiver() {
		receiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.AppointMentNewActivity");// 次卡购买成功
		filter.addAction("android.intent.action.AppointMentNewPetExtraItemAdapter");// 删除单项
		registerReceiver(receiver, filter);
	}

	private class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			int previous = bundle.getInt("previous");
			int itemId = bundle.getInt("itemId");
			int myPetId = bundle.getInt("myPetId");
			if (previous == Global.CARDSDETAIL_NEW_TO_BUT_CARD) {// 购买次卡成功返回
				// 请求数据
				getWorkers();
				getSingles();
			} else if (previous == Global.APPOINTNEW_DELETE_EXTRAITEM) {// 删除单项
				if (appointMentNewPetInfoList != null
						&& appointMentNewPetInfoList.size() > 0) {
					List<ExtraItem> localExtraItems = new ArrayList<ExtraItem>();
					localExtraItems.clear();
					for (int i = 0; i < appointMentNewPetInfoList.size(); i++) {
						AppointMentNewPetInfo appointMentNewPetInfo = appointMentNewPetInfoList
								.get(i);
						if (appointMentNewPetInfo != null) {
							if (appointMentNewPetInfo.getMyPetId() == myPetId) {
								List<ExtraItem> extraItems = appointMentNewPetInfo
										.getExtraItems();
								if (extraItems != null && extraItems.size() > 0) {
									localExtraItems.addAll(extraItems);
									for (int j = 0; j < extraItems.size(); j++) {
										ExtraItem extraItem = extraItems.get(j);
										if (extraItem != null) {
											if (extraItem.getItemId() == itemId) {
												localExtraItems.remove(j);
												appointMentNewPetInfo
														.setExtraItems(localExtraItems);
												appointMentNewPetAdapter
														.notifyDataSetChanged();
												setLastPrice();
												break;
											}
										}
									}
								}
							}
						}
					}
					for (int i = 0; i < appointMentNewPetInfoList.size(); i++) {
						AppointMentNewPetInfo appointMentNewPetInfo = appointMentNewPetInfoList
								.get(i);
						for (int j = 0; j < myPets.size(); j++) {
							MyPet myPet = myPets.get(j);
							if (appointMentNewPetInfo.getMyPetId() == myPet.myPetId) {
								myPet.setExtraItems(appointMentNewPetInfo
										.getExtraItems());
							}
						}
					}
				}
				appointMentNewExtraItemsAdapter
						.setPetData(appointMentNewPetInfoList);
			}
		}
	}

	private void getData() {
		getWorkers();
		getSingles();
	}

	// 加载次卡
	private void getNotice() {
		cardsConfirmOrders.clear();
		mPDialog.showDialog();
		CommUtil.confirmOrderPrompt(spUtil.getString("cellphone", ""), this, 1,
				serviceLoc, getPetID_ServiceIdDouHao(), workerId, level,
				appointment, null, confirmOrderPromptHandler);
	}

	private AsyncHttpResponseHandler confirmOrderPromptHandler = new AsyncHttpResponseHandler() {

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] responseBody) {
			mPDialog.closeDialog();
			try {
				JSONObject object = new JSONObject(new String(responseBody));
				int code = object.getInt("code");
				String msg = object.getString("msg");
				if (code == 0) {
					if (object.has("data") && !object.isNull("data")) {
						JSONObject objectData = object.getJSONObject("data");
						if (objectData.has("reductionTag")
								&& !objectData.isNull("reductionTag")) {
							JSONObject objectReductionTag = objectData
									.getJSONObject("reductionTag");
							if (objectReductionTag.has("cardTag")
									&& !objectReductionTag.isNull("cardTag")) {
								CardTag = objectReductionTag
										.getString("cardTag");
							}
						}
						if (objectData.has("cardAmount")
								&& !objectData.isNull("cardAmount")) {
							cardAmount = objectData.getInt("cardAmount");
						}
						if (objectData.has("cards")
								&& !objectData.isNull("cards")) {
							JSONArray arrayCards = objectData
									.getJSONArray("cards");
							if (arrayCards.length() > 0) {
								cardsConfirmOrders.clear();
								for (int i = 0; i < arrayCards.length(); i++) {
									CardsConfirmOrder confirmOrder = new CardsConfirmOrder();
									JSONObject objectEveryCards = arrayCards
											.getJSONObject(i);
									if (objectEveryCards.has("discountImg")
											&& !objectEveryCards
													.isNull("discountImg")) {
										confirmOrder.discountImg = objectEveryCards
												.getString("discountImg");
									}
									if (objectEveryCards.has("cardName")
											&& !objectEveryCards
													.isNull("cardName")) {
										confirmOrder.cardName = objectEveryCards
												.getString("cardName");
									}
									if (objectEveryCards.has("cardBgImg")
											&& !objectEveryCards
													.isNull("cardBgImg")) {
										confirmOrder.cardBgImg = objectEveryCards
												.getString("cardBgImg");
									}
									if (objectEveryCards.has("tip")
											&& !objectEveryCards.isNull("tip")) {
										confirmOrder.tip = objectEveryCards
												.getString("tip");
									}
									if (objectEveryCards.has("id")
											&& !objectEveryCards.isNull("id")) {
										confirmOrder.id = objectEveryCards
												.getInt("id");
									}
									confirmOrder.isChoose = 0;
									if (objectEveryCards.has("petId")
											&& !objectEveryCards
													.isNull("petId")) {
										confirmOrder.petId = objectEveryCards
												.getInt("petId");
									}
									if (objectEveryCards.has("items")
											&& !objectEveryCards
													.isNull("items")) {
										JSONArray arrayItems = objectEveryCards
												.getJSONArray("items");
										if (arrayItems.length() > 0) {
											for (int j = 0; j < arrayItems
													.length(); j++) {
												CardItems cardItems = new CardItems();
												JSONObject objectEva = arrayItems
														.getJSONObject(j);
												cardItems.petId = confirmOrder.petId;
												if (objectEva.has("id")
														&& !objectEva
																.isNull("id")) {
													cardItems.id = objectEva
															.getInt("id");
												}
												if (objectEva.has("count")
														&& !objectEva
																.isNull("count")) {
													cardItems.count = objectEva
															.getInt("count");
													cardItems.oldCount = objectEva
															.getInt("count");
												}
												if (objectEva.has("level")
														&& !objectEva
																.isNull("level")) {
													cardItems.level = objectEva
															.getInt("level");
												}
												if (objectEva.has("title")
														&& !objectEva
																.isNull("title")) {
													cardItems.title = objectEva
															.getString("title");
												}
												if (objectEva.has("countTag")
														&& !objectEva
																.isNull("countTag")) {
													cardItems.countTag = objectEva
															.getString("countTag");
												}
												if (objectEva
														.has("serviceType")
														&& !objectEva
																.isNull("serviceType")) {
													cardItems.serviceType = objectEva
															.getInt("serviceType");
												}
												confirmOrder.arrayList
														.add(cardItems);
											}
										}
									}
									cardsConfirmOrders.add(confirmOrder);
								}
							}
						}
					}
				} else {
					if (Utils.isStrNull(msg)) {
						ToastUtil.showToastShortBottom(
								AppointMentNewActivity.this, msg);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("TAG", "getServiceCards()数据异常e = " + e.toString());
				ToastUtil.showToastShortBottom(AppointMentNewActivity.this,
						"数据异常");
			}
			setCardTips();
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			ToastUtil.showToastShortBottom(AppointMentNewActivity.this, "请求失败");
			mPDialog.closeDialog();
		}
	};

	// 加载可购买次卡
	private void getServiceCards() {
		mPDialog.showDialog();
		CommUtil.queryAvailableCards(this, getPetID_ServiceId(), level,
				queryAvailableCardsHandler);
	}

	@Override
	public void onStart() {
		super.onStart();
		mv_appointmentnew_verticalbanner1.startFlipping();
	}

	@Override
	public void onStop() {
		super.onStop();
		mv_appointmentnew_verticalbanner1.stopFlipping();
	}

	private AsyncHttpResponseHandler queryAvailableCardsHandler = new AsyncHttpResponseHandler() {

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] responseBody) {
			mPDialog.closeDialog();
			try {
				JSONObject jsonObject = new JSONObject(new String(responseBody));
				int code = jsonObject.getInt("code");
				String msg = jsonObject.getString("msg");
				if (code == 0) {
					if (jsonObject.has("data") && !jsonObject.isNull("data")) {
						JSONObject jdata = jsonObject.getJSONObject("data");
						if (jdata.has("cards") && !jdata.isNull("cards")) {
							JSONArray jarrcards = jdata.getJSONArray("cards");
							if (jarrcards != null && jarrcards.length() > 0) {
								cardList.clear();
								for (int i = 0; i < jarrcards.length(); i++) {
									cardList.add(AppointNewCard
											.json2Entity(jarrcards
													.getJSONObject(i)));
								}
								appointMentNewServiceCardAdapter
										.notifyDataSetChanged();
							}
						}
						if (jdata.has("notices") && !jdata.isNull("notices")) {
							JSONArray jarrnotices = jdata
									.getJSONArray("notices");
							if (jarrnotices != null && jarrnotices.length() > 0) {
								noticesList.clear();
								for (int i = 0; i < jarrnotices.length(); i++) {
									noticesList.add(jarrnotices.getString(i));
								}
								mv_appointmentnew_verticalbanner1
										.startWithList(noticesList);
								mv_appointmentnew_verticalbanner1.start();
							}
						}
					}
				} else {
					if (Utils.isStrNull(msg)) {
						ToastUtil.showToastShortBottom(
								AppointMentNewActivity.this, msg);
					}
				}
			} catch (Exception e) {
				Log.e("TAG", "getServiceCards()数据异常e = " + e.toString());
				ToastUtil.showToastShortBottom(AppointMentNewActivity.this,
						"数据异常");
				e.printStackTrace();
			}
			if (cardList.size() <= 0 && noticesList.size() <= 0) {
				ll_appointmentnew_servicecard.setVisibility(View.GONE);
			} else if (cardList.size() <= 0 && noticesList.size() > 0) {
				ll_appointmentnew_servicecard.setVisibility(View.VISIBLE);
				mv_appointmentnew_verticalbanner1.setVisibility(View.VISIBLE);
				hlv_appointmentnew_servicecard.setVisibility(View.GONE);
			} else if (cardList.size() > 0 && noticesList.size() <= 0) {
				ll_appointmentnew_servicecard.setVisibility(View.VISIBLE);
				mv_appointmentnew_verticalbanner1.setVisibility(View.GONE);
				hlv_appointmentnew_servicecard.setVisibility(View.VISIBLE);
			} else if (cardList.size() > 0 && noticesList.size() > 0) {
				ll_appointmentnew_servicecard.setVisibility(View.VISIBLE);
				mv_appointmentnew_verticalbanner1.setVisibility(View.VISIBLE);
				hlv_appointmentnew_servicecard.setVisibility(View.VISIBLE);
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			ToastUtil.showToastShortBottom(AppointMentNewActivity.this, "请求失败");
			mPDialog.closeDialog();
		}
	};

	// 加载单项
	private void getSingles() {
		mPDialog.showDialog();
		CommUtil.queryExtraItems(this, getMyPetID_ServiceId(),
				queryExtraItemsHandler);
	}

	private AsyncHttpResponseHandler queryExtraItemsHandler = new AsyncHttpResponseHandler() {

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] responseBody) {
			mPDialog.closeDialog();
			try {
				JSONObject jsonObject = new JSONObject(new String(responseBody));
				int code = jsonObject.getInt("code");
				String msg = jsonObject.getString("msg");
				if (code == 0) {
					if (jsonObject.has("data") && !jsonObject.isNull("data")) {
						JSONObject jdata = jsonObject.getJSONObject("data");
						if (jdata.has("items") && !jdata.isNull("items")) {
							JSONArray jsonArray = jdata.getJSONArray("items");
							if (jsonArray != null && jsonArray.length() > 0) {
								extraItemList.clear();
								for (int i = 0; i < jsonArray.length(); i++) {
									extraItemList.add(ExtraItem
											.json2Entity(jsonArray
													.getJSONObject(i)));
								}
								appointMentNewExtraItemsAdapter
										.notifyDataSetChanged();
								ll_appointmentnew_addservices
										.setVisibility(View.VISIBLE);
								// 设置单项的AvailablePets,防止从换服务界面带过来的单项没有AvailablePets
								for (int j = 0; j < extraItemList.size(); j++) {
									ExtraItem extraItem = extraItemList.get(j);
									for (int k = 0; k < appointMentNewPetInfoList
											.size(); k++) {
										AppointMentNewPetInfo appointMentNewPetInfo = appointMentNewPetInfoList
												.get(k);
										List<ExtraItem> extraItems = appointMentNewPetInfo
												.getExtraItems();
										if (extraItems != null
												&& extraItems.size() > 0) {
											for (int l = 0; l < extraItems
													.size(); l++) {
												ExtraItem extraItem2 = extraItems
														.get(l);
												if (extraItem2.getItemId() == extraItem
														.getItemId()) {
													if (Utils
															.isStrNull(extraItem
																	.getAvailablePets())) {
														extraItem2
																.setAvailablePets(extraItem
																		.getAvailablePets());
													}
												}
											}
										}
									}
								}
								appointMentNewPetAdapter.notifyDataSetChanged();
							} else {
								ll_appointmentnew_addservices
										.setVisibility(View.GONE);
							}
						}
					}
				} else {
					if (Utils.isStrNull(msg)) {
						ToastUtil.showToastShortBottom(
								AppointMentNewActivity.this, msg);
					}
				}
			} catch (Exception e) {
				Log.e("TAG", "getSingles()数据异常e = " + e.toString());
				ToastUtil.showToastShortBottom(AppointMentNewActivity.this,
						"数据异常");
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			ToastUtil.showToastShortBottom(AppointMentNewActivity.this, "请求失败");
			mPDialog.closeDialog();
		}
	};
	private int source;// 预约渠道（1:指定时间、2:指定美容师）

	// 加载美容师
	private void getWorkers() {
		mPDialog.showDialog();
		if (beautician != null) {
			source = 2;
			CommUtil.querySelectedWorkers(this, appointment, serviceLoc,
					shopId, getPetID_ServiceId_ItemIds(),
					String.valueOf(beautician.id), serviceType, source,
					querySelectedWorkersHandler);
		} else {
			source = 1;
			CommUtil.querySelectedWorkers(this, appointment, serviceLoc,
					shopId, getPetID_ServiceId_ItemIds(), workerIds,
					serviceType, source, querySelectedWorkersHandler);
		}
	}

	private AsyncHttpResponseHandler querySelectedWorkersHandler = new AsyncHttpResponseHandler() {

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] responseBody) {
			mPDialog.closeDialog();
			try {
				Gson gson = new Gson();
				AppointNewWorker appointNewWorker = gson.fromJson(new String(
						responseBody), AppointNewWorker.class);
				if (appointNewWorker != null) {
					int code = appointNewWorker.getCode();
					DataBean data = appointNewWorker.getData();
					String msg = appointNewWorker.getMsg();
					if (code == 0) {
						if (data != null) {
							workers = data.getWorkers();
							appointMentNewLevelAdapter.setWorkers(workers);
							services = data.getServices();
							if (workers != null && workers.size() > 0) {
								workersMap.clear();
								for (int i = 0; i < workers.size(); i++) {
									WorkersBean workersBean = workers.get(i);
									if (workersBean != null) {
										LevelBean level2 = workersBean
												.getLevel();
										List<Workers> workers2 = workersBean
												.getWorkers();
										if (workers2 != null
												&& workers2.size() > 0) {
											workersMap
													.put(level2.getLevel(),
															(ArrayList<Workers>) workers2);
										}
									}
								}
							}
							if (services != null && services.size() > 0) {
								List<LevelsBean> levels = null;
								for (int i = 0; i < services.size(); i++) {
									ServicesBean servicesBean = services.get(i);
									if (servicesBean.getServiceId() == appointMentNewPetInfoList
											.get(0).getServiceId()) {
										levels = servicesBean.getLevels();
										break;
									}
								}
								if (levels != null && levels.size() > 0) {
									levelList.clear();
									levelList.addAll(levels);
									appointMentNewLevelAdapter
											.notifyDataSetChanged();
									// 选中一个级别
									for (int i = 0; i < levelList.size(); i++) {
										LevelsBean levelsBean = levelList
												.get(i);
										if (levelsBean != null) {
											if (levelsBean.getLevel() == level) {
												selectData(levelsBean
														.getLevel());
												break;
											}
										}
									}
								}
							}
						}
					} else {
						if (Utils.isStrNull(msg)) {
							ToastUtil.showToastShortBottom(act, msg);
						}
					}
				}
			} catch (Exception e) {
				Log.e("TAG", "getWorkers()数据异常e = " + e.toString());
				ToastUtil.showToastShortBottom(AppointMentNewActivity.this,
						"数据异常");
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			ToastUtil.showToastShortBottom(AppointMentNewActivity.this, "请求失败");
			mPDialog.closeDialog();
		}
	};

	private void setLevelAndWorker(int position) {
		appointMentNewLevelAdapter.setIndex(position);
		LevelsBean levelsBean = levelList.get(position);
		if (levelsBean != null) {
			level = levelsBean.getLevel();
			setPetLevelName();
			getServiceCards();
		}
		if (workers != null && workers.size() > 0) {
			List<Workers> workers2 = null;
			for (int i = 0; i < workers.size(); i++) {
				WorkersBean workersBean = workers.get(i);
				if (workersBean != null) {
					LevelBean level2 = workersBean.getLevel();
					if (level == level2.getLevel()) {
						levelName = level2.getName();
						workers2 = workersBean.getWorkers();
						break;
					}
				}
			}
			if (workers2 != null && workers2.size() > 0) {
				hlv_appointmentnew_beautician.setVisibility(View.VISIBLE);
				if (workerId > 0) {
					try {
						workersList.clear();
						workersList.addAll(workers2);
						appointMentNewBeauAdapter.notifyDataSetChanged();
						int position1 = -1;
						// 选中指定美容师
						for (int i = 0; i < workers2.size(); i++) {
							Workers worker = workers2.get(i);
							if (worker != null) {
								if (worker.getWorkerId() == workerId
										&& worker.getIsAvail() == 1) {
									position1 = i;
									break;
								}
							}
						}
						if (position1 >= 0) {
							Log.e("TAG", "position1 = " + position1);
							setBeau(position1);
							// 滚动到美容师的位置
							scollToPosition(position1);
						} else {
							ToastUtil.showToastShortBottom(this,
									"该美容师时间已经被约满，换个时间或地址试试吧！");
							setNoWorker(2);
						}
					} catch (Exception e) {
						Log.e("TAG", "异常e = " + e.toString());
						e.printStackTrace();
					}
				} else {
					int position1 = -1;
					// 选中第一个可约的美容师
					for (int i = 0; i < workers2.size(); i++) {
						Workers worker = workers2.get(i);
						if (worker != null) {
							if (worker.getIsAvail() == 1) {
								position1 = i;
								break;
							}
						}
					}
					if (position1 >= 0) {
						setBeau(position1);
					} else {
						ToastUtil.showToastShortBottom(this, "该等级下无可约美容师");
						setNoWorker(2);
					}
				}
			} else {
				ToastUtil.showToastShortBottom(this, "该等级下无美容师");
				setNoWorker(1);
			}
		}
	}

	private void scollToPosition(final int position) {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				hlv_appointmentnew_beautician.setSelection(position);
			}
		}, 500);
	}

	private void setLevel(int position) {
		appointMentNewLevelAdapter.setIndex(position);
		LevelsBean levelsBean = levelList.get(position);
		if (levelsBean != null) {
			level = levelsBean.getLevel();
			setPetLevelName();
			getServiceCards();
		}
		if (workers != null && workers.size() > 0) {
			List<Workers> workers2 = null;
			for (int i = 0; i < workers.size(); i++) {
				WorkersBean workersBean = workers.get(i);
				if (workersBean != null) {
					LevelBean level2 = workersBean.getLevel();
					if (level == level2.getLevel()) {
						levelName = level2.getName();
						workers2 = workersBean.getWorkers();
						break;
					}
				}
			}
			if (workers2 != null && workers2.size() > 0) {
				hlv_appointmentnew_beautician.setVisibility(View.VISIBLE);
				workersList.clear();
				workersList.addAll(workers2);
				appointMentNewBeauAdapter.notifyDataSetChanged();
				int position1 = -1;
				// 选中第一个可约的美容师
				for (int i = 0; i < workers2.size(); i++) {
					Workers worker = workers2.get(i);
					if (worker != null) {
						if (worker.getIsAvail() == 1) {
							position1 = i;
							break;
						}
					}
				}
				if (position1 >= 0) {
					setBeau(position1);
				} else {
					ToastUtil.showToastShortBottom(this, "该等级下无可约美容师");
					setNoWorker(2);
				}
			} else {
				ToastUtil.showToastShortBottom(this, "该等级下无美容师");
				setNoWorker(1);
			}
		}
	}

	private void setPetLevelName() {
		for (int i = 0; i < appointMentNewPetInfoList.size(); i++) {
			AppointMentNewPetInfo appointMentNewPetInfo = appointMentNewPetInfoList
					.get(i);
			if (appointMentNewPetInfo != null) {
				if (services != null && services.size() > 0) {
					for (int j = 0; j < services.size(); j++) {
						ServicesBean servicesBean = services.get(j);
						if (servicesBean.getServiceId() == appointMentNewPetInfo
								.getServiceId()) {
							List<LevelsBean> levels = servicesBean.getLevels();
							if (levels != null && levels.size() > 0) {
								for (int k = 0; k < levels.size(); k++) {
									LevelsBean levelsBean = levels.get(k);
									if (levelsBean != null) {
										if (levelsBean.getLevel() == level) {
											appointMentNewPetInfo
													.setLevelName(levelsBean
															.getTitle());
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void setNoWorker(int flag) {
		if (flag == 1) {// 无美容师
			hlv_appointmentnew_beautician.setVisibility(View.GONE);
		} else if (flag == 2) {// 约满
			hlv_appointmentnew_beautician.setVisibility(View.VISIBLE);
		}
		rl_appointmentnew_servicecard.setVisibility(View.GONE);
		workerId = 0;
		for (int i = 0; i < appointMentNewPetInfoList.size(); i++) {
			AppointMentNewPetInfo appointMentNewPetInfo = appointMentNewPetInfoList
					.get(i);
			if (appointMentNewPetInfo != null) {
				appointMentNewPetInfo.setPrice(0);
				appointMentNewPetInfo.setLevel(level);
				appointMentNewPetInfo.setUseCard(false);
			}
		}
		appointMentNewPetAdapter.notifyDataSetChanged();
		setLastPrice();
		setDiscountPrice();
	}

	private void setBeau(int position) {
		Workers workers = workersList.get(position);
		if (workers != null) {
			int isAvail = workers.getIsAvail();
			if (isAvail == 0) {// 约满
				ToastUtil.showToastShortBottom(this, "该美容师暂不可约");
				setNoWorker(2);
			} else if (isAvail == 1) {// 可约
				// 选中美容师
				appointMentNewBeauAdapter.setIndex(position);
				List<PricesBean> prices = workers.getPrices();
				if (prices != null && prices.size() > 0) {
					for (int i = 0; i < prices.size(); i++) {
						PricesBean pricesBean = prices.get(i);
						if (pricesBean != null) {
							int petId = pricesBean.getPetId();
							int price = pricesBean.getPrice();
							AppointMentNewPetInfo appointMentNewPetInfo = appointMentNewPetInfoList
									.get(i);
							if (petId == appointMentNewPetInfo.getPetId()) {
								appointMentNewPetInfo.setPrice(price);
								appointMentNewPetInfo.setLevel(level);
							}
						}
					}
				}
				workerId = workers.getWorkerId();
				getNotice();
			}
		}
		appointMentNewPetAdapter.notifyDataSetChanged();
	}

	// 犬种ID_服务ID多宠物逗号隔开
	private String getPetID_ServiceIdDouHao() {
		initStrp();
		StringBuffer sb = new StringBuffer();
		if (strpList != null && strpList.size() > 0) {
			for (int i = 0; i < strpList.size(); i++) {
				Strp strp = strpList.get(i);
				if (strp != null) {
					if (i < strpList.size() - 1) {
						sb.append(strp.getPetId());
						sb.append("_");
						sb.append(strp.getServiceId());
						sb.append(",");
					} else {
						sb.append(strp.getPetId());
						sb.append("_");
						sb.append(strp.getServiceId());
					}
				}
			}
		}
		return sb.toString();
	}

	private void initStrp() {
		strpList.clear();
		if (appointMentNewPetInfoList != null
				&& appointMentNewPetInfoList.size() > 0) {
			for (int i = 0; i < appointMentNewPetInfoList.size(); i++) {
				AppointMentNewPetInfo appointMentNewPetInfo = appointMentNewPetInfoList
						.get(i);
				if (appointMentNewPetInfo != null) {
					StringBuffer sbItems = new StringBuffer();
					List<ExtraItem> extraItems = appointMentNewPetInfo
							.getExtraItems();
					if (extraItems != null && extraItems.size() > 0) {
						for (int j = 0; j < extraItems.size(); j++) {
							ExtraItem extraItem = extraItems.get(j);
							if (extraItem != null) {
								if (j < extraItems.size() - 1) {
									sbItems.append(extraItem.getItemId());
									sbItems.append(",");
								} else {
									sbItems.append(extraItem.getItemId());
								}
							}
						}
					}
					strpList.add(new Strp(appointMentNewPetInfo.getPetId(),
							appointMentNewPetInfo.getMyPetId(),
							appointMentNewPetInfo.getServiceId(), null, sbItems));
				}
			}
		}
	}

	// 犬种ID_服务ID多宠物下划线隔开
	private String getMyPetID_ServiceId() {
		initStrp();
		StringBuffer sb = new StringBuffer();
		if (strpList != null && strpList.size() > 0) {
			for (int i = 0; i < strpList.size(); i++) {
				Strp strp = strpList.get(i);
				if (strp != null) {
					if (i < strpList.size() - 1) {
						sb.append(strp.getMyPetId());
						sb.append("_");
						sb.append(strp.getServiceId());
						sb.append("_");
					} else {
						sb.append(strp.getMyPetId());
						sb.append("_");
						sb.append(strp.getServiceId());
					}
				}
			}
		}
		return sb.toString();
	}

	// 犬种ID_服务ID多宠物下划线隔开
	private String getPetID_ServiceId() {
		initStrp();
		StringBuffer sb = new StringBuffer();
		if (strpList != null && strpList.size() > 0) {
			for (int i = 0; i < strpList.size(); i++) {
				Strp strp = strpList.get(i);
				if (strp != null) {
					if (i < strpList.size() - 1) {
						sb.append(strp.getPetId());
						sb.append("_");
						sb.append(strp.getServiceId());
						sb.append("_");
					} else {
						sb.append(strp.getPetId());
						sb.append("_");
						sb.append(strp.getServiceId());
					}
				}
			}
		}
		return sb.toString();
	}

	// 犬种ID_服务ID_单项ID
	private String getPetID_ServiceId_ItemIds() {
		initStrp();
		StringBuffer sb = new StringBuffer();
		if (strpList != null && strpList.size() > 0) {
			for (int i = 0; i < strpList.size(); i++) {
				Strp strp = strpList.get(i);
				if (strp != null) {
					if (i < strpList.size() - 1) {
						sb.append(strp.getPetId());
						sb.append("_");
						sb.append(strp.getServiceId());
						sb.append("_");
						if (strp.getExtraItemIds() != null
								&& !"".equals(strp.getExtraItemIds().toString())
								&& strp.getExtraItemIds().length() > 0)
							sb.append(strp.getExtraItemIds());
						else
							sb.append("0");
						sb.append("_");
					} else {
						sb.append(strp.getPetId());
						sb.append("_");
						sb.append(strp.getServiceId());
						sb.append("_");
						if (strp.getExtraItemIds() != null
								&& !"".equals(strp.getExtraItemIds().toString())
								&& strp.getExtraItemIds().length() > 0)
							sb.append(strp.getExtraItemIds());
						else
							sb.append("0");
					}
				}
			}
		}
		return sb.toString();
	}

	private void setLinster() {
		ibBack.setOnClickListener(this);
		bt_titlebar_other.setOnClickListener(this);
		rl_appointmentnew_servicetype.setOnClickListener(this);
		rl_appointmentnew_servicecard.setOnClickListener(this);
		tv_appointmentnew_payprice.setOnClickListener(this);
		rl_appointmentnew_choose_time.setOnClickListener(this);
		appointMentNewExtraItemsAdapter
				.setOnItemNewExtraItemsClickListener(new OnItemNewExtraItemsClickListener() {
					@Override
					public void OnItemNewExtraItemsClick(int position) {
						if (extraItemList != null && extraItemList.size() > 0
								&& extraItemList.size() > position) {
							ExtraItem extraItem = extraItemList.get(position);
							if (extraItem != null) {
								String availablePets = extraItem
										.getAvailablePets();
								if (Utils.isStrNull(availablePets)) {
									List<AppointMentNewPetInfo> tempAppointMentNewPetInfoList = new ArrayList<AppointMentNewPetInfo>();
									tempAppointMentNewPetInfoList.clear();
									if (availablePets.contains(",")) {
										String[] split = availablePets
												.split(",");
										if (split != null && split.length > 0) {
											// 集合去重
											ArrayList<String> removeDuplicatePetId = new ArrayList<String>(
													new HashSet<String>(Arrays
															.asList(split)));
											tempAppointMentNewPetInfoList
													.clear();
											for (int i = 0; i < removeDuplicatePetId
													.size(); i++) {
												for (int j = 0; j < appointMentNewPetInfoList
														.size(); j++) {
													AppointMentNewPetInfo appointMentNewPetInfo = appointMentNewPetInfoList
															.get(j);
													if (appointMentNewPetInfo
															.getMyPetId() == Integer
															.parseInt(removeDuplicatePetId
																	.get(i))) {
														tempAppointMentNewPetInfoList
																.add(appointMentNewPetInfo);
													}
												}
											}
											if (tempAppointMentNewPetInfoList
													.size() > 0) {
												setDialog(extraItem,
														tempAppointMentNewPetInfoList);
											} else {
												ToastUtil.showToastShortBottom(
														act, "此单项您选的宠物不可以做");
											}
										}
									} else {
										tempAppointMentNewPetInfoList.clear();
										for (int i = 0; i < appointMentNewPetInfoList
												.size(); i++) {
											AppointMentNewPetInfo appointMentNewPetInfo = appointMentNewPetInfoList
													.get(i);
											if (appointMentNewPetInfo
													.getMyPetId() == Integer
													.parseInt(availablePets)) {
												tempAppointMentNewPetInfoList
														.add(appointMentNewPetInfo);
											}
										}
										if (tempAppointMentNewPetInfoList
												.size() > 0) {
											setDialog(extraItem,
													tempAppointMentNewPetInfoList);
										} else {
											ToastUtil.showToastShortBottom(act,
													"此单项您选的宠物不可以做");
										}
									}
								} else {
									ToastUtil.showToastShortBottom(act,
											"此单项没有宠物可以做");
								}
							}
						}
					}
				});
		appointMentNewLevelAdapter
				.setOnItemNewLevelClickListener(new OnItemNewLevelClickListener() {

					@Override
					public void OnItemNewLevelClick(int position) {
						if (levelList != null && levelList.size() > 0
								&& levelList.size() > position) {
							LevelsBean levelsBean = levelList.get(position);
							boolean select = isSelect(workersMap.get(levelsBean
									.getLevel()));
							if (select) {
								setLevel(position);
							}
						}
					}
				});
		appointMentNewLevelAdapter
				.setOnNewLevelWenHaoClickListener(new OnNewLevelWenHaoClickListener() {

					@Override
					public void OnNewLevelWenHaoClick(int position) {
						UmengStatistics.UmengEventStatistics(
								AppointMentNewActivity.this,
								Global.UmengEventID.click_BeauticianCorner);
						if (levelList != null && levelList.size() > 0
								&& levelList.size() > position) {
							LevelsBean levelsBean = levelList.get(position);
							if (levelsBean != null) {
								String tip = levelsBean.getTip();
								if (Utils.isStrNull(tip)) {
									showWenHaoDialog(tip);
								}
							}
						}
					}
				});
		hlv_appointmentnew_beautician
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						if (workersList != null && workersList.size() > 0
								&& workersList.size() > position) {
							Workers workers2 = workersList.get(position);
							if (workers2.getIsAvail() == 1) {
								// 选中美容师
								setBeau(position);
							}
							if (!Utils.isStrNull(tv_appointmentnew_yuyuetime
									.getText().toString().trim())) {
								Intent intent = new Intent(act,
										BeauticianDetailActivity.class);
								intent.putExtra("beautician_id",
										workers2.getWorkerId());
								intent.putExtra("isavail",
										workers2.getIsAvail());
								intent.putExtra("shopid", shopId);
								intent.putExtra("serviceloc", serviceLoc);
								intent.putExtra("lng", lng);
								intent.putExtra("lat", lat);
								intent.putExtra("previous",
										Global.APPOINTNEW_TO_BEAUDETAI);
								intent.putExtra("apptime", appointment);
								intent.putExtra("strp",
										getPetID_ServiceId_ItemIds());
								startActivity(intent);
							}
						}
					}
				});
		mgv_appointmentnew_addservice
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

					}
				});
		hlv_appointmentnew_servicecard
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						UmengStatistics.UmengEventStatistics(
								AppointMentNewActivity.this,
								Global.UmengEventID.click_RecommendPurchase);
						if (cardList != null && cardList.size() > 0
								&& cardList.size() > position) {
							AppointNewCard appointNewCard = cardList
									.get(position);
							if (appointNewCard != null) {
								Intent intent = new Intent(
										AppointMentNewActivity.this,
										CardsDetailNewActivity.class);
								intent.putExtra("id", appointNewCard.cardId);
								intent.putExtra("tid", level);
								intent.putParcelableArrayListExtra("myPets",
										myPets);
								startActivity(intent);
							}
						}
					}
				});
	}

	protected void showWenHaoDialog(String tip) {
		pWin = null;
		if (pWin == null) {
			final View view = View.inflate(this, R.layout.pw_appointnew_wenhao,
					null);
			ImageButton ib_pw_appointnewwenhao_close = (ImageButton) view
					.findViewById(R.id.ib_pw_appointnewwenhao_close);
			TextView tv_pw_appointnewwenhao_msg = (TextView) view
					.findViewById(R.id.tv_pw_appointnewwenhao_msg);
			tv_pw_appointnewwenhao_msg.setText(tip);
			pWin = new PopupWindow(view,
					android.view.ViewGroup.LayoutParams.MATCH_PARENT,
					android.view.ViewGroup.LayoutParams.MATCH_PARENT, true);
			pWin.setFocusable(true);
			pWin.setWidth(Utils.getDisplayMetrics(this)[0]);
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

	protected void setDialog(final ExtraItem extraItem,
			final List<AppointMentNewPetInfo> tempAppointMentNewPetInfoList) {
		AlertDialogListNavAndPost builder = new AlertDialogListNavAndPost(this)
				.builder();
		appointMentNewDialogPetAdapter = new AppointMentNewDialogPetAdapter<AppointMentNewPetInfo>(
				this, tempAppointMentNewPetInfoList, extraItem);
		builder.mlv_appointmentnew_pet
				.setAdapter(appointMentNewDialogPetAdapter);
		builder.mlv_appointmentnew_pet
				.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						setExtraItems(
								(AppointMentNewPetInfo) appointMentNewDialogPetAdapter
										.getItem(position), extraItem);
					}
				});
		builder.setTitle("请选择需要" + extraItem.getItemName() + "服务的宠物")
				.setPositiveButton("提交", new OnClickListener() {
					@Override
					public void onClick(View v) {
						for (int i = 0; i < tempAppointMentNewPetInfoList
								.size(); i++) {
							AppointMentNewPetInfo appointMentNewPetInfo = tempAppointMentNewPetInfoList
									.get(i);
							for (int j = 0; j < appointMentNewPetInfoList
									.size(); j++) {
								AppointMentNewPetInfo appointMentNewPetInfo2 = appointMentNewPetInfoList
										.get(j);
								if (appointMentNewPetInfo.getMyPetId() == appointMentNewPetInfo2
										.getMyPetId()) {
									appointMentNewPetInfo2
											.setExtraItems(appointMentNewPetInfo
													.getExtraItems());
								}
							}
							for (int j = 0; j < myPets.size(); j++) {
								MyPet myPet = myPets.get(j);
								if (appointMentNewPetInfo.getMyPetId() == myPet.myPetId) {
									myPet.setExtraItems(appointMentNewPetInfo
											.getExtraItems());
								}
							}
						}
						appointMentNewPetAdapter.notifyDataSetChanged();
						setLastPrice();
						appointMentNewExtraItemsAdapter
								.setPetData(appointMentNewPetInfoList);
					}
				}).setNegativeButton("取消", new OnClickListener() {
					@Override
					public void onClick(View v) {
					}
				}).show();

	}

	private int isContainItem(List<ExtraItem> extraItems, ExtraItem extraItem) {
		int position = -1;
		for (int i = 0; i < extraItems.size(); i++) {
			if (extraItems.get(i).getItemId() == extraItem.getItemId()) {
				position = i;
				break;
			}
		}
		return position;
	}

	protected void setExtraItems(AppointMentNewPetInfo appointMentNewPetInfo,
			ExtraItem extraItem) {
		if (appointMentNewPetInfo != null) {
			List<ExtraItem> extraItems = appointMentNewPetInfo.getExtraItems();
			if (extraItems != null && extraItems.size() > 0) {
				int position = isContainItem(extraItems, extraItem);
				if (position >= 0) {
					extraItems.remove(position);
				} else {
					extraItems.add(extraItem);
				}
			} else {
				extraItems = new ArrayList<ExtraItem>();
				extraItems.add(extraItem);
			}
			appointMentNewPetInfo.setExtraItems(extraItems);
			appointMentNewDialogPetAdapter.notifyDataSetChanged();
		}
	}

	private void initView() {
		setContentView(R.layout.activity_appoint_ment_new);
		ibBack = (ImageButton) findViewById(R.id.ib_titlebar_back);
		tvTitle = (TextView) findViewById(R.id.tv_titlebar_title);
		bt_titlebar_other = (Button) findViewById(R.id.bt_titlebar_other);
		mgv_appointmentnew_level = (MyGridView) findViewById(R.id.mgv_appointmentnew_level);
		rl_appointmentnew_servicecard = (RelativeLayout) findViewById(R.id.rl_appointmentnew_servicecard);
		tv_appointmentnew_servicecard = (TextView) findViewById(R.id.tv_appointmentnew_servicecard);
		tv_appointmentnew_servicecarddes = (TextView) findViewById(R.id.tv_appointmentnew_servicecarddes);
		tv_appointmentnew_payprice = (TextView) findViewById(R.id.tv_appointmentnew_payprice);
		tv_appointmentnew_paydes = (TextView) findViewById(R.id.tv_appointmentnew_paydes);
		mlv_appointmentnew_pet = (MListview) findViewById(R.id.mlv_appointmentnew_pet);
		rl_appointmentnew_servicetype = (RelativeLayout) findViewById(R.id.rl_appointmentnew_servicetype);
		tv_appointmentnew_servicetype = (TextView) findViewById(R.id.tv_appointmentnew_servicetype);
		rl_appointmentnew_choose_time = (RelativeLayout) findViewById(R.id.rl_appointmentnew_choose_time);
		tv_appointmentnew_yuyuetime = (TextView) findViewById(R.id.tv_appointmentnew_yuyuetime);
		hlv_appointmentnew_beautician = (HorizontalListView) findViewById(R.id.hlv_appointmentnew_beautician);
		mgv_appointmentnew_addservice = (MyGridView) findViewById(R.id.mgv_appointmentnew_addservice);
		mv_appointmentnew_verticalbanner1 = (MarqueeView) findViewById(R.id.mv_appointmentnew_verticalbanner1);
		hlv_appointmentnew_servicecard = (HorizontalListView) findViewById(R.id.hlv_appointmentnew_servicecard);
		ll_appointmentnew_addservices = (LinearLayout) findViewById(R.id.ll_appointmentnew_addservices);
		ll_appointmentnew_servicecard = (LinearLayout) findViewById(R.id.ll_appointmentnew_servicecard);
		slv_appointmentnew = (ScrollView) findViewById(R.id.slv_appointmentnew);
	}

	private void setView() {
		extraItemList.clear();
		String serviceTypeName = null;
		if (serviceType == 1) {
			serviceTypeName = "洗澡";
		} else if (serviceType == 2) {
			serviceTypeName = "美容";
		}
		if (serviceLoc == 2) {
			tv_appointmentnew_servicetype.setText("选择上门服务:" + petAddress);
		} else if (serviceLoc == 1) {
			tv_appointmentnew_servicetype.setText("选择到店服务:" + shopName);
		}
		tvTitle.setText("选择" + serviceTypeName + "服务");
		bt_titlebar_other.setText("切换服务");
		appointMentNewPetAdapter = new AppointMentNewPetAdapter<AppointMentNewPetInfo>(
				this, appointMentNewPetInfoList);
		mlv_appointmentnew_pet.setAdapter(appointMentNewPetAdapter);
		appointMentNewPetAdapter.notifyDataSetChanged();
		appointMentNewExtraItemsAdapter = new AppointMentNewExtraItemsAdapter<ExtraItem>(
				this, extraItemList);
		mgv_appointmentnew_addservice
				.setAdapter(appointMentNewExtraItemsAdapter);
		levelList.clear();
		appointMentNewLevelAdapter = new AppointMentNewLevelAdapter<LevelsBean>(
				this, levelList);
		mgv_appointmentnew_level.setAdapter(appointMentNewLevelAdapter);
		workersList.clear();
		appointMentNewBeauAdapter = new AppointMentNewBeauAdapter<Workers>(
				this, workersList);
		hlv_appointmentnew_beautician.setAdapter(appointMentNewBeauAdapter);
		appointMentNewServiceCardAdapter = new AppointMentNewServiceCardAdapter<AppointNewCard>(
				this, cardList);
		hlv_appointmentnew_servicecard
				.setAdapter(appointMentNewServiceCardAdapter);
	}

	private void initData() {
		beforeAppointMentNewPetInfo = null;
		isClear = false;
		isLevelAndWorker = false;
		appointMentNewPetInfoList.clear();
		act = this;
		MApplication.listAppoint.add(this);
		Intent intent = getIntent();
		myPets = intent.getParcelableArrayListExtra("myPets");
		lat = intent.getDoubleExtra("lat", 0);
		lng = intent.getDoubleExtra("lng", 0);
		beautician = (Beautician) intent.getSerializableExtra("beautician");
		if (beautician != null) {
			if (beautician.sort == 10) {
				level = 1;
			} else if (beautician.sort == 20) {
				level = 2;
			} else if (beautician.sort == 30) {
				level = 3;
			}
		}
		if (myPets != null && myPets.size() > 0) {
			for (int i = 0; i < myPets.size(); i++) {
				MyPet myPet = myPets.get(i);
				if (myPet != null) {
					if (myPet.petKind == 1) {// 狗
						if (myPet.serviceType == 1) {// 狗洗澡
							myPet.serviceId = 1;
						} else if (myPet.serviceType == 2) {// 狗美容
							myPet.serviceId = 2;
						}
					} else if (myPet.petKind == 2) {// 猫
						if (myPet.serviceType == 1) {// 猫洗澡
							myPet.serviceId = 3;
						} else if (myPet.serviceType == 2) {// 猫美容
							myPet.serviceId = 4;
						}
					}
					appointMentNewPetInfoList.add(new AppointMentNewPetInfo(
							myPet.avatar, myPet.petName, myPet.petId,
							myPet.myPetId, myPet.nickname, null, 0, false,
							myPet.serviceId, null, myPet.petKind,
							myPet.serviceType, level, myPet.serviceLoc,
							myPet.addressId, myPet.address, myPet.shopName,
							myPet.shopId, myPet.shopAddress));
				}
			}
			MyPet myPet = myPets.get(0);
			if (myPet != null) {
				serviceType = myPet.serviceType;
				serviceLoc = myPet.serviceLoc;
				petAddress = myPet.address;
				shopName = myPet.shopName;
				shopId = myPet.shopId;
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_titlebar_back:// 返回
			finish();
			break;
		case R.id.bt_titlebar_other:// 换服务
			if (appointMentNewPetInfoList != null
					&& appointMentNewPetInfoList.size() > 0) {
				if (appointMentNewPetInfoList.size() == 1) {
					beforeAppointMentNewPetInfo = appointMentNewPetInfoList
							.get(0);
					Intent data = new Intent(AppointMentNewActivity.this,
							ChangeServiceActivity.class);
					data.putExtra("previous",
							Global.APPOINTMENTNEW_TO_CHANGESERVICE);
					data.putExtra("lat", lat);
					data.putExtra("lng", lng);
					data.putExtra("time", appointment);
					data.putExtra("appointMentNewPetInfo",
							(Serializable) appointMentNewPetInfoList.get(0));
					startActivityForResult(data,
							Global.APPOINTMENTNEW_TO_CHANGESERVICE);
				} else {
					setChangeBeautyDialog();
				}
			}
			break;
		case R.id.rl_appointmentnew_servicetype://
			UmengStatistics.UmengEventStatistics(this,
					Global.UmengEventID.click_Return);
			finish();
			break;
		case R.id.rl_appointmentnew_servicecard://
			if (cardAmount > 0) {
				if (cardsConfirmOrders.size() > 0) {
					Intent intent = new Intent(mContext,
							OrderConfirmChooseCardActivity.class);
					intent.putParcelableArrayListExtra("cardsConfirmOrders",
							cardsConfirmOrders);
					intent.putExtra("petSize", appointMentNewPetInfoList.size());
					startActivityForResult(intent,
							Global.CARDSORDERDETAIL_TO_CHOOSE_CARDS);
				} else {
					ToastUtil.showToastShortBottom(act, "暂无可用服务卡");
				}
			} else {
				slv_appointmentnew.fullScroll(ScrollView.FOCUS_DOWN);
			}
			break;
		case R.id.tv_appointmentnew_payprice://
			// 提交订单
			if (!Utils.isStrNull(tv_appointmentnew_yuyuetime.getText()
					.toString().trim())) {
				ToastUtil.showToastShort(AppointMentNewActivity.this,
						"请选择您的服务时间");
			} else if (workerId <= 0) {
				ToastUtil.showToastShort(AppointMentNewActivity.this, "请选择美容师");
			} else {
				Workers selectedWorker = null;
				if (workersList != null && workersList.size() > 0) {
					for (int i = 0; i < workersList.size(); i++) {
						Workers workers = workersList.get(i);
						if (workers != null) {
							if (workerId == workers.getWorkerId()) {
								selectedWorker = workers;
								break;
							}
						}
					}
				}
				if (selectedWorker != null && appointMentNewPetInfoList != null
						&& appointMentNewPetInfoList.size() > 0) {
					selectedWorker.setLevelName(levelName);
					Intent intent = new Intent(this,
							OrderDetailNewActivity.class);
					intent.putExtra("lat", lat);
					intent.putExtra("lng", lng);
					intent.putExtra("shopLat",
							getIntent().getDoubleExtra("shopLat", 0));
					intent.putExtra("shopLng",
							getIntent().getDoubleExtra("shopLng", 0));
					intent.putExtra("strp", getPetID_ServiceId_ItemIds());
					intent.putExtra("appointment", appointment);
					intent.putExtra("lastPrice", lastPrice);
					intent.putExtra("listIds", listIds);
					intent.putExtra("selectedWorker",
							(Serializable) selectedWorker);
					intent.putExtra("appointMentNewPetInfoList",
							(Serializable) appointMentNewPetInfoList);
					startActivity(intent);
				}
			}
			break;
		case R.id.rl_appointmentnew_choose_time:// 选择时间
			int clicksort = 10;
			UmengStatistics.UmengEventStatistics(mContext,
					Global.UmengEventID.click_SelectTime);
			Intent intent1 = new Intent(this, SelectTimeOrUrgentActivity.class);
			intent1.putExtra("lat", lat);
			intent1.putExtra("lng", lng);
			if (beautician != null) {
				intent1.putExtra("source", 2);
				intent1.putExtra("beautician_id", beautician.id);
			} else {
				intent1.putExtra("beautician_id", workerId);
				intent1.putExtra("source", 1);
			}
			intent1.putExtra("serviceloc", serviceLoc);
			intent1.putExtra("shopid", shopId);
			if (level == 1) {
				clicksort = 10;
			} else if (level == 2) {
				clicksort = 20;
			} else if (level == 3) {
				clicksort = 30;
			}
			intent1.putExtra("clicksort", clicksort);
			intent1.putExtra("position", position);
			intent1.putExtra("strp", getPetID_ServiceId_ItemIds());
			startActivityForResult(intent1,
					Global.BOOKINGSERVICEREQUESTCODE_TIME);
			break;
		default:
			break;
		}
	}

	private void setChangeBeautyDialog() {
		AlertDialogListNavAndPost builder = new AlertDialogListNavAndPost(this)
				.builder();
		appointNewChangeBeautyDialogPetAdapter = new AppointNewChangeBeautyDialogPetAdapter<AppointMentNewPetInfo>(
				this, appointMentNewPetInfoList);
		builder.mlv_appointmentnew_pet
				.setAdapter(appointNewChangeBeautyDialogPetAdapter);
		builder.mlv_appointmentnew_pet
				.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						if (appointMentNewPetInfoList != null
								&& appointMentNewPetInfoList.size() > 0
								&& appointMentNewPetInfoList.size() > position) {
							AppointMentNewPetInfo itemAppointMentNewPetInfo = (AppointMentNewPetInfo) appointNewChangeBeautyDialogPetAdapter
									.getItem(position);
							for (AppointMentNewPetInfo appointMentNewPetInfo : appointMentNewPetInfoList) {
								if (appointMentNewPetInfo != null) {
									if (itemAppointMentNewPetInfo.getMyPetId() == appointMentNewPetInfo
											.getMyPetId()) {
										appointMentNewPetInfo.setSelect(true);
									} else {
										appointMentNewPetInfo.setSelect(false);
									}
								}
							}
							appointNewChangeBeautyDialogPetAdapter
									.notifyDataSetChanged();
						}
					}
				});
		builder.setTitle("请选择需要换服务服务的宠物")
				.setPositiveButton("提交", new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (appointMentNewPetInfoList != null
								&& appointMentNewPetInfoList.size() > 0) {
							for (int i = 0; i < appointMentNewPetInfoList
									.size(); i++) {
								AppointMentNewPetInfo appointMentNewPetInfo = appointMentNewPetInfoList
										.get(i);
								if (appointMentNewPetInfo != null) {
									if (appointMentNewPetInfo.isSelect()) {
										beforeAppointMentNewPetInfo = appointMentNewPetInfo;
										Intent data = new Intent(
												AppointMentNewActivity.this,
												ChangeServiceActivity.class);
										data.putExtra(
												"previous",
												Global.APPOINTMENTNEW_TO_CHANGESERVICE);
										data.putExtra("lat", lat);
										data.putExtra("lng", lng);
										data.putExtra("time", appointment);
										data.putExtra(
												"appointMentNewPetInfo",
												(Serializable) appointMentNewPetInfo);
										startActivityForResult(
												data,
												Global.APPOINTMENTNEW_TO_CHANGESERVICE);
									}
								}
							}
						}
					}
				}).setNegativeButton("取消", new OnClickListener() {
					@Override
					public void onClick(View v) {
					}
				}).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Global.RESULT_OK) {
			if (requestCode == Global.CARDSORDERDETAIL_TO_CHOOSE_CARDS) {// 选择次卡返回
				cardsConfirmOrders = data
						.getParcelableArrayListExtra("cardsConfirmOrders");
				int index = data.getIntExtra("index", -1);
				switch (index) {
				case 0:
					listIds.clear();
					tv_appointmentnew_servicecarddes.setText("服务卡");
					tv_appointmentnew_servicecard.setText("不使用服务卡");
					for (int i = 0; i < appointMentNewPetInfoList.size(); i++) {
						AppointMentNewPetInfo appointMentNewPetInfo = appointMentNewPetInfoList
								.get(i);
						if (appointMentNewPetInfo != null) {
							appointMentNewPetInfo.setUseCard(false);
						}
					}
					appointMentNewPetAdapter.notifyDataSetChanged();
					setLastPrice();
					setDiscountPrice();
					break;
				case 1:
					if (!cardsConfirmOrders.isEmpty()) {
						setCardTips();
					}
					break;
				}
			} else if (requestCode == Global.BOOKINGSERVICEREQUESTCODE_TIME) {// 选择时间返回
				isLevelAndWorker = false;
				// 选择时间返回
				workerIds = data.getStringExtra("strListWokerId");
				appointment = data.getStringExtra("date");
				position = data.getIntExtra("position", -1);
				if (data.getIntExtra("level", 0) == 10) {
					level = 1;
				} else if (data.getIntExtra("level", 0) == 20) {
					level = 2;
				} else if (data.getIntExtra("level", 0) == 30) {
					level = 3;
				}
				if (data.getIntExtra("beautician_id", 0) > 0) {
					isLevelAndWorker = true;
					workerId = data.getIntExtra("beautician_id", 0);
				}
				String datastr = "";
				if (appointment.length() >= 3) {
					String substring = appointment.substring(
							appointment.length() - 3, appointment.length());
					if (substring.equals(":00")) {
						datastr = appointment.substring(0,
								appointment.length() - 3);
						tv_appointmentnew_yuyuetime.setText(datastr);
					} else {
						tv_appointmentnew_yuyuetime.setText(appointment);
					}
				}
				getWorkers();
			} else if (requestCode == Global.APPOINTMENTNEW_TO_CHANGESERVICE) {// 换服务
				AppointMentNewPetInfo appointMentNewPetInfo = (AppointMentNewPetInfo) data
						.getSerializableExtra("appointMentNewPetInfo");
				if (appointMentNewPetInfo != null) {
					if (beforeAppointMentNewPetInfo != null
							&& appointMentNewPetInfo.getServiceType() != beforeAppointMentNewPetInfo
									.getServiceType()) {
						workerIds = null;
						appointment = null;
						position = -1;
						workerId = 0;
						tv_appointmentnew_yuyuetime.setText("");
					}
					for (int i = 0; i < appointMentNewPetInfoList.size(); i++) {
						AppointMentNewPetInfo appointMentNewPetInfo2 = appointMentNewPetInfoList
								.get(i);
						if (appointMentNewPetInfo2 != null) {
							if (appointMentNewPetInfo.getMyPetId() == appointMentNewPetInfo2
									.getMyPetId()) {
								appointMentNewPetInfoList.set(i,
										appointMentNewPetInfo);
								break;
							}
						}
					}
					for (int i = 0; i < myPets.size(); i++) {
						MyPet myPet = myPets.get(i);
						if (myPet != null) {
							if (appointMentNewPetInfo.getMyPetId() == myPet.myPetId) {
								myPet.serviceId = appointMentNewPetInfo
										.getServiceId();
								myPet.serviceType = appointMentNewPetInfo
										.getServiceType();
								myPet.extraItems = appointMentNewPetInfo
										.getExtraItems();
								break;
							}
						}
					}
					// 请求数据
					getWorkers();
					getSingles();
					appointMentNewExtraItemsAdapter
							.setPetData(appointMentNewPetInfoList);
				}
			}
		}
	}

	private void setCardTips() {
		setNoUseCard();
		rl_appointmentnew_servicecard.setVisibility(View.VISIBLE);
		if (cardAmount > 0) {
			if (cardsConfirmOrders != null && cardsConfirmOrders.size() > 0) {
				tv_appointmentnew_servicecarddes.setText("服务卡");
				// 匹配服务卡
				matchingServiceCard();
			} else {
				setDiscountPrice();
				setLastPrice();
				tv_appointmentnew_servicecarddes.setText("服务卡");
				tv_appointmentnew_servicecard.setText("本次消费暂无可用洗美卡");
			}
		} else {
			setDiscountPrice();
			setLastPrice();
			tv_appointmentnew_servicecard.setText("去购卡");
			tv_appointmentnew_servicecarddes.setText("未购买洗美折扣卡，原价支付");
		}
	}

	private void setDiscountPrice() {
		if (cardAmount > 0 && cardsConfirmOrders.size() <= 0) {
			tv_appointmentnew_paydes.setVisibility(View.GONE);
		} else {
			tv_appointmentnew_paydes.setVisibility(View.VISIBLE);
			favorablePrice = 0;
			for (int i = 0; i < appointMentNewPetInfoList.size(); i++) {
				AppointMentNewPetInfo appointMentNewPetInfo = appointMentNewPetInfoList
						.get(i);
				if (appointMentNewPetInfo.isUseCard()) {
					favorablePrice += appointMentNewPetInfo.getPrice();
				}
			}
			String text1 = "使用服务卡已优惠 ¥ " + Utils.formatDouble(favorablePrice);
			SpannableString ss1 = new SpannableString(text1);
			ss1.setSpan(
					new ForegroundColorSpan(getResources().getColor(
							R.color.aD1494F)), 8, ss1.length(),
					Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			tv_appointmentnew_paydes.setText(ss1);
		}
	}

	private void setLastPrice() {
		lastPrice = 0;
		for (int i = 0; i < appointMentNewPetInfoList.size(); i++) {
			AppointMentNewPetInfo appointMentNewPetInfo = appointMentNewPetInfoList
					.get(i);
			if (appointMentNewPetInfo.isUseCard()) {
				favorablePrice += appointMentNewPetInfo.getPrice();
			}
			if (appointMentNewPetInfo.isUseCard()) {
				if (appointMentNewPetInfo.getExtraItems() != null
						&& appointMentNewPetInfo.getExtraItems().size() > 0) {
					double extraItemPayPrices = 0;
					for (int j = 0; j < appointMentNewPetInfo.getExtraItems()
							.size(); j++) {
						ExtraItem extraItem = appointMentNewPetInfo
								.getExtraItems().get(j);
						if (extraItem != null) {
							extraItemPayPrices += extraItem.getPrice();
						}
					}
					lastPrice += extraItemPayPrices;
				} else {
					lastPrice += 0;
				}
			} else {
				if (appointMentNewPetInfo.getExtraItems() != null
						&& appointMentNewPetInfo.getExtraItems().size() > 0) {
					double extraItemPayPrices = 0;
					for (int j = 0; j < appointMentNewPetInfo.getExtraItems()
							.size(); j++) {
						ExtraItem extraItem = appointMentNewPetInfo
								.getExtraItems().get(j);
						if (extraItem != null) {
							extraItemPayPrices += extraItem.getPrice();
						}
					}
					lastPrice += (extraItemPayPrices + appointMentNewPetInfo
							.getPrice());
				} else {
					lastPrice += appointMentNewPetInfo.getPrice();
				}
			}
		}
		String text = "提交订单 ¥ " + Utils.formatDouble(lastPrice);
		SpannableString ss = new SpannableString(text);
		ss.setSpan(new TextAppearanceSpan(this, R.style.swimdetailshowtext0),
				6, ss.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		tv_appointmentnew_payprice.setText(ss);
	}

	private void matchingServiceCard() {
		listIds.clear();
		int bathNum = 0;
		int beauNum = 0;
		int feature = 0;
		boolean isChoose = false;
		for (int i = 0; i < cardsConfirmOrders.size(); i++) {
			if (cardsConfirmOrders.get(i).isChoose == 1) {
				isChoose = true;
				break;
			}
		}
		for (int i = 0; i < cardsConfirmOrders.size(); i++) {
			CardsConfirmOrder orderCard = cardsConfirmOrders.get(i);
			for (int j = 0; j < orderCard.arrayList.size(); j++) {
				orderCard.arrayList.get(j).count = orderCard.arrayList.get(j).oldCount;
			}
			cardsConfirmOrders.set(i, orderCard);
		}
		for (int i = 0; i < appointMentNewPetInfoList.size(); i++) {
			AppointMentNewPetInfo appointMentNewPetInfo = appointMentNewPetInfoList
					.get(i);
			for (int j = 0; j < cardsConfirmOrders.size(); j++) {
				boolean isJump = false;
				CardsConfirmOrder cardsConfirmOrder = cardsConfirmOrders.get(j);
				ArrayList<CardItems> arrayList = cardsConfirmOrder.arrayList;
				for (int k = 0; k < arrayList.size(); k++) {
					CardItems cardItems = arrayList.get(k);
					if (appointMentNewPetInfo.getPetId() == cardsConfirmOrder.petId
							&& appointMentNewPetInfo.getServiceType() == cardItems.serviceType
							&& appointMentNewPetInfo.getLevel() == cardItems.level
							&& appointMentNewPetInfo.getServiceId() == cardItems.id) {
						if (cardItems.count > 0) {
							if (isChoose) {
								if (cardsConfirmOrder.isChoose == 1) {
									cardsConfirmOrder.isChoose = 1;
									if (appointMentNewPetInfo.getServiceType() == 1) {
										appointMentNewPetInfo.setUseCard(true);
										bathNum++;
									} else if (appointMentNewPetInfo
											.getServiceType() == 2) {
										appointMentNewPetInfo.setUseCard(true);
										beauNum++;
									} else if (appointMentNewPetInfo
											.getServiceType() == 3) {
										appointMentNewPetInfo.setUseCard(true);
										feature++;
									}
									listIds.add(cardsConfirmOrder.id);
									cardItems.count--;
									isJump = true;
									break;
								}
							} else {
								cardsConfirmOrder.isChoose = 1;
								if (appointMentNewPetInfo.getServiceType() == 1) {
									appointMentNewPetInfo.setUseCard(true);
									bathNum++;
								} else if (appointMentNewPetInfo
										.getServiceType() == 2) {
									appointMentNewPetInfo.setUseCard(true);
									beauNum++;
								} else if (appointMentNewPetInfo
										.getServiceType() == 3) {
									appointMentNewPetInfo.setUseCard(true);
									feature++;
								}
								listIds.add(cardsConfirmOrder.id);
								cardItems.count--;
								isJump = true;
								break;
							}
							if (isJump) {
								break;
							}
						}
					}
				}
				if (isJump) {
					break;
				}
			}
		}
		for (int i = 0; i < cardsConfirmOrders.size(); i++) {
			CardsConfirmOrder orderCard = cardsConfirmOrders.get(i);
			for (int j = 0; j < orderCard.arrayList.size(); j++) {
				orderCard.arrayList.get(j).count = orderCard.arrayList.get(j).oldCount;
			}
			cardsConfirmOrders.set(i, orderCard);
		}
		if (bathNum > 0 && beauNum > 0) {
			tv_appointmentnew_servicecard.setText(CardTag + bathNum + "次洗澡"
					+ beauNum + "美容");
		} else if (bathNum > 0 && beauNum <= 0) {
			tv_appointmentnew_servicecard.setText(CardTag + bathNum + "次洗澡");
		} else if (bathNum <= 0 && beauNum > 0) {
			tv_appointmentnew_servicecard.setText(CardTag + beauNum + "次美容");
		} else if (bathNum <= 0 && beauNum <= 0 && feature > 0) {
			tv_appointmentnew_servicecard.setText("服务卡支付" + feature + "次特色服务");
		} else if (bathNum == 0 && beauNum == 0) {
			tv_appointmentnew_servicecard.setText("本次消费暂无可用洗美卡");
		}
		appointMentNewPetAdapter.notifyDataSetChanged();
		setDiscountPrice();
		setLastPrice();
	}

	private void setNoUseCard() {
		if (appointMentNewPetInfoList != null
				&& appointMentNewPetInfoList.size() > 0) {
			for (int i = 0; i < appointMentNewPetInfoList.size(); i++) {
				AppointMentNewPetInfo appointMentNewPetInfo = appointMentNewPetInfoList
						.get(i);
				if (appointMentNewPetInfo != null) {
					appointMentNewPetInfo.setUseCard(false);
				}
			}
		}
		appointMentNewPetAdapter.notifyDataSetChanged();
	}

	private void selectData(int localLevel) {
		ArrayList<Workers> btc = workersMap.get(1);
		ArrayList<Workers> btc1 = workersMap.get(2);
		ArrayList<Workers> btc2 = workersMap.get(3);
		if (localLevel > 0) {
			switch (localLevel) {
			case 1:
				if (isSelect(btc)) {// 中级可约
					setLevelOrWorker(0);
				} else {// 中级不可约
					if (isSelect(btc1)) {// 高级可约
						setLevelOrWorker(1);
					} else {// 高级不可约
						if (isSelect(btc2)) {// 首席可约
							setLevelOrWorker(2);
						}
					}
				}
				break;
			case 2:
				if (isSelect(btc1)) {// 高级可约
					setLevelOrWorker(1);
				} else {// 高级不可约
					if (isSelect(btc)) {// 中级可约
						setLevelOrWorker(0);
					} else {// 中级不可约
						if (isSelect(btc2)) {// 首席可约
							setLevelOrWorker(2);
						}
					}
				}
				break;
			case 3:
				if (isSelect(btc2)) {// 首席可约
					setLevelOrWorker(2);
				} else {// 首席不可约
					if (isSelect(btc)) {// 中级可约
						setLevelOrWorker(0);
					} else {// 中级不可约
						if (isSelect(btc1)) {// 高级可约
							setLevelOrWorker(1);
						}
					}
				}
				break;
			default:
				break;
			}
		}
	}

	private void setLevelOrWorker(int position) {
		if (isLevelAndWorker) {
			setLevelAndWorker(position);
			isLevelAndWorker = false;
		} else {
			setLevel(position);
		}
	}

	private boolean isSelect(ArrayList<Workers> workersList) {
		boolean bool = false;
		if (workersList != null && workersList.size() > 0) {
			bool = true;
		} else {
			bool = false;
		}
		return bool;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			unregisterReceiver(receiver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
