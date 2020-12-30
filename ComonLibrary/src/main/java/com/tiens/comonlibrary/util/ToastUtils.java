package com.tiens.comonlibrary.util;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.tiens.comonlibrary.application.BaseApplication;

/**
 * Toast工具类
 */

public class ToastUtils {
    /**
     * Toast实例，用于对出现的所有Toast进行处理
     */
    private static Toast toast;

    /**
     * 此处是一个封装的Toast方法，可以取消掉上一次未完成的，直接进行下一次Toast * @param context context * @param text 需要toast的内容
     */
    public static void showToast(String text, int duration) {
        if(TextUtils.isEmpty(text)){
            return;
        }

        if (toast != null) {
            toast.cancel();
            toast = Toast.makeText(BaseApplication.getAppContext(), text, duration);
        } else {
            toast = Toast.makeText(BaseApplication.getAppContext(), text, duration);
        }
        toast.show();
    }

    public static void showToastCenter(String text, int duration) {
        if(TextUtils.isEmpty(text)){
            return;
        }

        if (toast != null) {
            toast.cancel();
            toast = Toast.makeText(BaseApplication.getAppContext(), text, duration);
        } else {
            toast = Toast.makeText(BaseApplication.getAppContext(), text, duration);
        }
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

}
