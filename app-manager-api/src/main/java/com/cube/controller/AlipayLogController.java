package com.cube.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cube.entity.AlipayLogEntity;
import com.cube.service.AlipayLogService;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.R;



/**
 * 支付宝支付日志
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-11-20 17:35:43
 */
@RestController
@RequestMapping("cube/alipaylog")
public class AlipayLogController {
    @Autowired
    private AlipayLogService alipayLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")

    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = alipayLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
        AlipayLogEntity alipayLog = alipayLogService.getById(id);

        return R.ok().put("alipayLog", alipayLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AlipayLogEntity alipayLog){
        alipayLogService.save(alipayLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AlipayLogEntity alipayLog){
        alipayLogService.updateById(alipayLog);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] ids){
        alipayLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
