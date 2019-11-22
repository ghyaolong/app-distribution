package com.cube.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.common.utils.PageUtils;
import com.cube.entity.AppEntity;
import com.cube.vo.AppVo;
import com.cube.vo.PackageVo;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-11-20 17:42:17
 */
public interface AppService extends IService<AppEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<AppVo> findAll(String memberId);

    AppVo saveByPackage(PackageVo packageVo);
}

