package com.cube.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cube.annotation.Login;
import com.cube.entity.PackageEntity;
import com.cube.entity.ProvisionEntity;
import com.cube.service.PackageService;
import com.cube.service.ProvisionService;
import com.cube.utils.MyBeanUtils;
import com.cube.utils.PathManager;
import com.cube.utils.Result;
import com.cube.utils.ResultUtils;
import com.cube.vo.AppVo;
import com.cube.vo.PackageVo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cube.entity.AppEntity;
import com.cube.service.AppService;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.R;



/**
 * 
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-11-20 17:35:43
 */
@RestController
@RequestMapping("cube/app")
public class AppController {
    @Autowired
    private AppService appService;

    @Autowired
    private PackageService packageService;

    @Autowired
    private PathManager pathManager;

    @Autowired
    private ProvisionService provisionService;

    /**
     * 列表
     */
    @Login
    @RequestMapping("/list")
    public Result list(@RequestAttribute("userId") String memberId){
        List<AppEntity> list = appService.list(new QueryWrapper<AppEntity>().lambda().eq(AppEntity::getMemberId, memberId));

        List<AppVo> appVos = MyBeanUtils.copyList(list, AppVo.class);
        for (AppVo appVo : appVos) {
            supplyPackage(appVo);
        }
        return ResultUtils.ok(appVos);
    }

    @Login
    @GetMapping("/{appId}")
    public Result getAppById(@PathVariable("appId")String appId){
        AppEntity appEntity = appService.getById(appId);
        AppVo appVo = MyBeanUtils.copy(appEntity, AppVo.class);
        supplyPackage(appVo);
        return ResultUtils.ok(appVo);
    }


    /**
     * 查看App的包列表
     * @param appId
     * @return
     */
    @GetMapping("/packageList/{appId}")
    public Result getAppPackageList(@PathVariable("appId") String appId){
        AppEntity appEntity = appService.getById(appId);
        AppVo appVo = MyBeanUtils.copy(appEntity, AppVo.class);
        supplyPackage(appVo);
        return ResultUtils.ok(appVo);
    }

    @DeleteMapping("/del/{appId}")
    public Result delAppById(@PathVariable("appId") String appId){
        return ResultUtils.okMsg("删除成功");
    }

    private void supplyPackage(AppVo appVo) {
        PackageVo packageVo = getCurrentPackage(appVo.getCurrentId());
        appVo.setVersion(packageVo.getVersion());
        appVo.setBuildVersion(packageVo.getBuildVersion());
        appVo.setInstallPath(pathManager.getBaseURL(false) + "s/" + appVo.getShortCode());
        appVo.setMinVersion(appVo.getMinVersion());
        appVo.setCurrentPackage(additionToPackage(packageVo, pathManager));
        appVo.setIcon(PathManager.getRelativePath(appVo.getCurrentPackage()) + "icon.png");
        List<PackageEntity> packageEntityList = packageService.list(new QueryWrapper<PackageEntity>().lambda().eq(PackageEntity::getAppId, appVo.getId()));
        appVo.setPackageList(sortPackages(packageEntityList, pathManager));
    }

    /**
     * 获取
     * @param packageId
     * @return
     */
    private PackageVo getCurrentPackage(String packageId) {
        PackageEntity packageEntity = packageService.getById(packageId);
        return MyBeanUtils.copy(packageEntity,PackageVo.class);
    }

    private List<PackageVo> sortPackages(List<PackageEntity> packageEntityList, PathManager pathManager) {
        List<PackageVo> list = new ArrayList<>();
        for (PackageEntity packageEntity : packageEntityList) {
            PackageVo packageVo = additionToPackage(MyBeanUtils.copy(packageEntity,PackageVo.class), pathManager);
            list.add(packageVo);
        }
        list.sort((o1,o2) -> {
            if(o1.getCreateTime() > o2.getCreateTime()){
                return -1;
            }
            return 1;
        });
        return list;
    }


    private PackageVo additionToPackage(PackageVo aPackage,PathManager pathManager){
        AppEntity appEntity = appService.getById(aPackage.getAppId());

        aPackage.setDownloadURL(pathManager.getBaseURL(false) + "p/" + aPackage.getId());
        aPackage.setSafeDownloadURL(pathManager.getBaseURL(true) + "p/" + aPackage.getId());
        aPackage.setIconURL(pathManager.getPackageResourceURL(aPackage, true) + "icon.png");
        aPackage.setDisplaySize(String.format("%.2f MB", aPackage.getSize() / (1.0F * FileUtils.ONE_MB)));
        Date updateTime = new Date(aPackage.getCreateTime());
        String displayTime = (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(updateTime);
        aPackage.setDisplayTime(displayTime);

        if("ios".equalsIgnoreCase(aPackage.getPlatform())){
            aPackage.setIOS(true);
            String url = pathManager.getBaseURL(true) + "m/" + aPackage.getId();
            try {
                aPackage.setInstallURL("itms-services://?action=download-manifest&url=" + URLEncoder.encode(url, "utf-8"));
            }catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{
            aPackage.setIOS(false);
            aPackage.setInstallURL(pathManager.getPackageResourceURL(aPackage, false) + aPackage.getFileName());
        }
        aPackage.setPreviewURL(pathManager.getBaseURL(false) + "s/" + appEntity.getShortCode() + "?id=" + aPackage.getId());

        if(aPackage.getIOS()){
            if(StringUtils.isEmpty(aPackage.getProvisionId())){
                aPackage.setType("内测版");
            }else{
                ProvisionEntity provisionEntity = provisionService.getById(aPackage.getProvisionId());
                if(provisionEntity!=null){
                    if(provisionEntity.getIsEnterprise()){
                        aPackage.setType("企业版");
                    }else{
                        if ("AdHoc".equalsIgnoreCase(provisionEntity.getType())) {
                            aPackage.setType("内测版");
                        } else {
                            aPackage.setType("商店版");
                        }
                        aPackage.setDeviceCount(provisionEntity.getDeviceCount());
                        aPackage.setDevices(Arrays.asList(provisionEntity.getDevices()));
                    }
                }

            }
        }else{
            aPackage.setType("内测版");
        }
        return aPackage;
    }

    private PackageVo findPackageById(AppVo appVo, String packageId) {
        if(packageId!=null){
            PackageEntity packageEntity = packageService.getById(packageId);
            return MyBeanUtils.copy(packageEntity,PackageVo.class);
        }
        return appVo.getCurrentPackage();
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
    public R save(@RequestBody AppEntity app){
        appService.save(app);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AppEntity app){
        appService.updateById(app);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] ids){
        appService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
