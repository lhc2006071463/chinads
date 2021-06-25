package com.tiens.comonlibrary.application;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebView;

import androidx.core.graphics.PathUtils;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.tencent.mmkv.MMKV;
//import com.tiens.chinads.commonaop.AOP;
import com.tiens.apt_library.PathListUtil;
import com.tiens.comonlibrary.BuildConfig;
import com.tiens.comonlibrary.R;
import com.tiens.comonlibrary.util.ALog;
import java.util.ArrayList;
import com.tiens.comonlibrary.util.AppExecutors;
import com.tiens.comonlibrary.util.AppUtils;

public abstract class BaseApplication extends MultiDexApplication implements IBaseApplication {
    private static final String PROCESS_NAME = "com.tiens.ChinaDs";
    private static BaseApplication baseContext;
    private AppExecutors mAppExecutors;
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(android.R.color.darker_gray, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseContext = this;
        initALog(this);
        initApps();
        initARoute();
        initMMKV();
//        AOP.init(this);
        initWebView();
        mAppExecutors = new AppExecutors();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    private void initApps() {
        for (String moduleApp: AppConfig.moduleApps) {
            try {
                Class clazz = Class.forName(moduleApp);
                BaseApplication baseApp = (BaseApplication) clazz.newInstance();
                baseApp.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initMMKV() {
        MMKV.initialize(this);
    }

    protected void initARoute() {
        if(BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    protected void initWebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            String processName = AppUtils.getProcessName(this);
            if (!TextUtils.equals(PROCESS_NAME,processName)) {
                WebView.setDataDirectorySuffix(processName);
            }
        }
    }

    public static BaseApplication getAppContext() {
        return baseContext;
    }

    private void initALog(Context context) {
        ALog.init(context)
                .setLogSwitch(BuildConfig.DEBUG)// 设置 log 总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(BuildConfig.DEBUG)// 设置是否输出到控制台开关，默认开
                .setGlobalTag("chinaDS")// 设置 log 全局标签，默认为空
                // 当全局标签不为空时，我们输出的 log 全部为该 tag，
                // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                .setLogHeadSwitch(false)// 设置 log 头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印 log 时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的 /cache/log/ 目录中
                .setFilePrefix("")// 当文件前缀为空时，默认为 "alog"，即写入文件为 "alog-MM-dd.txt"
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setSingleTagSwitch(true)// 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
                .setConsoleFilter(ALog.V)// log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
                .setFileFilter(ALog.V)// log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
                .setStackDeep(2)// log 栈深度，默认为 1
                .setStackOffset(0)// 设置栈偏移，比如二次封装的话就需要设置，默认为 0
                .setSaveDays(3)// 设置日志可保留天数，默认为 -1 表示无限时长
                // 新增 ArrayList 格式化器，默认已支持 Array, Throwable, Bundle, Intent 的格式化输出
                .addFormatter(new ALog.IFormatter<ArrayList>() {
                    @Override
                    public String format(ArrayList list) {
                        return "ALog Formatter ArrayList { " + list.toString() + " }";
                    }
                });
    }

    public AppExecutors getAppExecutors() {
        return mAppExecutors;
    }


}
