package com.tiens.chinads.commonaop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface PermissionTrace {
    /**
     * @return 需要申请权限的集合
     */
    String[] value();

    /**
     * @return 申请权限的页面名称
     */
    String pageName();
}