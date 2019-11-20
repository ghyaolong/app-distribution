package com.cube.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.common.utils.PageUtils;
import com.cube.entity.FileEntity;

import java.util.Map;

/**
 * APP包管理
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-11-20 17:42:17
 */
public interface FileService extends IService<FileEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

