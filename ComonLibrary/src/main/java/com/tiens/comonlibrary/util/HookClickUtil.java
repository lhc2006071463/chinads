package com.tiens.comonlibrary.util;

import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;

public class HookClickUtil {
    public static void hookView(View view) {
        try {
            Class vClazz = Class.forName("android.view.View");
            Method listenerInfoMethod = vClazz.getDeclaredMethod("getListenerInfo");
            if(!listenerInfoMethod.isAccessible()) {
                listenerInfoMethod.setAccessible(true);
            }
            Object listenerObj = listenerInfoMethod.invoke(view);
            Class<?> listenerInfoClazz = Class.forName("android.view.View$ListenerInfo");
            Field mOnClickListenerField = listenerInfoClazz.getDeclaredField("mOnClickListener");
            if(!mOnClickListenerField.isAccessible()) {
                mOnClickListenerField.setAccessible(true);
            }
            View.OnClickListener onClickListener = (View.OnClickListener) mOnClickListenerField.get(listenerObj);
            View.OnClickListener clickListenerProxy = new OnClickListenerProxy(onClickListener);
            mOnClickListenerField.set(listenerObj, clickListenerProxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class OnClickListenerProxy implements View.OnClickListener {
        private final View.OnClickListener obj;
        private static final int MIN_CLICK_DELAY_TIME = 8000;
        private long lastClickTime = 0;

        private OnClickListenerProxy(View.OnClickListener listener) {
            this.obj = listener;
        }

        @Override
        public void onClick(View v) {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if(currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                Log.e("Tag","----完成了onClickHook");
                lastClickTime = currentTime;
                if(obj!=null) obj.onClick(v);
            }else {
                Log.e("Tag","----完成了快速点击拦截");
            }
        }
    }
}
