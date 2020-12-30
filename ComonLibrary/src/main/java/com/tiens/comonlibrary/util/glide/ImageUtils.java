package com.tiens.comonlibrary.util.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tiens.comonlibrary.application.BaseApplication;
import com.tiens.comonlibrary.util.UIUtil;


/**
 * @author: lhc
 * @date: 2020-12-28 16:15
 * @description 图片加载工具类
 */

public class ImageUtils {

    @BindingAdapter({"imgUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        GlideApp.with(context)
                .load(imageUrl)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .dontAnimate()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view);
    }

    @BindingAdapter({"imgUrl", "placeHolder"})
    public static void loadImage(ImageView view, String imageUrl, int placeHolder) {
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        GlideApp.with(context)
                .load(imageUrl)
                .placeholder(placeHolder)
                .error(placeHolder)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .dontAnimate()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view);
    }

    @BindingAdapter({"imgRoundUrl"})
    public static void loadRoundCornerImage(ImageView view, String imageUrl) {
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        if (context instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (((Activity) context).isFinishing() || ((Activity) context).isDestroyed()) {
                    return;
                }
            }
        }
        GlideApp.with(context)
                .load(imageUrl)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(UIUtil.dip2px(context, 4))))
                .into(view);

    }

    @BindingAdapter(value = {"imgRoundUrl", "corner"})
    public static void loadRoundCornerImage(ImageView view, String imageUrl, int corner) {
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing() || ((Activity) context).isDestroyed()) {
                return;
            }
        }
        GlideApp.with(context)
                .load(imageUrl)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(UIUtil.dip2px(context, corner))))
                .into(view);
    }

    @BindingAdapter(value = {"imgRoundUrl", "corner"})
    public static void loadRoundCornerImage(ImageView view, Drawable draw, int corner) {
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        if (context instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (((Activity) context).isFinishing() || ((Activity) context).isDestroyed()) {
                    return;
                }
            }
        }
        GlideApp.with(context)
                .load(draw)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(UIUtil.dip2px(context, corner))))
                .into(view);
    }

    @BindingAdapter({"imgCircleUrl"})
    public static void loadCircleImg(ImageView view, String url) {
        // 不能崩
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        // View你还活着吗？
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing() || ((Activity) context).isDestroyed()) {
                return;
            }
        }
        try {
            GlideApp.with(context)
                    .load(url)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .transform(new GlideCircleTransform())
                    .into(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadCircleImage(ImageView view, String url, int placeHolder) {
        // 不能崩
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        // View你还活着吗？
        if (context instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (((Activity) context).isFinishing() || ((Activity) context).isDestroyed()) {
                    return;
                }
            }
        }
        try {
            GlideApp.with(context)
                    .load(url)
                    .centerCrop()
                    .placeholder(placeHolder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .transform(new GlideCircleTransform())
                    .into(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadResource(ImageView view, int drawable) {
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        Glide.with(context)
                .load(drawable)
                .into(view);
    }


    public static void preload(String imageUrl) {
        Glide.with(BaseApplication.getAppContext())
                .asBitmap()
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .preload();
    }

    public static void loadAsBitmap(ImageView view, String url) {
        if(view==null) return;
        Context context = view.getContext();
        if(context==null) return;
        Glide.with(context)
                .asBitmap()
                .skipMemoryCache(true)
                .dontAnimate()
                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                        layoutParams.width = UIUtil.getScreenWidth(context);
                        layoutParams.height = layoutParams.width * resource.getWidth() / resource.getHeight();
                        view.setLayoutParams(layoutParams);
                        view.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

}
