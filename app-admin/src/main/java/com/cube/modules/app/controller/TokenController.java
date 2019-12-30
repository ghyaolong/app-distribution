package com.cube.modules.app.controller;

import java.util.Arrays;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cube.modules.app.entity.TokenEntity;
import com.cube.modules.app.service.TokenService;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.R;



/**
 * token管理
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-20 15:35:40
 */
@RestController
@RequestMapping("app/token")
public class TokenController {
    @Autowired
    private TokenService tokenService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tokenService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        TokenEntity token = tokenService.getById(id);

        return R.ok().put("token", token);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody TokenEntity token){
        tokenService.save(token);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TokenEntity token){
        tokenService.updateById(token);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        tokenService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
