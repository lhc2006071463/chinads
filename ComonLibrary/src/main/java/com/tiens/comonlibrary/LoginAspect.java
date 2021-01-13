package com.tiens.comonlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tiens.chinads.commonaop.annotation.LoginTrace;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author: lhc
 * @date: 2021-01-12 19:57
 * @description 登录拦截校验
 */

@Aspect
public class LoginAspect {
    //带有LoginTrace注解的所有类
    @Pointcut("within(@com.tiens.chinads.commonaop.annotation.LoginTrace *)")
    public void withinAnnotatedClass() {
    }

    //带有LoginTrace注解的所有类，除去synthetic修饰的方法
    @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
    public void methodInsideAnnotatedType() {
    }

    //带有DebugLog注解的方法
    @Pointcut("execution(@com.tiens.chinads.commonaop.annotation.LoginTrace * *(..)) || methodInsideAnnotatedType()")
    public void method() {
    }  //方法切入点

    @Around("method() && @annotation(loginTrace)")
    public Object doLoginMethod(ProceedingJoinPoint joinPoint, LoginTrace loginTrace)throws Throwable {
        if(true) {
            ARouter.getInstance().build("/Main/MainActivity").navigation();
            return null;
        }

        return joinPoint.proceed();
    }

    /**
     * 通过对象获取上下文
     * @param object
     * @return
     */
    private Context getContext(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof android.app.Fragment) {
            android.app.Fragment fragment = (android.app.Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof View) {
            View view = (View) object;
            return view.getContext();
        }
        return null;
    }
}
