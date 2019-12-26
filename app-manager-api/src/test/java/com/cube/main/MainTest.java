package com.cube.main;

import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;

public class MainTest {
    @Test
    public void test(){
        String sql = "/certificate\\ae6c9ab281486bf8a4ac72cbc843a654\\0\\543b00f4-e5b4-4812-965e-059be8241106.png";
        String s = sql.replaceAll("\\\\", "/");
        //String s = StringEscapeUtils.unescapeJava(sql);
        System.out.println(s);
    }

    @Test
    public void test1() throws UnknownHostException {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        System.out.println(hostAddress);
    }
    @Test
    public void test3(){
        String aa ="http://jhfenfa.oss-cn-hongkong.aliyuncs.com/04afe3b17ce560731151e1a055c74433/1576655741805794.apk";
        String substring = aa.substring(aa.indexOf(".com")+5);
        System.out.println(substring);
    }


    @Test
    public void test2(){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("price","1.2");
        list.add(map);
        list.add(map);
        list.add(map);
        Optional<Double> price = list.stream().map((str) -> Double.valueOf(str.get("price").toString())).reduce(Double::sum);
        System.out.println(price.get());
    }
}
