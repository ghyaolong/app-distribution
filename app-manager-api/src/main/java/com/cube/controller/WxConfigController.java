package com.cube.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cube.entity.WxConfigEntity;
import com.cube.service.WxConfigService;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.R;



/**
 * 
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-11-20 17:35:42
 */
@RestController
@RequestMapping("cube/wxconfig")
public class WxConfigController {
    @Autowired
    private WxConfigService wxConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")

    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wxConfigService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
        WxConfigEntity wxConfig = wxConfigService.getById(id);

        return R.ok().put("wxConfig", wxConfig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody WxConfigEntity wxConfig){
        wxConfigService.save(wxConfig);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody WxConfigEntity wxConfig){
        wxConfigService.updateById(wxConfig);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] ids){
        wxConfigService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
