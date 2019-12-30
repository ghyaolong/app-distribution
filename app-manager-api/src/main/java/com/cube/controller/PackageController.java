package com.cube.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cube.annotation.Login;
import com.cube.common.exception.RRException;
import com.cube.constant.Constants;
import com.cube.entity.AppEntity;
import com.cube.entity.MemberEntity;
import com.cube.entity.PackageEntity;
import com.cube.entity.ProvisionEntity;
import com.cube.service.*;
import com.cube.upload.param.MultipartFileParam;
import com.cube.utils.*;
import com.cube.utils.ipa.PlistGenerator;
import com.cube.vo.AppVo;
import com.cube.vo.PackageVo;
import lombok.extern.slf4j.Slf4j;
import net.glxn.qrgen.javase.QRCode;
import oracle.jdbc.proxy.annotation.Post;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;


/**
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

    @Autowired
    private StorageService storageService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 预览页
     *
     * @param shortCode
     * @return
     */
    @GetMapping("/s/{shortCode}/{packageId}")
    public Result preview(@PathVariable("shortCode") String shortCode, @PathVariable("packageId") String packageId) {

        AppEntity appEntity = appService.getOne(new QueryWrapper<AppEntity>().lambda().eq(AppEntity::getShortCode, shortCode));

        if(appEntity==null){
            throw new RRException("APP不存在");
        }
        AppVo appVo = MyBeanUtils.copy(appEntity, AppVo.class);

        PackageEntity packageEntity = packageService.getById(packageId);
        if(packageEntity==null){
            throw new RRException("该版本不存在");
        }
        PackageVo packageVo = MyBeanUtils.copy(packageEntity, PackageVo.class);
        appVo.setVersion(packageVo.getVersion());
        appVo.setBuildVersion(packageVo.getBuildVersion());
        appVo.setInstallPath(pathManager.getBaseURL(false) + "cube/package/s/" + appVo.getShortCode());
        appVo.setMinVersion(appVo.getMinVersion());
        appVo.setCurrentPackage(additionToPackage(packageVo, pathManager));
        appVo.setIcon(PathManager.getRelativePath(appVo.getCurrentPackage()) + "icon.png");

        appVo.setCaPath(this.pathManager.getCAPath());
        appVo.setBasePath(this.pathManager.getBaseURL(false));
        return ResultUtils.ok(appVo);
    }

    @GetMapping("/s/{shortCode}/")
    public Result preview(@PathVariable("shortCode") String shortCode){
        AppEntity appEntity = appService.getOne(new QueryWrapper<AppEntity>().lambda().eq(AppEntity::getShortCode, shortCode));

        if(appEntity==null){
            throw new RRException("APP不存在");
        }
        AppVo appVo = MyBeanUtils.copy(appEntity, AppVo.class);
        PackageEntity packageEntity = packageService.getById(appEntity.getCurrentId());
        if(packageEntity==null){
            throw new RRException("该版本不存在");
        }
        PackageVo packageVo = MyBeanUtils.copy(packageEntity, PackageVo.class);
        appVo.setVersion(packageVo.getVersion());
        appVo.setBuildVersion(packageVo.getBuildVersion());
        appVo.setInstallPath(pathManager.getBaseURL(false) + "cube/package/s/" + appVo.getShortCode());
        appVo.setMinVersion(appVo.getMinVersion());
        appVo.setCurrentPackage(additionToPackage(packageVo, pathManager));
        appVo.setIcon(PathManager.getRelativePath(appVo.getCurrentPackage()) + "icon.png");
        appVo.setCaPath(this.pathManager.getCAPath());
        appVo.setBasePath(this.pathManager.getBaseURL(false));
        return ResultUtils.ok(appVo);
    }


    /**
     * 修改版本标注
     * @param packageId
     * @param log
     * @return
     */
    @Login
    @PostMapping("/changeLog")
    public Result changeLog(String packageId,String log){
        if(StringUtils.isBlank(packageId)){
            throw new RRException("packageId不能为空");
        }
        boolean update = packageService.update(new UpdateWrapper<PackageEntity>().lambda().eq(PackageEntity::getId, packageId).set(PackageEntity::getChangeLog, log));
        return ResultUtils.okMsg("修改成功");
    }

    private PackageVo additionToPackage(PackageVo aPackage, PathManager pathManager) {
        AppEntity appEntity = appService.getById(aPackage.getAppId());

        aPackage.setDownloadURL(pathManager.getBaseURL(false) + "cube/package/p/" + aPackage.getId());
        aPackage.setSafeDownloadURL(pathManager.getBaseURL(true) + "cube/package/p/" + aPackage.getId());
        aPackage.setIconURL(pathManager.getPackageResourceURL(aPackage, false) + "icon.png");
        aPackage.setDisplaySize(String.format("%.2f MB", aPackage.getSize() / (1.0F * FileUtils.ONE_MB)));
        Date updateTime = new Date(aPackage.getCreateTime());
        String displayTime = (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(updateTime);
        aPackage.setDisplayTime(displayTime);

        if ("ios".equalsIgnoreCase(aPackage.getPlatform())) {
            aPackage.setIOS(true);
            String url = pathManager.getBaseURL(true) + "cube/package/m/" + aPackage.getId();
            try {
                aPackage.setInstallURL("itms-services://?action=download-manifest&url=" + URLEncoder.encode(url, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            aPackage.setIOS(false);
            aPackage.setInstallURL(pathManager.getPackageResourceURL(aPackage, false) + aPackage.getFileName());
        }
        aPackage.setPreviewURL(pathManager.getBaseURL(false) + "cube/package/s/" + appEntity.getShortCode() + "?packageId=" + aPackage.getId());

        if (aPackage.getIOS()) {
            if (StringUtils.isEmpty(aPackage.getProvisionId())) {
                aPackage.setType("内测版");
            } else {
                ProvisionEntity provisionEntity = provisionService.getById(aPackage.getProvisionId());
                if (provisionEntity != null) {
                    if (provisionEntity.getIsEnterprise()) {
                        aPackage.setType("企业版");
                    } else {
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
        } else {
            aPackage.setType("内测版");
        }
        return aPackage;
    }


    /**
     * 设备列表
     *
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
     *
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
     *
     * @param packageId
     */
    @RequestMapping("/code/{packageId}")
    public void getQrCode(@PathVariable("packageId") String packageId, HttpServletResponse response) {
        try {
            PackageEntity packageEntity = packageService.getById(packageId);
            if(packageEntity==null){
                throw new RRException("包不存在");
            }
            PackageVo packageVo = MyBeanUtils.copy(packageEntity, PackageVo.class);
            additionToPackage(packageVo, pathManager);
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
     *
     * @param packageId
     * @return
     */
    @Login
    @DeleteMapping("/delete/{packageId}")
    public Result deleteById(@PathVariable("packageId") String packageId) {
        PackageEntity packageEntity = packageService.getById(packageId);
        packageService.removeById(packageId);
        return ResultUtils.okMsg("删除成功");
    }


    /**
     * 上传包
     *
     * @param file
     * @param request
     * @return
     */
    @Login
    @PostMapping("/app/upload")
    public Result upload(@RequestAttribute("userId") String userId, @RequestParam("file") MultipartFile file, HttpServletRequest request) {

        String filePath = transfer(file);
        PackageVo aPackage = this.packageService.buildPackage(filePath);
        aPackage.setMemberId(userId);
        Map<String, String> extra = new HashMap<>();



        if (!extra.isEmpty()) {
            aPackage.setExtra(JSON.toJSONString(extra));
        }
        //String ossUrl = AliyunOSSClientUtil.multipartUpload(filePath);
        //aPackage.setOssUrl(ossUrl);
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

        //log.error("上传错误{}",e);

        //return ResultUtils.error("500","系统错误");

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////               上传文件2.0               ////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 秒传判断，断点判断
     *
     * @return
     */
    @RequestMapping(value = "/checkFileMd5", method = RequestMethod.POST)
    public Object checkFileMd5(String md5) throws IOException {
        Object processingObj = stringRedisTemplate.opsForHash().get(Constants.FILE_UPLOAD_STATUS, md5);
        if (processingObj == null) {
            return ResultUtils.error("101","该文件没有上传过");
        }
        String processingStr = processingObj.toString();
        boolean processing = Boolean.parseBoolean(processingStr);
        String value = stringRedisTemplate.opsForValue().get(Constants.FILE_MD5_KEY + md5);
        if (processing) {
            return ResultUtils.error("100","文件已存在");
            //throw new RRException("文件已存在");
        } else {
            File confFile = new File(value);
            byte[] completeList = FileUtils.readFileToByteArray(confFile);
            List<String> missChunkList = new LinkedList<>();
            for (int i = 0; i < completeList.length; i++) {
                if (completeList[i] != Byte.MAX_VALUE) {
                    missChunkList.add(i + "");
                }
            }
            return ResultUtils.error("102","该文件上传了一部分",missChunkList);
        }
    }

    /**
     * 分片上传
     * @param request
     * @return
     */
    @PostMapping("/upload2")
    public Result upload2(MultipartFileParam param, HttpServletRequest request){
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(isMultipart){
            log.info("上传文件start");
            try {
                // 方法1
                //storageService.uploadFileRandomAccessFile(param);
                // 方法2 这个更快点
                storageService.uploadFileByMappedByteBuffer(param);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("文件上传失败。{}", param.toString());
            }
        }
        return ResultUtils.okMsg("上传文件成功");
    }

    /**
     * 下载文件源文件(ipa 或 apk)
     *
     * @param id
     * @param response
     */
    @RequestMapping("/p/{id}")
    public void download(@PathVariable("id") String id, HttpServletResponse response) {

        PackageEntity packageEntity = packageService.getById(id);
        if (packageEntity == null) {
            throw new RRException("该文件不存在");
        }
        try {
            PackageVo aPackage = MyBeanUtils.copy(packageEntity, PackageVo.class);
            String path = PathManager.getFullPath(aPackage) + aPackage.getFileName();
            File file = new File(path);
            if (file.exists()) { //判断文件父目录是否存在

                /**
                 * 下载次数-1
                 */
                AppEntity appEntity = appService.getById(packageEntity.getAppId());
                String memberId = appEntity.getMemberId();
                synchronized (this) {
                    MemberEntity memberEntity = memberService.getById(memberId);
                    if (memberEntity.getDownloadCount() <= 0) {
                        log.error("memberId={},无下载次数",memberId);
                        return;
                    }

                    if (memberEntity.getDownloadCount() <= 1000) {
                        //todoycl 是否需要给用户添加次数阈值通知
                    }


                    boolean update = memberService.update(new UpdateWrapper<MemberEntity>().lambda().setSql(" download_count = download_count -1 ").eq(MemberEntity::getId,memberId).gt(MemberEntity::getDownloadCount,0));
                    log.info("用户userId={}下载次数已扣减，剩余{}次数", memberId, (memberEntity.getDownloadCount() - 1));
                    if(!update){
                        log.error("memberId={},无下载次数",memberId);
                        return;
                    }
                }


                response.setContentType("application/force-download");
                // 文件名称转换
                String fileName = aPackage.getName() + "_" + aPackage.getVersion();
                String ext = "." + FilenameUtils.getExtension(aPackage.getFileName());
                String appName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
                response.setHeader("Content-Disposition", "attachment;fileName=" + appName + ext);

//                byte[] buffer = new byte[1024];
//                OutputStream os = response.getOutputStream();
//                FileInputStream fis = new FileInputStream(file);
//                BufferedInputStream bis = new BufferedInputStream(fis);
//                int i = bis.read(buffer);
//                while (i != -1) {
//                    os.write(buffer);
//                    i = bis.read(buffer);
//                }
//                bis.close();
//                fis.close();
                FileInputStream fis = null;
                ServletOutputStream out = null;
                try {
                    fis =  new FileInputStream(file);
                    out  = response.getOutputStream();
                    byte[] outputByte = new byte[1024];
                    int readTmp = 0;
                    while ((readTmp = fis.read(outputByte)) != -1) {
                        out.write(outputByte, 0, readTmp); //并不是每次都能读到1024个字节，所有用readTmp作为每次读取数据的长度，否则会出现文件损坏的错误
                    }
                }catch (Exception e){

                }finally {
                    if(fis!=null){
                        fis.close();
                    }
                    if(out!=null){
                        out.flush();
                        out.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 转存文件
     *
     * @param srcFile
     * @return
     */
    private String transfer(MultipartFile srcFile) {

        // 获取文件后缀
        String fileName = srcFile.getOriginalFilename();
        String ext = FilenameUtils.getExtension(fileName);
        if (!"apk".equals(ext) && !"ipa".equals(ext)) {
            throw new RRException("请上传apk或者ipa类型的文件");
        }
        // 生成文件名
        String newFileName = UUID.randomUUID().toString() + "." + ext;
        // 转存到 tmp
        String destPath = FileUtils.getTempDirectoryPath() + File.separator + newFileName;
        destPath = destPath.replaceAll("//", "/");
        try {
            srcFile.transferTo(new File(destPath));
        } catch (IOException e) {
            log.error("转存文件失败：{}", e);
            e.printStackTrace();
        }
        return destPath;
    }


}
