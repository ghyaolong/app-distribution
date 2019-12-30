package com.cube.modules.app.controller;

import java.util.Arrays;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cube.modules.app.entity.WebHookEntity;
import com.cube.modules.app.service.WebHookService;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.R;



/**
 * hook管理
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-20 15:35:40
 */
@RestController
@RequestMapping("app/webhook")
public class WebHookController {
    @Autowired
    private WebHookService webHookService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = webHookService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
        WebHookEntity webHook = webHookService.getById(id);

        return R.ok().put("webHook", webHook);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody WebHookEntity webHook){
        webHookService.save(webHook);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody WebHookEntity webHook){
        webHookService.updateById(webHook);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] ids){
        webHookService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
