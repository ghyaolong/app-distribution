package com.cube.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cube.entity.PackageEntity;
import com.cube.service.PackageService;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.R;



/**
 * 
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-11-20 17:44:44
 */
@RestController
@RequestMapping("cube/package")
public class PackageController {
    @Autowired
    private PackageService packageService;

    /**
     * 列表
     */
    @RequestMapping("/list")

    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = packageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
        PackageEntity pck = packageService.getById(id);

        return R.ok().put("package", pck);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PackageEntity pck){
        packageService.save(pck);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PackageEntity pck){
        packageService.updateById(pck);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] ids){
        packageService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
