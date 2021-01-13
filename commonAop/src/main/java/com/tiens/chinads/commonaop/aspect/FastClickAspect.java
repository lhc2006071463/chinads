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

package com.tiens.chinads.commonaop.aspect;

import android.util.Log;
import android.view.View;

import com.tiens.chinads.commonaop.annotation.FastClickTrace;
import com.tiens.chinads.commonaop.util.ClickUtils;
import com.tiens.chinads.commonaop.util.Utils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author: lhc
 * @date: 2021-01-12 15:15
 * @description 防止连续点击
 */

@Aspect
public class FastClickAspect {

    @Pointcut("within(@com.tiens.chinads.commonaop.annotation.FastClickTrace *)")
    public void withinAnnotatedClass() {
    }

    @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
    public void methodInsideAnnotatedType() {
    }

    @Pointcut("execution(@com.tiens.chinads.commonaop.annotation.FastClickTrace * *(..)) || methodInsideAnnotatedType()")
    public void method() {
    }  //方法切入点

    @Around("method() && @annotation(fastClickTrace)")//在连接点进行方法替换
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint, FastClickTrace fastClickTrace) throws Throwable {
        View view = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) {
                view = (View) arg;
                break;
            }
        }
        if (view != null) {
            if (!ClickUtils.isFastDoubleClick(view, fastClickTrace.value())) {
                joinPoint.proceed();//不是快速点击，执行原方法
            } else {
                Log.e(this.getClass().getSimpleName(),Utils.getMethodDescribeInfo(joinPoint) + ":发生快速点击，View id:" + view.getId());
            }
        }
    }




}
