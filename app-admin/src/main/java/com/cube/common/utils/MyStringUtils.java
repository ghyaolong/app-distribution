package com.cube.common.utils;

import org.apache.commons.lang.StringUtils;

public class MyStringUtils {

    public static boolean isEmpty(Object object){
        if(object!=null){
            if(!StringUtils.isEmpty(object.toString())){
                return false;
            }
            return true;
        }
        return true;
    }
}
