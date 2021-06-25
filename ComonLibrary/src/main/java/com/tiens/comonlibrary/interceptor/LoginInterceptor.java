package com.tiens.comonlibrary.interceptor;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.tiens.apt_library.PathListUtil;

@Interceptor(priority = 1)
public class LoginInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        Log.e("login",postcard.getPath()+"======");
        if(PathListUtil.getPathList().contains(postcard.getPath())) {
            Log.e("login",postcard.getPath()+"======需要登录");
        }else {
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {

    }
}
