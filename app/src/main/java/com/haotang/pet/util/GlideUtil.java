package com.haotang.pet.util;

import java.io.File;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.haotang.pet.R;
import com.haotang.pet.view.GlideCircleTransform;

public class GlideUtil {
	private Context mContext;
	// 显示数据
	private int width = (int) TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_DIP, 200, mContext.getResources()
					.getDisplayMetrics());
	private int height = (int) TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_DIP, 200f, mContext.getResources()
					.getDisplayMetrics());

	public void load(Context mContext, String url, ImageView image) {
		Glide.with(mContext).load(url).into(image);

		// （2）加载资源图片
		Glide.with(mContext).load(R.drawable.logo).into(image);

		// （3）加载本地图片
		String path = Environment.getExternalStorageDirectory() + "/meinv1.jpg";
		File file = new File(path);
		Uri uri = Uri.fromFile(file);
		Glide.with(mContext).load(uri).into(image);

		// （4）加载网络gif
		String gifUrl = "http://b.hiphotos.baidu.com/zhidao/pic/item/faedab64034f78f066abccc57b310a55b3191c67.jpg";
		Glide.with(mContext).load(gifUrl).placeholder(R.drawable.ic_launcher)
				.into(image);

		// （5）加载资源gif
		Glide.with(mContext).load(R.drawable.loading).asGif()
				.placeholder(R.drawable.ic_launcher).into(image);

		// （6）加载本地gif
		String gifPath = Environment.getExternalStorageDirectory()
				+ "/meinv2.jpg";
		File gifFile = new File(gifPath);
		Glide.with(mContext).load(gifFile).placeholder(R.drawable.ic_launcher)
				.into(image);

		// （7）加载本地小视频和快照
		String videoPath = Environment.getExternalStorageDirectory()
				+ "/video.mp4";
		File videoFile = new File(videoPath);
		Glide.with(mContext).load(Uri.fromFile(videoFile))
				.placeholder(R.drawable.ic_launcher).into(image);

		// （8）设置缩略图比例,然后，先加载缩略图，再加载原图
		String urlPath = Environment.getExternalStorageDirectory()
				+ "/meinv1.jpg";
		Glide.with(mContext).load(new File(urlPath)).thumbnail(0.1f)
				.centerCrop().placeholder(R.drawable.ic_launcher).into(image);

		// （9）先建立一个缩略图对象，然后，先加载缩略图，再加载原图
		DrawableRequestBuilder thumbnailRequest = Glide.with(mContext).load(
				new File(urlPath));
		Glide.with(mContext).load(Uri.fromFile(videoFile))
				.thumbnail(thumbnailRequest).centerCrop()
				.placeholder(R.drawable.ic_launcher).into(image);

		Glide.with(mContext).load(url).placeholder(R.drawable.ic_launcher) // 占位图
				.error(R.drawable.ic_launcher) // 出错的占位图
				.override(width, height) // 图片显示的分辨率 ，像素值 可以转化为DP再设置
				.animate(R.anim.anim_marquee_in)// 图片加载动画
				.centerCrop()// CenterCrop()会缩放图片让图片充满整个ImageView的边框，然后裁掉超出的部分。ImageVIew会被完全填充满，但是图片可能不能完全显示出。
				.fitCenter()// fitCenter()会缩放图片让两边都相等或小于ImageView的所需求的边框。图片会被完整显示，可能不能完全填充整个ImageView。
				.into(image);
	}

	public static void loadImg(Context mContext, String imgUrl,
			ImageView imageView, int placeholderResourceId) {
		String localImgUrl = "";
		if (imgUrl != null && !TextUtils.isEmpty(imgUrl)) {
			if (imgUrl.startsWith("drawable://")) {
				localImgUrl = imgUrl;
			} else if (imgUrl.startsWith("file://")) {
				localImgUrl = imgUrl;
			} else {
				if (!imgUrl.startsWith("http://")
						&& !imgUrl.startsWith("https://")) {
					localImgUrl = CommUtil.getServiceNobacklash() + imgUrl;
				} else {
					localImgUrl = imgUrl;
				}
			}
		}
		try {
			Glide.with(mContext).load(localImgUrl)
					.placeholder(placeholderResourceId) // 占位图
					.error(placeholderResourceId) // 出错的占位图
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					.into(imageView);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void loadCircleImg(Context mContext, String imgUrl,
			ImageView imageView, int placeholderResourceId) {
		String localImgUrl = "";
		if (imgUrl != null && !TextUtils.isEmpty(imgUrl)) {
			if (imgUrl.startsWith("drawable://")) {
				localImgUrl = imgUrl;
			} else if (imgUrl.startsWith("file://")) {
				localImgUrl = imgUrl;
			} else {
				if (!imgUrl.startsWith("http://")
						&& !imgUrl.startsWith("https://")) {
					localImgUrl = CommUtil.getServiceNobacklash() + imgUrl;
				} else {
					localImgUrl = imgUrl;
				}
			}
		}
		try {
			Glide.with(mContext).load(localImgUrl)
					.placeholder(placeholderResourceId) // 占位图
					.error(placeholderResourceId) // 出错的占位图
					.bitmapTransform(new GlideCircleTransform(mContext))
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					.into(imageView);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
