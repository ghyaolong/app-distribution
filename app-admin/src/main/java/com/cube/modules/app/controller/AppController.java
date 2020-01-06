package com.cube.modules.app.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.cube.modules.app.service.PackageService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cube.modules.app.entity.AppEntity;
import com.cube.modules.app.service.AppService;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.R;



/**
 * APP管理
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-20 15:35:40
 */
@RestController
@RequestMapping("app/app")
public class AppController {
    @Autowired
    private AppService appService;

    @Autowired
    private PackageService packageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:app:info")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = appService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
        AppEntity app = appService.getById(id);

        return R.ok().put("app", app);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:app:save")
    public R save(@RequestBody AppEntity app){
        appService.save(app);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:app:update")
    public R update(@RequestBody AppEntity app){
        appService.updateById(app);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:app:delete")
    public R delete(@RequestBody String[] ids){
        if(ids!=null){
            for (String id : ids) {
                Map<String,Object> params = new HashMap<>();
                params.put("app_id",id);
                packageService.removeByMap(params);
            }
        }
        appService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
