package com.haotang.pet.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

public class GlideCircleTransform extends BitmapTransformation {

	
	public GlideCircleTransform(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public GlideCircleTransform(BitmapPool bitmapPool) {
		super(bitmapPool);
		// TODO Auto-generated constructor stub
	
	}
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return getClass().getName();
	}

	@Override
	protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
		// TODO Auto-generated method stub
		return circleCrop(pool, toTransform);
	}
	
	 private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
	        if (source == null) return null;

	        int size = Math.min(source.getWidth(), source.getHeight());
	        int x = (source.getWidth() - size) / 2;
	        int y = (source.getHeight() - size) / 2;

	        // TODO this could be acquired from the pool too
	        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

	        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
	        if (result == null) {
	            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
	        }

	        Canvas canvas = new Canvas(result);
	        Paint paint = new Paint();
	        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
	        paint.setAntiAlias(true);
	        float r = size / 2f;
	        canvas.drawCircle(r, r, r, paint);
	        return result;
	    }

	   

	

	
}