package com.cube.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyBeanUtils {

    /**
     * @param poList  需要拷贝的对象列表
     * @param voClass 目标List的对象类型
     * @param <T>     返回的目标List的对象类型
     * @return
     * @Description: 将poList拷贝到List<T>中
     */
    public static <T> List<T> copyList(List<? extends Object> poList, Class<T> voClass) {
        List voList = new ArrayList();
        Object voObj = null;
        for (Object poObj : poList) {
            try {
                voObj = voClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            BeanUtils.copyProperties(poObj, voObj);
            voList.add(voObj);
        }
        return voList;
    }

    public static <T> List<T> copyList(List<? extends Object> poList, Class<T> voClass, String... ignoreProperties) {
        List voList = new ArrayList();
        Object voObj = null;
        for (Object poObj : poList) {
            try {
                voObj = voClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            BeanUtils.copyProperties(poObj, voObj, ignoreProperties);
            voList.add(voObj);
        }
        return voList;
    }

    public static <T> T copy(Object o, Class<T> voClass) {
        Object voObj = null;
        try {
            voObj = voClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(o, voObj);
        return (T) voObj;
    }

    public static <T> T copyIgnoreNull(Object o, Class<T> voClass) {
        Object voObj = null;
        try {
            voObj = voClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(o, voObj, getNullPropertyNames(o));
        return (T) voObj;
    }

    public static void  copyIgnoreNull(Object o, Object o2) {
        BeanUtils.copyProperties(o, o2, getNullPropertyNames(o));
    }
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static <T> T copy(Object o, Class<T> voClass, String... ignoreProperties) {
        Object voObj = null;
        try {
            voObj = voClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(o, voObj, ignoreProperties);
        return (T) voObj;
    }
}
