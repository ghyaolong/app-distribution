package com.cube.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cube.annotation.Login;
import com.cube.entity.AppEntity;
import com.cube.entity.MemberEntity;
import com.cube.entity.PackageEntity;
import com.cube.entity.ProvisionEntity;
import com.cube.service.AppService;
import com.cube.service.MemberService;
import com.cube.service.PackageService;
import com.cube.service.ProvisionService;
import com.cube.utils.MyBeanUtils;
import com.cube.utils.PathManager;
import com.cube.utils.Result;
import com.cube.utils.ResultUtils;
import com.cube.utils.ipa.PlistGenerator;
import com.cube.vo.AppVo;
import com.cube.vo.PackageVo;
import lombok.extern.slf4j.Slf4j;
import net.glxn.qrgen.javase.QRCode;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;


/**
 * 
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-11-20 17:44:44
 */
@Slf4j
@RestController
@RequestMapping("cube/package")
public class PackageController {
    @Autowired
    private PackageService packageService;

    @Resource
    private AppService appService;

    @Resource
    private PathManager pathManager;

    @Autowired
    private ProvisionService provisionService;

    @Autowired
    private MemberService memberService;


    /**
     * 预览页
     * @param code
     * @return
     */
    @GetMapping("/s/{code}")
    public Result get(@PathVariable("code") String code, String packageId) {

        AppEntity appEntity = appService.getOne(new QueryWrapper<AppEntity>().lambda().eq(AppEntity::getShortCode, code));

        AppVo appVo = MyBeanUtils.copy(appEntity, AppVo.class);


        PackageEntity packageEntity = packageService.getById(packageId);
        PackageVo packageVo = MyBeanUtils.copy(packageEntity, PackageVo.class);
        appVo.setVersion(packageVo.getVersion());
        appVo.setBuildVersion(packageVo.getBuildVersion());
        appVo.setInstallPath(pathManager.getBaseURL(false) + "s/" + appVo.getShortCode());
        appVo.setMinVersion(appVo.getMinVersion());
        appVo.setCurrentPackage(additionToPackage(packageVo, pathManager));
        appVo.setIcon(PathManager.getRelativePath(appVo.getCurrentPackage()) + "icon.png");

        appVo.setCaPath(this.pathManager.getCAPath());
        appVo.setBasePath(this.pathManager.getBaseURL(false));
        return ResultUtils.ok(appVo);
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


    /**
     * 设备列表
     * @return
     */
    @GetMapping("/devices/{packageId}")
    public Result devices(@PathVariable("packageId") String packageId) {
        PackageEntity packageEntity = packageService.getById(packageId);
        PackageVo packageVo = MyBeanUtils.copy(packageEntity, PackageVo.class);
        additionToPackage(packageVo, pathManager);
        return ResultUtils.ok(packageVo);
    }

    /**
     * 获取 manifest
     * @param packageId
     * @param response
     */
    @RequestMapping("/m/{packageId}")
    public void getManifest(@PathVariable("packageId") String packageId, HttpServletResponse response) {
        try {
            PackageEntity packageEntity = packageService.getById(packageId);
            PackageVo packageVo = MyBeanUtils.copy(packageEntity, PackageVo.class);
            AppEntity appEntity = appService.getById(packageVo.getAppId());
            if (packageEntity != null && "ios".equalsIgnoreCase(appEntity.getPlatform())) {
                response.setContentType("application/force-download");
                response.setHeader("Content-Disposition", "attachment;fileName=manifest.plist");
                Writer writer = new OutputStreamWriter(response.getOutputStream());
                additionToPackage(packageVo, pathManager);
                PlistGenerator.generate(packageVo, writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取包二维码
     * @param packageId
     */
    @RequestMapping("/code/{packageId}")
    public void getQrCode(@PathVariable("packageId") String packageId, HttpServletResponse response) {
        try {
            PackageEntity packageEntity = packageService.getById(packageId);
            PackageVo packageVo = MyBeanUtils.copy(packageEntity, PackageVo.class);
            additionToPackage(packageVo,pathManager);
            if (packageEntity != null) {
                response.setContentType("image/png");
                QRCode.from(packageVo.getPreviewURL()).withSize(250, 250).writeTo(response.getOutputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除包
     * @param packageId
     * @return
     */
    @DeleteMapping("/delete/{packageId}")
    public Result deleteById(@PathVariable("packageId") String packageId) {
        packageService.removeById(packageId);
        return ResultUtils.okMsg("删除成功");
    }


    /**
     * 上传包
     * @param file
     * @param request
     * @return
     */
    @PostMapping("/app/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        try {
            String filePath = transfer(file);
            PackageVo aPackage = this.packageService.buildPackage(filePath);
            Map<String , String> extra = new HashMap<>();
//            String jobName = request.getParameter("jobName");
//            String buildNumber = request.getParameter("buildNumber");
//            if (StringUtils.hasLength(jobName)) {
//                extra.put("jobName", jobName);
//            }
//            if (StringUtils.hasLength(buildNumber)) {
//                extra.put("buildNumber", buildNumber);
//            }
            if (!extra.isEmpty()) {
                aPackage.setExtra(JSON.toJSONString(extra));
            }
            AppVo app = this.appService.saveByPackage(aPackage);
//            app.getPackageList().add(aPackage);
//            app.setCurrentPackage(aPackage);
//            aPackage.setApp(app);
//            app = this.appService.save(app);
            // URL
            String codeURL = this.pathManager.getBaseURL(false) + "cube/package/code/" + app.getCurrentId();
            // 发送WebHook消息
            //WebHookClient.sendMessage(app, pathManager);
            return ResultUtils.ok(codeURL);
        } catch (Exception e) {
            log.error("上传错误{}",e);
            e.printStackTrace();
            return ResultUtils.error("500","系统错误");
        }
    }



    /**
     * 下载文件源文件(ipa 或 apk)
     * @param id
     * @param response
     */
    @RequestMapping("/p/{id}")
    public void download(@PathVariable("id") String id, HttpServletResponse response){
        try {
            PackageEntity packageEntity = packageService.getById(id);
            PackageVo aPackage = MyBeanUtils.copy(packageEntity, PackageVo.class);
            String path = PathManager.getFullPath(aPackage) + aPackage.getFileName();
            File file = new File(path);
            if(file.exists()){ //判断文件父目录是否存在

                /**
                 * 下载次数-1
                 */
                AppEntity appEntity = appService.getById(packageEntity.getAppId());
                String memberId = appEntity.getMemberId();
                synchronized (this){
                    MemberEntity memberEntity = memberService.getById(memberId);
                    if(memberEntity.getDownloadCount()<=0){
                        return;
                    }

                    if(memberEntity.getDownloadCount()<=1000){
                        //todoycl 是否需要给用户添加次数阈值通知
                    }


                    boolean update = memberService.update(new UpdateWrapper<MemberEntity>().lambda().setSql(" download_count = download_count -1 "));
                    log.info("用户userId={}下载次数已扣减，剩余{}次数",memberId,(memberEntity.getDownloadCount()-1));
                }


                response.setContentType("application/force-download");
                // 文件名称转换
                String fileName = aPackage.getName() + "_" + aPackage.getVersion();
                String ext =  "." + FilenameUtils.getExtension(aPackage.getFileName());
                String appName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
                response.setHeader("Content-Disposition", "attachment;fileName=" + appName + ext);

                byte[] buffer = new byte[1024];
                OutputStream os = response.getOutputStream();
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }
                bis.close();
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 转存文件
     * @param srcFile
     * @return
     */
    private String transfer(MultipartFile srcFile) {
        try {
            // 获取文件后缀
            String fileName = srcFile.getOriginalFilename();
            String ext = FilenameUtils.getExtension(fileName);
            // 生成文件名
            String newFileName = UUID.randomUUID().toString() + "." + ext;
            // 转存到 tmp
            String destPath = FileUtils.getTempDirectoryPath() + File.separator + newFileName;
            destPath = destPath.replaceAll("//", "/");
            srcFile.transferTo(new File(destPath));
            return destPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




}
