package com.tiens.comonlibrary.util.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class GlideCircleTransform extends BitmapTransformation {
    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap inBitmap, int destWidth, int destHeight) {
        if (inBitmap == null) return null;

        int size = Math.min(inBitmap.getWidth(), inBitmap.getHeight());
        int x = (inBitmap.getWidth() - size) / 2;
        int y = (inBitmap.getHeight() - size) / 2;

        Bitmap squared = Bitmap.createBitmap(inBitmap, x, y, size, size);
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
    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}