package com.haotang.pet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haotang.base.SuperActivity;
import com.haotang.pet.adapter.MemberFancyCoverFlowAdapter;
import com.haotang.pet.adapter.MemberLevelDesAdapter;
import com.haotang.pet.adapter.MgvMemberServiceAdapter;
import com.haotang.pet.entity.AllLevel;
import com.haotang.pet.entity.AllPrivilege;
import com.haotang.pet.entity.MemberLevelDes;
import com.haotang.pet.net.AsyncHttpResponseHandler;
import com.haotang.pet.util.CommUtil;
import com.haotang.pet.util.GlideUtil;
import com.haotang.pet.util.Global;
import com.haotang.pet.util.ImageLoaderUtil;
import com.haotang.pet.util.MProgressDialog;
import com.haotang.pet.util.ToastUtil;
import com.haotang.pet.util.Utils;
import com.haotang.pet.view.FancyCoverFlow;
import com.haotang.pet.view.MListview;
import com.haotang.pet.view.MyGridView;

/**
 * <p>
 * Title:UserMemberActivity
 * </p>
 * <p>
 * Description:会员主界面
 * </p>
 * <p>
 * Company:北京昊唐科技有限公司
 * </p>
 * 
 * @author 徐俊
 * @date 2016-12-22 上午10:34:50
 */
public class UserMemberActivity extends SuperActivity implements
		OnClickListener {
	private static final int RequestCode_UserMember = 1234;
	private MProgressDialog pDialog;
	private ImageButton ibBack;
	private TextView tvTitle;
	private TextView tv_member_des;
	private TextView tv_member_loginorcz;
	private ImageView iv_member_nologin;
	private RelativeLayout rl_member_hyjs;
	private TextView tv_member_hynum;
	private MyGridView mgv_member_service;
	private MListview mlv_member_hyjs;
	private List<MemberLevelDes> hyjsList = new ArrayList<MemberLevelDes>();
	private String memberLevelIntroUrl;
	private List<AllPrivilege> allPrivilegeList = new ArrayList<AllPrivilege>();
	private List<AllPrivilege> tempPrivilegeList = new ArrayList<AllPrivilege>();
	private PopupWindow pWin;
	private RelativeLayout rl_member_login;
	private ProgressBar pb_member_level;
	private FancyCoverFlow fcf_member_level;
	private MemberFancyCoverFlowAdapter memberAdapter;
	private ArrayList<AllLevel> allLevelList = new ArrayList<AllLevel>();
	private int localMemberLevelId;
	private String[] unSelectPic = new String[] {
			"drawable://" + R.drawable.unlight1,
			"drawable://" + R.drawable.unlight2,
			"drawable://" + R.drawable.unlight3,
			"drawable://" + R.drawable.light4 };
	private String[] selectPic = new String[] {
			"drawable://" + R.drawable.light1,
			"drawable://" + R.drawable.light2,
			"drawable://" + R.drawable.light3,
			"drawable://" + R.drawable.light4 };
	private String[] levelPic = new String[] {
			"drawable://" + R.drawable.level1,
			"drawable://" + R.drawable.level2,
			"drawable://" + R.drawable.level3,
			"drawable://" + R.drawable.level4 };
	private int userGrowthValue;
	private MgvMemberServiceAdapter<AllPrivilege> mgvMemberServiceAdapter;
	private String localPrivilegeIds;
	private int ItemSelected;
	private TextView tv_member_czz;
	private String memberTime;
	private int userLevel;
	private LinearLayout linearlayout1;
	private JSONObject jUserInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		findView();
		setView();
		setLinster();
		getData();
	}

	private void init() {
		pDialog = new MProgressDialog(this);
	}

	private void setLinster() {
		ibBack.setOnClickListener(this);
		tv_member_loginorcz.setOnClickListener(this);
		rl_member_hyjs.setOnClickListener(this);
		mgv_member_service.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (allPrivilegeList.size() > 0
						&& allPrivilegeList.size() > position) {
					if (position != allPrivilegeList.size()) {
						AllPrivilege allPrivilege = allPrivilegeList
								.get(position);
						if (allPrivilege != null) {
							String privilegePic = allPrivilege.privilegePic;
							if (privilegePic != null
									&& !TextUtils.isEmpty(privilegePic)) {
								showPopPhoto(privilegePic);
							}
						}
					}
				}
			}
		});
		fcf_member_level.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});
		fcf_member_level
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						try {
							ItemSelected = position;
							if (position == allLevelList.size() - 1) {
								AutoScollTask autoScollTask = new AutoScollTask();
								autoScollTask.execute();
							} else {
								if (allLevelList.size() > position) {
									AllLevel allLevel = allLevelList
											.get(position);
									if (allLevel != null) {
										boolean isLight = false;
										String privilegeIds = allLevel.privilegeIds;
										tempPrivilegeList.clear();
										if (position == allLevelList.size() - 2) {
											tempPrivilegeList
													.addAll(allPrivilegeList);
											tempPrivilegeList
													.add(new AllPrivilege(
															1024,
															0,
															"drawable://"
																	+ R.drawable.member_jqqd,
															"敬请期待",
															"drawable://"
																	+ R.drawable.member_jqqd,
															"drawable://"
																	+ R.drawable.member_jqqd));
										} else {
											for (int i = 0; i < allPrivilegeList
													.size(); i++) {
												if (privilegeIds.contains(String
														.valueOf(allPrivilegeList
																.get(i).MemberPrivilegeId))) {
													tempPrivilegeList
															.add(allPrivilegeList
																	.get(i));
												}
											}
										}
										if (localMemberLevelId == allLevel.MemberLevelId) {
											isLight = true;
										} else {
											isLight = false;
										}
										mgvMemberServiceAdapter.setDataLevel(
												isLight, tempPrivilegeList);
										setLocalProgress(position);
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
	}

	public void setLocalProgress(int position) {
		pb_member_level.setMax(100);
		if (userGrowthValue <= 0) {
			pb_member_level.setProgress(0);
		}else{
			if ((position + 1) - localMemberLevelId == 1) {
				pb_member_level.setProgress(30);
			} else if ((position + 1) - localMemberLevelId > 1) {
				pb_member_level.setProgress(0);
			} else if ((position + 1) == localMemberLevelId) {
				pb_member_level.setProgress(50);
			} else if (localMemberLevelId - (position + 1) == 1) {
				pb_member_level.setProgress(90);
			} else if (localMemberLevelId - (position + 1) > 1) {
				pb_member_level.setProgress(100);
			}
		}
	}

	private void showPopPhoto(String privilegePic) {
		pWin = null;
		if (pWin == null) {
			final View view = View.inflate(this,
					R.layout.pw_usermemberfragment, null);
			ImageView iv_pw_main_sendpost = (ImageView) view
					.findViewById(R.id.iv_pw_main_sendpost);
			GlideUtil.loadImg(UserMemberActivity.this,privilegePic, iv_pw_main_sendpost, 0);
			ImageButton ib_pw_main_sendpost_close = (ImageButton) view
					.findViewById(R.id.ib_pw_main_sendpost_close);
			pWin = new PopupWindow(view,
					android.view.ViewGroup.LayoutParams.MATCH_PARENT,
					android.view.ViewGroup.LayoutParams.MATCH_PARENT, true);
			pWin.setFocusable(true);
			pWin.setWidth(Utils.getDisplayMetrics(this)[0]);
			pWin.showAtLocation(view, Gravity.CENTER, 0, 0);
			iv_pw_main_sendpost.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
				}
			});
			ib_pw_main_sendpost_close.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (pWin != null) {
						pWin.dismiss();
					}
					pWin = null;
				}
			});
		}
	}

	private void getData() {
		pDialog.showDialog();
		CommUtil.loadMemberInfo(this, MemberHanler);
	}

	private AsyncHttpResponseHandler MemberHanler = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] responseBody) {
			pDialog.closeDialog();
			try {
				JSONObject jobj = new JSONObject(new String(responseBody));
				int code = jobj.getInt("code");
				if (code == 0) {
					if (jobj.has("data") && !jobj.isNull("data")) {
						memberLevelIntroUrl = "";
						localPrivilegeIds = "";
						userLevel = 0;
						localMemberLevelId = 0;
						userGrowthValue = 0;
						memberTime = "";
						JSONObject jdata = jobj.getJSONObject("data");
						if (jdata.has("memberLevelIntro")
								&& !jdata.isNull("memberLevelIntro")) {
							String memberLevelIntro = jdata
									.getString("memberLevelIntro");
							if (memberLevelIntro != null
									&& !TextUtils.isEmpty(memberLevelIntro)
									&& memberLevelIntro.contains("&&")) {
								String[] split = memberLevelIntro.split("&&");
								if (split != null && split.length > 0) {
									hyjsList.clear();
									for (int i = 0; i < split.length; i++) {
										String desStr = split[i];
										String[] split2 = desStr.split("@");
										String des = split2[1];
										String[] split3 = split2[0].split("&");
										String no = split3[0];
										String title = split3[1];
										hyjsList.add(new MemberLevelDes(no,
												title, des));
									}
									mlv_member_hyjs.setVisibility(View.VISIBLE);
									mlv_member_hyjs
											.setAdapter(new MemberLevelDesAdapter<MemberLevelDes>(
													UserMemberActivity.this,
													hyjsList));
								}
							}
						}
						if (jdata.has("memberLevelIntroUrl")
								&& !jdata.isNull("memberLevelIntroUrl")) {
							memberLevelIntroUrl = jdata
									.getString("memberLevelIntroUrl");
						}
						if (jdata.has("userInfo") && !jdata.isNull("userInfo")) {
							jUserInfo = jdata.getJSONObject("userInfo");
							if (jUserInfo.has("userMemberLevel")
									&& !jUserInfo.isNull("userMemberLevel")) {
								JSONObject jUserMemberLevel = jUserInfo
										.getJSONObject("userMemberLevel");
								if (jUserMemberLevel.has("privilegeIds")
										&& !jUserMemberLevel
												.isNull("privilegeIds")) {
									localPrivilegeIds = jUserMemberLevel
											.getString("privilegeIds");
								}
								if (jUserMemberLevel.has("level")
										&& !jUserMemberLevel.isNull("level")) {
									userLevel = jUserMemberLevel
											.getInt("level");
								}
							}
							if (jUserInfo.has("memberLevelId")
									&& !jUserInfo.isNull("memberLevelId")) {
								localMemberLevelId = jUserInfo
										.getInt("memberLevelId");
							}
							if (jUserInfo.has("growthValue")
									&& !jUserInfo.isNull("growthValue")) {
								userGrowthValue = jUserInfo
										.getInt("growthValue");
							}
							if (jUserInfo.has("memberTime")
									&& !jUserInfo.isNull("memberTime")) {
								memberTime = jUserInfo.getString("memberTime");
							}
						}
						if (jdata.has("allPrivilege")
								&& !jdata.isNull("allPrivilege")) {
							JSONArray allPrivilege = jdata
									.getJSONArray("allPrivilege");
							if (allPrivilege.length() > 0) {
								allPrivilegeList.clear();
								for (int i = 0; i < allPrivilege.length(); i++) {
									allPrivilegeList.add(AllPrivilege
											.json2Entity(allPrivilege
													.getJSONObject(i)));
								}
								mgv_member_service.setVisibility(View.VISIBLE);
							}
						}
						if (jdata.has("allLevel") && !jdata.isNull("allLevel")) {
							JSONArray allLevel = jdata.getJSONArray("allLevel");
							if (allLevel.length() > 0) {
								allLevelList.clear();
								for (int i = 0; i < allLevel.length(); i++) {
									allLevelList.add(AllLevel
											.json2Entity(allLevel
													.getJSONObject(i)));
								}
								allLevelList.add(new AllLevel(1024, Utils
										.getTime(), 0, "敬请期待", 0, 0, "", "V0",
										"", ""));
								fcf_member_level.setVisibility(View.VISIBLE);
							}
						}
						memberAdapter.addAll(allLevelList, localMemberLevelId,
								Arrays.asList(unSelectPic),
								Arrays.asList(selectPic),
								Arrays.asList(levelPic));
						int levelPosition = 0;
						for (int i = 0; i < allLevelList.size(); i++) {
							if (allLevelList.get(i).MemberLevelId == localMemberLevelId) {
								levelPosition = i;
								break;
							}
						}
						fcf_member_level.setSelection(levelPosition);
						pb_member_level.setVisibility(View.VISIBLE);
						tempPrivilegeList.clear();
						tempPrivilegeList.addAll(allPrivilegeList);
						tempPrivilegeList.add(new AllPrivilege(1024, 0,
								"drawable://" + R.drawable.member_jqqd, "敬请期待",
								"drawable://" + R.drawable.member_jqqd,
								"drawable://" + R.drawable.member_jqqd));
						mgvMemberServiceAdapter.setDataLevel(false,
								tempPrivilegeList);
						setLoginInfo();
					}
				} else {
					if (jobj.has("msg") && !jobj.isNull("msg")) {
						ToastUtil.showToastShortCenter(UserMemberActivity.this,
								jobj.getString("msg"));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			pDialog.closeDialog();
		}
	};

	private void setView() {
		tvTitle.setText("会员中心");
		fcf_member_level.bringToFront();
		memberAdapter = new MemberFancyCoverFlowAdapter(UserMemberActivity.this);
		fcf_member_level.setAdapter(memberAdapter);
		fcf_member_level.setUnselectedAlpha(1f);// 未被选中的的透明度
		fcf_member_level.setUnselectedSaturation(1f);// 设置未被选中的饱和度
		fcf_member_level.setUnselectedScale(0.1f);// 设置选中的规模
		fcf_member_level.setSpacing(10);// 设置间距
		fcf_member_level.setMaxRotation(0);// 设置最大旋转
		fcf_member_level.setScaleDownGravity(0.5f);// 设置未被选中图片的旋转的角度
		// fancyCoverFlow.setReflectionEnabled(true); // 3D的倒影
		fcf_member_level.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);// 作用距离
		tempPrivilegeList.clear();
		mgvMemberServiceAdapter = new MgvMemberServiceAdapter(
				UserMemberActivity.this, allPrivilegeList);
		mgv_member_service.setAdapter(mgvMemberServiceAdapter);
	}

	class AutoScollTask extends AsyncTask<Void, Integer, Integer> {
		/**
		 * 运行在UI线程中，在调用doInBackground()之前执行
		 */
		@Override
		protected void onPreExecute() {
		}

		/**
		 * 后台运行的方法，可以运行非UI线程，可以执行耗时的方法
		 */
		@Override
		protected Integer doInBackground(Void... params) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * 运行在ui线程中，在doInBackground()执行完毕后执行
		 */
		@Override
		protected void onPostExecute(Integer integer) {
			fcf_member_level.setSelection(ItemSelected - 1);
		}

		/**
		 * 在publishProgress()被调用以后执行，publishProgress()用于更新进度
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
		}
	}

	private void setLoginInfo() {
		boolean checkLogin = Utils.checkLogin(UserMemberActivity.this);
		String text = "";
		String str = "";
		linearlayout1.setVisibility(View.VISIBLE);
		if (checkLogin) {
			tv_member_czz.setText("成长值: " + userGrowthValue);
			tv_member_czz.setVisibility(View.VISIBLE);
			if (localPrivilegeIds != null
					&& !TextUtils.isEmpty(localPrivilegeIds)
					&& localPrivilegeIds.contains(",")) {
				String[] split = localPrivilegeIds.split(",");
				// tv_member_hynum.setVisibility(View.VISIBLE);
				tv_member_hynum.setText(split.length + "项");
			}
			iv_member_nologin.setVisibility(View.GONE);
			rl_member_login.setVisibility(View.VISIBLE);
			int timeCha = Utils.TimeCha(Utils.getTime(), memberTime);
			if (userLevel == 0) {
				String growthValueStr = "";
				String levelName = "";
				for (int i = 0; i < allLevelList.size(); i++) {
					AllLevel allLevel = allLevelList.get(i);
					if (allLevel != null) {
						if (allLevel.level == userLevel + 1) {
							growthValueStr = allLevel.growthValue;
							levelName = allLevel.levelName;
						}
					}
				}
				text = "升级享更多特权>>";
				str = "还差"
						+ (Integer.parseInt(growthValueStr) - userGrowthValue)
						+ "成长值就到" + levelName + "了";
			} else if (userLevel > 0 && userLevel < 3) {
				if (timeCha <= 15) {
					text = "立即补充>>";
					str = "还有" + timeCha + "天您的" + userGrowthValue + "成长值就到期了";
				} else {
					String growthValueStr = "";
					String levelName = "";
					for (int i = 0; i < allLevelList.size(); i++) {
						AllLevel allLevel = allLevelList.get(i);
						if (allLevel != null) {
							if (allLevel.level == userLevel + 1) {
								growthValueStr = allLevel.growthValue;
								levelName = allLevel.levelName;
							}
						}
					}
					text = "升级享更多特权>>";
					str = "还差"
							+ (Integer.parseInt(growthValueStr) - userGrowthValue)
							+ "成长值就到" + levelName + "了";
				}
			} else if (userLevel >= 3) {
				if (timeCha <= 15) {
					text = "立即补充>>";
					str = "还有" + timeCha + "天您的" + userGrowthValue + "成长值就到期了";
				} else {
					str = "充值得返现单单享实惠";
					text = "立即充值>>";
				}
			}
		} else {
			text = "立即登录>>";
			iv_member_nologin.setVisibility(View.VISIBLE);
			rl_member_login.setVisibility(View.GONE);
			str = "登录后查看";
		}
		SpannableString ss = new SpannableString(text);
		// 用下划线标记文本
		ss.setSpan(new UnderlineSpan(), 0, ss.length(),
				Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		tv_member_loginorcz.setText(ss);
		tv_member_des.setText(str);
	}

	private void findView() {
		setContentView(R.layout.usermemberfragment);
		ibBack = (ImageButton) findViewById(R.id.ib_titlebar_back);
		tvTitle = (TextView) findViewById(R.id.tv_titlebar_title);
		tv_member_des = (TextView) findViewById(R.id.tv_member_des);
		tv_member_loginorcz = (TextView) findViewById(R.id.tv_member_loginorcz);
		iv_member_nologin = (ImageView) findViewById(R.id.iv_member_nologin);
		rl_member_hyjs = (RelativeLayout) findViewById(R.id.rl_member_hyjs);
		tv_member_hynum = (TextView) findViewById(R.id.tv_member_hynum);
		mgv_member_service = (MyGridView) findViewById(R.id.mgv_member_service);
		mlv_member_hyjs = (MListview) findViewById(R.id.mlv_member_hyjs);
		rl_member_login = (RelativeLayout) findViewById(R.id.rl_member_login);
		pb_member_level = (ProgressBar) findViewById(R.id.pb_member_level);
		fcf_member_level = (FancyCoverFlow) findViewById(R.id.fcf_member_level);
		tv_member_czz = (TextView) findViewById(R.id.tv_member_czz);
		linearlayout1 = (LinearLayout) findViewById(R.id.linearlayout1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Global.RESULT_OK) {
			switch (requestCode) {
			case RequestCode_UserMember:
				getData();
				break;

			default:
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_member_loginorcz:
			if (Utils.checkLogin(UserMemberActivity.this)) {
				startActivity(new Intent(this, MyLastMoney.class));
			} else {
				startActivityForResult(
						new Intent(this, LoginActivity.class).putExtra(
								"previous", Global.RequestCode_UserMember),
						RequestCode_UserMember);
			}
			break;
		case R.id.rl_member_hyjs:
			if (memberLevelIntroUrl != null
					&& !TextUtils.isEmpty(memberLevelIntroUrl)) {
				startActivity(new Intent(this, ADActivity.class).putExtra(
						"url", memberLevelIntroUrl));
			}
			break;
		case R.id.ib_titlebar_back:
			finish();
			break;
		default:
			break;
		}
	}
}
