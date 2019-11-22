package com.cube.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.Query;
import com.cube.dao.PackageDao;
import com.cube.entity.PackageEntity;
import com.cube.service.PackageService;
import com.cube.utils.ImageUtils;
import com.cube.utils.PathManager;
import com.cube.utils.parser.ParserClient;
import com.cube.vo.PackageVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Service("packageService")
public class PackageServiceImpl extends ServiceImpl<PackageDao, PackageEntity> implements PackageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PackageEntity> page = this.page(
                new Query<PackageEntity>().getPage(params),
                new QueryWrapper<PackageEntity>()
        );

        return new PageUtils(page);
    }


    @Override
    public PackageVo buildPackage(String filePath) {
        PackageVo aPackage = ParserClient.parse(filePath);
        try {
            String fileName = aPackage.getPlatform() + "." + FilenameUtils.getExtension(filePath);
            // 更新文件名
            aPackage.setFileName(fileName);

            String packagePath = PathManager.getFullPath(aPackage);
            String tempIconPath = PathManager.getTempIconPath(aPackage);
            String iconPath = packagePath + File.separator + "icon.png";
            String sourcePath = packagePath + File.separator + fileName;
            String jpgIconPath = packagePath + File.separator + "icon.jpg";

            // 拷贝图标
            ImageUtils.resize(tempIconPath, iconPath, 192, 192);
            // 生成钉钉发送所需要图片
            ImageUtils.convertPNGToJPG(iconPath, jpgIconPath, 64, 64);
            // 源文件
            FileUtils.copyFile(new File(filePath), new File(sourcePath));

            // 删除临时图标
            FileUtils.forceDelete(new File(tempIconPath));
            // 源文件
            FileUtils.forceDelete(new File(filePath));

        }catch (IOException e){
            log.error("解析app包，拷贝源文件失败",e);
            e.printStackTrace();
        }
        return aPackage;
    }

}
