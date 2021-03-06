package com.haotang.pet.fragment;

import java.io.File;

import com.haotang.pet.MainActivity;
import com.haotang.pet.R;
import com.haotang.pet.util.GlideUtil;
import com.haotang.pet.util.ImageLoaderUtil;
import com.haotang.pet.util.MProgressDialog;
import com.haotang.pet.util.Utils;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class BeauticianProductionDetailFragment extends Fragment {
	private Activity mActivity;
	private TextView tvDes;
	private ImageView ivImage;
	private String mPath;
	private String mDes;
	private String mSmallImage;
	private MProgressDialog pDialog;
	
	public void setData(String path,String des,String smallimage){
		this.mPath = path;
		this.mDes = des;
		this.mSmallImage = smallimage;
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
		pDialog = new MProgressDialog(mActivity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		findView(view);
	}
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.beauticianproductiondetailfragment, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setViw();
	}
	
	private void findView(View view) {
		tvDes = (TextView) view.findViewById(R.id.tv_beauticianproduction_detail);
		ivImage = (ImageView) view.findViewById(R.id.iv_beauticianproduction_detail);
	}

	private void setViw() {
		if(mDes!=null&&!"".equals(mDes.trim())){
			tvDes.setVisibility(View.VISIBLE);
			tvDes.setText(mDes);
		}else{
			tvDes.setVisibility(View.GONE);
			tvDes.setText("");
		}
		if(mPath!=null&&!"".equals(mPath.trim())){
			pDialog.showDialog();
			GlideUtil.loadImg(mActivity,mPath, ivImage,R.drawable.icon_production_default);
		}
	}
}
