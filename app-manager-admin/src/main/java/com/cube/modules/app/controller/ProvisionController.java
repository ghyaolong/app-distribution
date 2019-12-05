package com.cube.modules.app.controller;

import java.util.Arrays;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cube.modules.app.entity.ProvisionEntity;
import com.cube.modules.app.service.ProvisionService;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.R;



/**
 * IOS文件描述
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-05 10:57:12
 */
@RestController
@RequestMapping("app/provision")
public class ProvisionController {
    @Autowired
    private ProvisionService provisionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = provisionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
        ProvisionEntity provision = provisionService.getById(id);

        return R.ok().put("provision", provision);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ProvisionEntity provision){
        provisionService.save(provision);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ProvisionEntity provision){
        provisionService.updateById(provision);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] ids){
        provisionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
