package com.cube.modules.app.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.cube.modules.app.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cube.modules.app.entity.PackageEntity;
import com.cube.modules.app.service.PackageService;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.R;



/**
 * APP包管理
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-20 15:35:40
 */
@RestController
@RequestMapping("app/package")
public class PackageController {
    @Autowired
    private PackageService packageService;

    @Autowired
    private AppService appService;

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
        PackageEntity aPackage = packageService.getById(id);

        return R.ok().put("package", aPackage);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PackageEntity aPackage){
        packageService.save(aPackage);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PackageEntity aPackage){
        packageService.updateById(aPackage);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] ids){
        if(ids!=null){
            for (String id : ids) {
                PackageEntity one = packageService.getById(id);
                if(one!=null){
                    String appId = one.getAppId();
                    Map<String,Object> params = new HashMap<>();
                    params.put("app_id",appId);
                    Collection<PackageEntity> packageEntities = packageService.listByMap(params);
                    if(CollectionUtils.isEmpty(packageEntities)){
                        appService.removeById(appId);
                    }
                    break;
                }
            }
            packageService.removeByIds(Arrays.asList(ids));
        }
        return R.ok();
    }

}
