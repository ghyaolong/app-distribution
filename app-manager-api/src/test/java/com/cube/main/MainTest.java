package com.cube.main;

import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Test;

public class MainTest {
    @Test
    public void test(){
        String sql = "/certificate\\ae6c9ab281486bf8a4ac72cbc843a654\\0\\543b00f4-e5b4-4812-965e-059be8241106.png";
        String s = sql.replaceAll("\\\\", "/");
        //String s = StringEscapeUtils.unescapeJava(sql);
        System.out.println(s);
    }
}
