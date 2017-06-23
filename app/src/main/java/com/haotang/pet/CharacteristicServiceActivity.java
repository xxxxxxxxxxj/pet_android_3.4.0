package com.haotang.pet;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.haotang.base.SuperActivity;
import com.haotang.pet.adapter.MainCharacteristicServiceAdapter;
import com.haotang.pet.entity.MainCharacteristicService;
import com.haotang.pet.net.AsyncHttpResponseHandler;
import com.haotang.pet.pulltorefresh.PullToRefreshBase;
import com.haotang.pet.pulltorefresh.PullToRefreshBase.Mode;
import com.haotang.pet.pulltorefresh.PullToRefreshListView;
import com.haotang.pet.util.CommUtil;
import com.haotang.pet.util.Global;
import com.haotang.pet.util.MProgressDialog;
import com.haotang.pet.util.SharedPreferenceUtil;
import com.haotang.pet.util.ToastUtil;
import com.haotang.pet.util.Utils;
import com.umeng.analytics.MobclickAgent;

/**
 * <p>
 * Title:CharacteristicServiceActivity
 * </p>
 * <p>
 * Description:特色服务列表界面
 * </p>
 * <p>
 * Company:北京昊唐科技有限公司
 * </p>
 * 
 * @author 徐俊
 * @date 2017-2-14 下午4:59:01
 */
public class CharacteristicServiceActivity extends SuperActivity {
	private ImageButton ib_titlebar_back;
	private TextView tv_titlebar_title;
	private PullToRefreshListView listView_character;
	private MainCharacteristicServiceAdapter characterAdapter;
	private List<MainCharacteristicService> CharactList = new ArrayList<MainCharacteristicService>();
	private MProgressDialog pDialog;
	private SharedPreferenceUtil spUtil;
	public static Activity act;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_character);
		act = this;
		MApplication.listAppoint.add(this);
		spUtil = SharedPreferenceUtil.getInstance(this);
		pDialog = new MProgressDialog(this);
		findView();
		spUtil.saveInt("charactservice", 1);
		getData();
		setData();
	}

	private void setData() {
		tv_titlebar_title.setText("特色服务");
		characterAdapter = new MainCharacteristicServiceAdapter<MainCharacteristicService>(
				this, CharactList);
		listView_character.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
		listView_character
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener() {
					@Override
					public void onRefresh(PullToRefreshBase refreshView) {
						PullToRefreshBase.Mode mode = refreshView
								.getCurrentMode();
						if (mode == Mode.PULL_FROM_START) {
							CharactList.clear();
							characterAdapter.notifyDataSetChanged();
							getData();
						}
					}
				});
		listView_character.setAdapter(characterAdapter);
		initListener();
	}

	private void initListener() {
		ib_titlebar_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				goBack();
			}
		});

		listView_character.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (Utils.checkLogin(CharacteristicServiceActivity.this)
						&& Utils.hasPet(CharacteristicServiceActivity.this)) {
					Intent intent = new Intent(
							CharacteristicServiceActivity.this,
							ServiceFeature.class);
					intent.putExtra("previous", Global.PRE_MAINFRAGMENT);
					intent.putExtra("petid", spUtil.getInt("petid", -1));
					intent.putExtra("shopid", spUtil.getInt("shopid", -1));
					intent.putExtra("servicename", String.valueOf(CharactList
							.get(position - 1).PetServiceTypeForListId));
					intent.putExtra(
							"typeId",
							CharactList.get(position - 1).PetServiceTypeForListId);// 从特色服务跳转到门店列表专用，为了计算价格
					startActivity(intent);
				} else {
					Intent intent = new Intent(
							CharacteristicServiceActivity.this,
							ChoosePetActivityNew.class);
					intent.putExtra("servicename", String.valueOf(CharactList
							.get(position - 1).PetServiceTypeForListId));
					intent.putExtra(
							"typeId",
							CharactList.get(position - 1).PetServiceTypeForListId);// 从特色服务跳转到门店列表专用，为了计算价格
					intent.putExtra("previous",
							Global.SERVICEFEATURE_TO_PETLIST);
					startActivity(intent);
				}
			}
		});
	}

	private void findView() {
		listView_character = (PullToRefreshListView) findViewById(R.id.listView_character);
		ib_titlebar_back = (ImageButton) findViewById(R.id.ib_titlebar_back);
		tv_titlebar_title = (TextView) findViewById(R.id.tv_titlebar_title);
	}

	private void goBack() {
		MApplication.listAppoint.remove(this);
		spUtil.removeData("charactservice");
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			goBack();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void getData() {
		pDialog.showDialog();
		CommUtil.getFe(this, getFe);
	}

	private AsyncHttpResponseHandler getFe = new AsyncHttpResponseHandler() {

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] responseBody) {
			listView_character.onRefreshComplete();
			pDialog.closeDialog();
			try {
				JSONObject jsonObject = new JSONObject(new String(responseBody));
				int code = jsonObject.getInt("code");
				if (code == 0) {
					if (jsonObject.has("data") && !jsonObject.isNull("data")) {
						JSONArray array = jsonObject.getJSONArray("data");
						if (array.length() > 0) {
							for (int i = 0; i < array.length(); i++) {
								CharactList.add(MainCharacteristicService
										.json2Entity(array.getJSONObject(i)));
							}
						}
					}
					characterAdapter.notifyDataSetChanged();
				} else {
					if (jsonObject.has("msg") && !jsonObject.isNull("msg")) {
						ToastUtil.showToastShortCenter(
								CharacteristicServiceActivity.this,
								jsonObject.getString("msg"));
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			listView_character.onRefreshComplete();
			pDialog.closeDialog();
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("CharacteristicServiceActivity");// 统计页面(仅有Activity的应用中SDK自动调用，不需要
		MobclickAgent.onResume(this); // 统计时长
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("CharacteristicServiceActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）
		MobclickAgent.onPause(this);
	}
}
