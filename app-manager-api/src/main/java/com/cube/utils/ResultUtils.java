package com.cube.utils;

/**
 * Result工具类
 */
public class ResultUtils {

    public static Result ok(){
        Result result = new Result();
        result.setCode("200");
        result.setMessage("");
        return result;
    }

    public static Result ok(Object data){
        Result result = new Result();
        result.setCode("200");
        result.setMessage("");
        result.setData(data);
        return result;
    }

//    public static Result ok(String msg){
//        Result result = new Result();
//        result.setCode("200");
//        result.setMessage(msg);
//        return result;
//    }

        public static Result okMsg(String msg){
        Result result = new Result();
        result.setCode("200");
        result.setMessage(msg);
        return result;
    }

    public static Result ok(String msg,String object){
        Result result = new Result();
        result.setCode("200");
        result.setData(object);
        result.setMessage(msg);
        return result;
    }

    public static Result error(Object data,String errMsg){
        Result result = new Result();
        result.setCode("500");
        result.setMessage(errMsg);
        result.setData(data);
        return result;
    }

    public static Result error(String code,String errMsg){
        Result result = new Result();
        result.setCode(code);
        result.setMessage(errMsg);
        return result;
    }
}
