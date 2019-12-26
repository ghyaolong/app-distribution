package com.cube.main;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;

public class FileTest {

    @Test
    public void testAccesFile(){
        try {
            RandomAccessFile ra  = new RandomAccessFile("d:\\employee.txt" , "rw");
            ra.write("zhangsan".getBytes());
            ra.write(23);
            ra.write("lisi".getBytes());
            ra.write(24);
            ra.write("wangwu".getBytes());
            ra.write(25);
            ra.close();

            //读

            RandomAccessFile raf = new RandomAccessFile("d:\\employee.txt" , "r");
            int len = 8;
            raf.skipBytes(12);//跳过第一个员工的信息，其姓名8字节，年龄4字节
            System.out.println("第二个员工信息：");
            String str = "";
            for (int i = 0 ; i < len ; i++){
                str = str + (char)raf.readByte();
            }
            System.out.println("name:" + str);
            System.out.println("age:" + raf.readInt());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
