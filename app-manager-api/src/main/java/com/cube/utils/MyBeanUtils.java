package com.cube.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class MyBeanUtils {

    /**
     * @Description: 将poList拷贝到List<T>中
     * @param poList 需要拷贝的对象列表
     * @param voClass 目标List的对象类型
     * @param <T> 返回的目标List的对象类型
     * @return
     */
    public static <T> List<T> copyList (List <? extends Object> poList , Class<T> voClass){
        List voList=new ArrayList();
        Object voObj =null;
        for(Object poObj:poList){
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

    public static <T> List<T> copyList(List <? extends Object> poList , Class<T> voClass, String... ignoreProperties){
        List voList=new ArrayList();
        Object voObj =null;
        for(Object poObj:poList){
            try {
                voObj = voClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            BeanUtils.copyProperties(poObj, voObj,ignoreProperties);
            voList.add(voObj);
        }
        return voList;
    }

    public static <T> T copy (Object o , Class<T> voClass){
        Object voObj =null;
        try {
            voObj = voClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(o, voObj);
        return (T) voObj;
    }

    public static <T> T copy (Object o , Class<T> voClass,String... ignoreProperties){
        Object voObj =null;
        try {
            voObj = voClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(o, voObj,ignoreProperties);
        return (T) voObj;
    }
}
