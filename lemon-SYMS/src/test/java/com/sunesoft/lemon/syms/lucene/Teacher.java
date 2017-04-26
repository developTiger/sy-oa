package com.sunesoft.lemon.syms.lucene;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;

/**
 * Created by temp on 2016/7/14.
 */
public class Teacher {

    //将页面参数动态封装到对象,防止参数丢失
    public static Object modifyVideoEncode(Object object, HttpServletRequest req) {
        if (object == null) {
            return null;
        }
        Enumeration parameterNames = req.getParameterNames();
        Class<?> clazz = object.getClass();
        while (parameterNames.hasMoreElements()) {
            try {
                String name = (String) parameterNames.nextElement();
                String value = req.getParameter(name);
                System.out.println("name:" + name + " value:" + value);
                Method get_Method = clazz.getMethod("get" + getMethodName(name));  //获取getMethod方法
                Method set_Method = clazz.getMethod("set" + getMethodName(name), get_Method.getReturnType());//获得属性set方法
                Class<?> c = get_Method.getReturnType();
                String type = c.getName();
                if (type.equals("long")) set_Method.invoke(object, Long.valueOf(value));  //对于类型 long
                else if (type.equals("int")) set_Method.invoke(object, Integer.valueOf(value)); //对于int 类型
                else set_Method.invoke(object, c.cast(value));//其他类型调用class.cast方法
            } catch (Exception e) {

            }
        }
        return object;
    }


    // 把一个字符串的第一个字母大写、效率是最高的、
    private static String getMethodName(String fildeName) {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }
}
