package com.tiens.comonlibrary.util;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityOptionsCompat;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

public class RouteUtil {
    public static void navigation(String path) {
        ARouter.getInstance().build(path).navigation();
    }

    public static void navigation(Uri uri) {
        ARouter.getInstance().build(uri).navigation();
    }

    public static void navigation(String path, Bundle bundle) {
        ARouter.getInstance().build(path)
                .with(bundle)
                .navigation();
    }

    public static void navigation(Uri uri, Bundle bundle) {
        ARouter.getInstance().build(uri)
                .with(bundle)
                .navigation();
    }

    public static void navigation(String path, int flag) {
        ARouter.getInstance().build(path).withFlags(flag).navigation();
    }

    public static void navigationForResult(Activity context, int requestCode, String path) {
        ARouter.getInstance()
                .build(path)
                .navigation(context, requestCode);
    }

    public static void navigationForResult(Activity context, int requestCode, String path, Bundle bundle) {
        ARouter.getInstance().build(path)
                .with(bundle)
                .navigation(context, requestCode);
    }

    public static void navigationWithSharedElement(Activity context, String path, Bundle bundle, ActivityOptionsCompat optionsCompat) {
        Postcard postcard = ARouter.getInstance().build(path)
                .withOptionsCompat(optionsCompat);
        if (bundle != null)
            postcard.with(bundle);
        postcard.navigation(context);

    }

    public static void navigationWithSharedElement(Activity context, int requestCode, String path, Bundle bundle, ActivityOptionsCompat optionsCompat) {
        Postcard postcard = ARouter.getInstance().build(path)
                .withOptionsCompat(optionsCompat);
        if (bundle != null)
            postcard.with(bundle);
        postcard.navigation(context, requestCode);

    }

}
