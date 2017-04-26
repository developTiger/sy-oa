package com.sunesoft.lemon.deanery;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Created by wangwj on 2016/8/3 0003.
 */
public class StringCommHelper {
    /**
     * 强对象转换成string类型的数据
     * @param obj
     * @return
     */
    public static String nullToString(Object obj){
        if(obj != null && !"".equals(obj.toString().trim()))
            return obj.toString().trim();
        else
            return "";
    }

    /**
     * 将对象转换成Integer类型的数据
     * 如果传进来的数据为null或者是带有空格的字符串则返回0
     * @param obj
     * @return
     */
    public static Integer nullToInteger(Object obj){
        if(obj != null && !"".equals(obj.toString().trim()))
            return Integer.valueOf(obj.toString().trim());
        else
            return 0;
    }
    /**
     * 将对象转换成Long类型的数据
     * 如果传进来的数据为null或者是带有空格的字符串则返回0L
     * @param obj
     * @return
     */
    public static Long nullToLong(Object obj){
        if(obj != null && !"".equals(obj.toString().trim()))
            return Long.valueOf(obj.toString().trim());
        else
            return 0L;
    }

    public static Double nullToDouble(Object obj){
        if(obj != null && !"".equals(obj.toString().trim())){
            return Double.valueOf(obj.toString().trim());
        }else
            return 0.00D;
    }
    public static Boolean nullToBoolean(Object obj){
        if(obj != null && !"".equals(obj.toString().trim())){
            return Boolean.valueOf(obj.toString().trim());
        }else
            return false;
    }
}
