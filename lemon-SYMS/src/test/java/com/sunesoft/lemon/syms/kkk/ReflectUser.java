package com.sunesoft.lemon.syms.kkk;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by temp on 2016/7/14.
 */
public class ReflectUser {
    public static void main(String[] args) throws Exception {
        /*
         * 实列化类 方法2
         */
        Project bean = new Project();
        // bean.setAge(100);
        // bean.setBirthday(new Date());
        // bean.setName("武汉");

        // 得到类对象
        @SuppressWarnings("rawtypes")
        Class cls = (Class) bean.getClass();

        /*
         * 得到类中的所有属性集合
         */
        Field[] fs = cls.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置些属性是可以访问的
            // Object val = f.get(bean);// 得到此属性的值

            // System.out.println("name:" + f.getName() + "\t value = " + val);

            String type = f.getType().toString();// 得到此属性的类型
            if (type.endsWith("String")) {
                // System.out.println(f.getType() + "\t是String");
                f.set(bean, "12");        // 给属性设值
            } else if (type.endsWith("int") || type.endsWith("Integer")) {
                // System.out.println(f.getType() + "\t是int");
                f.set(bean, 12);       // 给属性设值
            } else {
                // System.out.println(f.getType() + "\t");
            }

        }

        /*
         * 得到类中的方法
         */
        Method[] methods = cls.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            // if (method.getName().startsWith("get")) {
            // System.out.print("methodName:" + method.getName() + "\t");
            // System.out.println("value:" + method.invoke(bean));// 得到get 方法的值
            // }

            if (method.getName().startsWith("set")) {
                System.out.print(firstToLower(cls.getSimpleName()) + "." + method.getName() + "(node.getChildText(\""
                        + firstToLower(method.getName().substring(3)) + "\"));\n");
            }
        }
    }

    /**
     * @param val
     * @return
     */
    public static String firstToLower(String val) {
        return val.substring(0, 1).toLowerCase() + val.substring(1);
    }
}
