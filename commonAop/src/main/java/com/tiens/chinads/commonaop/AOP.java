/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tiens.chinads.commonaop;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.tiens.chinads.commonaop.util.PermissionUtils;

/**
 * @author: lhc
 * @date: 2021-01-12 15:33
 * @description
 */

public final class AOP {

    private static Context sContext;

    /**
     * 权限申请被拒绝的监听
     */
    private static PermissionUtils.OnPermissionDeniedListener sOnPermissionDeniedListener;

    /**
     * 初始化
     *
     * @param application
     */
    public static void init(Application application) {
        sContext = application.getApplicationContext();
    }

    /**
     * 获取全局上下文
     *
     * @return
     */
    public static Context getContext() {
        testInitialize();
        return sContext;
    }

    private static void testInitialize() {
        if (sContext == null) {
            throw new ExceptionInInitializerError("请先在Application中调用 AOP.init()");
        }
    }

    /**
     * 设置权限申请被拒绝的监听
     *
     * @param listener 权限申请被拒绝的监听器
     */
    public static void setOnPermissionDeniedListener(@NonNull PermissionUtils.OnPermissionDeniedListener listener) {
        sOnPermissionDeniedListener = listener;
    }

    public static PermissionUtils.OnPermissionDeniedListener getOnPermissionDeniedListener() {
        return sOnPermissionDeniedListener;
    }


}
