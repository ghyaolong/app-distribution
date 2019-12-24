package com.cube.utils.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cube.common.exception.RRException;
import com.cube.utils.HttpUtil;

import java.util.HashMap;
import java.util.Map;

public class SMSUtils {
    //请求地址
    public static final String url="http://intapi.253.com/send/json";
    //API账号，50位以内。必填
    public static final String account="I451513_I5249050";
    //API账号对应密钥，联系客服获取。必填
    public static final String password="byOXp8z2Ud0184";

    /**
     *
     * @param verificationStr
     * @param phoneNum
     * @param phoneCode
     * @return
     */
    public static Boolean sendSms(String verificationStr,String phoneNum,String phoneCode){
        //String msg="【UB网】您本次操作验证码为"+verificationStr+",有效期5分钟，如非本人操作请忽略！";
        String msg = "【久惠科技】验证码："+verificationStr+"，请在60秒内验证，如非本人操作，请忽略本短信。";
        //手机号码，格式(区号+手机号码)，例如：8615800000000，其中86为中国的区号，区号前不使用00开头,15800000000为接收短信的真实手机号码。5-20位。必填
        String mobile=phoneCode+phoneNum;
        //组装请求参数
        JSONObject map=new JSONObject();
        map.put("account", account);
        map.put("password", password);
        map.put("msg", msg);
        map.put("mobile", mobile);
        String params=map.toString();
        Map<String,String> jsonMap=new HashMap<String,String>();
        try {
            String result= HttpUtil.post(url, params);
            JSONObject jsonObject =  JSON.parseObject(result);
            String code = jsonObject.get("code").toString();
            String msgid = jsonObject.get("msgid").toString();
            String error = jsonObject.get("error").toString();
            if(!"0".equals(code)){
                throw new RRException(error);
            }
            jsonMap.put("code", code);
            jsonMap.put("msgid", msgid);
            jsonMap.put("error", error);
            //return JSONObject.toJSON(jsonMap).toString();
            return true;

        } catch (Exception e) {
            // TODO: handle exception
            jsonMap.put("code", "500");
            jsonMap.put("message", "获取短信异常!");
            throw new RRException("获取短信异常!");
            //return JSONObject.toJSON(jsonMap).toString();
        }
    }

    public static void main(String[] args) {
        Boolean result= SMSUtils.sendSms(String.valueOf(12311), "17792073879","86");
        System.out.println(result);
    }
}
