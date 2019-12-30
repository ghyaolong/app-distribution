package com.cube.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cube.common.exception.RRException;
import com.cube.common.utils.IDGenerator;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.R;
import com.cube.modules.app.entity.GoodsEntity;
import com.cube.modules.app.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * 商品管理
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-05 10:57:43
 */
@RestController
@RequestMapping("app/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = goodsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
        GoodsEntity goods = goodsService.getById(id);

        return R.ok().put("goods", goods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody GoodsEntity goods){

        goods.setId(IDGenerator.getUUID());
        goods.setProductId(IDGenerator.getProduID());
        goods.setCreateTime(new Date());
        goods.setStatus("0");
        goodsService.save(goods);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody GoodsEntity goods){
        goodsService.updateById(goods);
        
        return R.ok();
    }

    /**
     * 更新商品状态 0:正常   1：下架
     * @param
     * @param
     * @return
     */
    @PostMapping("/updateStatus")
    public R updateStatus(@RequestBody GoodsEntity entity){
        if(entity!=null) {
            if(StringUtils.isEmpty(entity.getId())||StringUtils.isEmpty(entity.getStatus())){
                throw new RRException("id和status不能为空");
            }
            goodsService.update(new UpdateWrapper<GoodsEntity>().lambda().eq(GoodsEntity::getId,entity.getId()).set(GoodsEntity::getStatus,entity.getStatus()));
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] ids){
        goodsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
