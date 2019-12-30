package com.cube.modules.app.controller;

import java.util.Arrays;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cube.modules.app.entity.RechargeLogEntity;
import com.cube.modules.app.service.RechargeLogService;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.R;



/**
 * 充值记录管理
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-20 15:35:40
 */
@RestController
@RequestMapping("app/rechargelog")
public class RechargeLogController {
    @Autowired
    private RechargeLogService rechargeLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = rechargeLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
        RechargeLogEntity rechargeLog = rechargeLogService.getById(id);

        return R.ok().put("rechargeLog", rechargeLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody RechargeLogEntity rechargeLog){
        rechargeLogService.save(rechargeLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody RechargeLogEntity rechargeLog){
        rechargeLogService.updateById(rechargeLog);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] ids){
        rechargeLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
