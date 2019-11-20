package com.cube.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cube.entity.FileEntity;
import com.cube.service.FileService;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.R;



/**
 * APP包管理
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-11-20 17:35:43
 */
@RestController
@RequestMapping("cube/file")
public class FileController {
    @Autowired
    private FileService fileService;

    /**
     * 列表
     */
    @RequestMapping("/list")

    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fileService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
        FileEntity file = fileService.getById(id);

        return R.ok().put("file", file);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody FileEntity file){
        fileService.save(file);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody FileEntity file){
        fileService.updateById(file);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] ids){
        fileService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
