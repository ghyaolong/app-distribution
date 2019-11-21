package com.cube.service.impl;

import com.cube.entity.PackageEntity;
import com.cube.service.PackageService;
import com.cube.utils.MyBeanUtils;
import com.cube.vo.AppVo;
import com.cube.vo.PackageVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.Query;

import com.cube.dao.AppDao;
import com.cube.entity.AppEntity;
import com.cube.service.AppService;


@Service("appService")
public class AppServiceImpl extends ServiceImpl<AppDao, AppEntity> implements AppService {

    @Autowired
    private PackageService packageService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AppEntity> page = this.page(
                new Query<AppEntity>().getPage(params),
                new QueryWrapper<AppEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<AppVo> findAll(String memberId) {
        Map<String,Object> map = new HashMap<>();
        map.put("member_Id",memberId);
        List<AppEntity> appEntityList = this.baseMapper.selectByMap(map);

        List<AppVo> appVos = MyBeanUtils.copyList(appEntityList, AppVo.class);
        for (AppVo appVo : appVos) {
            List<PackageEntity> list = packageService.list(new QueryWrapper<PackageEntity>().lambda().eq(PackageEntity::getAppId, appVo.getId()));
            List<PackageVo> packageVos = MyBeanUtils.copyList(list, PackageVo.class);
            appVo.setPackageList(packageVos);
            for (PackageVo packageVo : packageVos) {
                if(appVo.getCurrentId().equals(packageVo.getAppId())){
                    appVo.setCurrentPackage(packageVo);
                }
            }
        }
        return appVos;
    }

}
