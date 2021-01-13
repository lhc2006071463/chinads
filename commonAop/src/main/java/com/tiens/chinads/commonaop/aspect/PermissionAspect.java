package com.tiens.chinads.commonaop.aspect;

import android.util.Log;

import com.tiens.chinads.commonaop.AOP;
import com.tiens.chinads.commonaop.annotation.PermissionTrace;
import com.tiens.chinads.commonaop.util.PermissionUtils;
import com.tiens.chinads.commonaop.util.Utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;

/**
 * @author: lhc
 * @date: 2021-01-12 15:25
 * @description 权限处理
 */

@Aspect
public class PermissionAspect {
    private static final String TAG = "PermissionAspectJ";
    @Pointcut("within(@com.tiens.chinads.commonaop.annotation.PermissionTrace *)")
    public void withinAnnotatedClass() {
    }

    @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
    public void methodInsideAnnotatedType() {
    }

    @Pointcut("execution(@com.tiens.chinads.commonaop.annotation.PermissionTrace * *(..)) || methodInsideAnnotatedType()")
    public void method() {
    }  //方法切入点

    @Around("method() && @annotation(permission)")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint, PermissionTrace permission) throws Throwable {
        PermissionUtils.permission(permission.value())
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        try {
                            joinPoint.proceed();//获得权限，执行原方法
                        } catch (Throwable e) {
                            e.printStackTrace();
                            Log.e(TAG,e.getMessage());
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        Log.e(TAG,"权限申请被拒绝:" + Utils.listToString(permissionsDenied));
                        if (AOP.getOnPermissionDeniedListener() != null) {
                            AOP.getOnPermissionDeniedListener().onDenied(permissionsDenied);
                        }
                    }
                })
                .request();
    }



}


