package com.cube.service.impl;

import com.cube.entity.PackageEntity;
import com.cube.service.PackageService;
import com.cube.utils.CodeGenerator;
import com.cube.utils.IDGenerator;
import com.cube.utils.MyBeanUtils;
import com.cube.vo.AppVo;
import com.cube.vo.PackageVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public AppVo saveByPackage(PackageVo aPackage) {
        //App app = this.appDao.get(aPackage.getBundleId(), aPackage.getPlatform());
        AppEntity appEntity = this.baseMapper.selectOne(new QueryWrapper<AppEntity>().lambda()
                .eq(AppEntity::getBundleId, aPackage.getBundleId())
                .eq(AppEntity::getPlatform, aPackage.getPlatform())
                .eq(AppEntity::getMemberId,aPackage.getMemberId())
        );
        if(appEntity==null){
            appEntity = new AppEntity();
            String shortCode = CodeGenerator.generate(6);
            AppEntity isShortCode = this.baseMapper.selectOne(new QueryWrapper<AppEntity>().lambda().eq(AppEntity::getShortCode, shortCode));
            while (isShortCode != null) {
                shortCode = CodeGenerator.generate(6);
            }
            String packageID = IDGenerator.getUUID();
            BeanUtils.copyProperties(aPackage, appEntity);
            appEntity.setShortCode(shortCode);
            appEntity.setCurrentId(packageID);

            //保存app
            this.baseMapper.insert(appEntity);

            //保存package
            aPackage.setId(packageID);
            aPackage.setAppId(appEntity.getId());
            PackageEntity packageEntity = MyBeanUtils.copy(aPackage, PackageEntity.class);
            packageService.save(packageEntity);
            System.out.println(packageEntity.getCreateTime());

        }else{
            //保存package
            aPackage.setAppId(appEntity.getId());
            PackageEntity packageEntity = MyBeanUtils.copy(aPackage, PackageEntity.class);
            packageService.save(packageEntity);

            appEntity.setCurrentId(packageEntity.getId());
            appEntity.setName(aPackage.getName());
            this.baseMapper.updateById(appEntity);
        }

        return MyBeanUtils.copy(appEntity,AppVo.class);
    }

}
