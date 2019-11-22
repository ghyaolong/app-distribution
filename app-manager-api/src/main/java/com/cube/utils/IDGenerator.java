package com.cube.utils;

import java.util.UUID;

public class IDGenerator {

    public static String getUUID(){
        String id = UUID.randomUUID().toString().replace("-", "");
        return id;
    }
}
