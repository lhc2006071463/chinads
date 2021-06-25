package com.tiens.apt_library;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PathListUtil {
    private static List<String> pathList = new ArrayList<>();
    private static String commonPrefix = "com.tiens.vshare.PathUtil$";
    private static String[] classNames = {
            commonPrefix + "app",
            commonPrefix + "owner"
    };
    public static List<String> getPathList() {
        if(pathList.isEmpty()) {
            try {
                for(String className: classNames) {
                    Class<?> aClass = Class.forName(className);
                    Method method = aClass.getMethod("getPathList");
                    List<String> paths = (List<String>) method.invoke(null);
                    pathList.addAll(paths);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pathList;
    }
}
