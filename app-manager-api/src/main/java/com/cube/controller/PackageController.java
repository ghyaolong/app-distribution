package com.cube.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.cube.service.AppService;
import com.cube.utils.MyBeanUtils;
import com.cube.utils.PathManager;
import com.cube.utils.Result;
import com.cube.utils.ResultUtils;
import com.cube.vo.AppVo;
import com.cube.vo.PackageVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.cube.entity.PackageEntity;
import com.cube.service.PackageService;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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

    /**
     * 列表
     */
    @RequestMapping("/list")

    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = packageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
        PackageEntity pck = packageService.getById(id);

        return R.ok().put("package", pck);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PackageEntity pck){
        packageService.save(pck);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PackageEntity pck){
        packageService.updateById(pck);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] ids){
        packageService.removeByIds(Arrays.asList(ids));

        return R.ok();
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
            String codeURL = this.pathManager.getBaseURL(false) + "p/code/" + app.getCurrentId();
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
