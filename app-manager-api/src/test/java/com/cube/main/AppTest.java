package com.cube.main;


import com.alibaba.fastjson.JSON;
import com.cube.service.AppService;
import com.cube.vo.AppVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {

    @Autowired
    private AppService appService;

    /**
     * 查询所有app
     */
    @Test
    public void findAllApp(){
        String memberId = "1";
        List<AppVo> all = appService.findAll(memberId);
        System.out.println(JSON.toJSONString(all));
    }
}
