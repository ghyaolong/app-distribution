package com.cube.utils.parser;

import com.cube.vo.PackageVo;

public interface PackageParser {
    // 解析包
    PackageVo parse(String filePath);
}
