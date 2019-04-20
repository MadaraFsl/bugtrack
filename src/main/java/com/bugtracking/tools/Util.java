package com.bugtracking.tools;


import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Util {

    /**************************************************************************
     * @Description 通用将Hibernate转换为对应VO类方法
     * 使用时注意：VO类中属性必须和对象sql查询结果集顺序一致，其类型必须对应。
     * 目前常用数据库中类型与Java类型对应关系：int = Integer; varchar = String; datetime = Timestamp; double = Double; decimal = BigDecimal;  可后续添加
     * @param objects 需要转换的list
     * @param T 将要转换的vo对象(new xxxVO)
     **************************************************************************/

    public static List transferObjectsToList(List<Object[]> objects, Class T) {

        List list = new ArrayList<>();

        for (Object[] os : objects) {
            Object o = null;
            try {
                o = T.newInstance();
                Field[] fields = T.getDeclaredFields();

                for (int j = 0; j < fields.length; j++) {
                    if (os[j] != null) {
                        String field = fields[j].getName();
                        fields[j].setAccessible(true);
                        try {
                            //区分数据库中datetime类型 如有其他类型特殊处理，可后续添加
                            if (os[j] instanceof Timestamp) {
                                //对应object[] 中对象为Timestamp类型，可直接toString
                                os[j] = os[j].toString();
                            }
                            fields[j].set(o, os[j]);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            list.add(o);
        }
        return list;
    }

}
