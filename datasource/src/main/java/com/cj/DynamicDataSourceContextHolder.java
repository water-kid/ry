package com.cj;

public class DynamicDataSourceContextHolder {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setDataSourceType(String dsType){
        threadLocal.set(dsType);
    }

    public static String getDataSourceType(){
        return threadLocal.get();
    }

    public static void clearDataSourceType(){
        threadLocal.remove();
    }
}
