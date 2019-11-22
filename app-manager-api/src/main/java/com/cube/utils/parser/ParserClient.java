package com.cube.utils.parser;

import com.cube.vo.PackageVo;
import org.apache.commons.io.FilenameUtils;

public class ParserClient {

    /**
     * 解析包
     * @param filePath 文件路径
     * @return
     */
    public static PackageVo parse(String filePath) {
        PackageParser parser = getParser(filePath);
        if (parser != null) {
            return parser.parse(filePath);
        }
        return null;
    }

    /**
     * 根据文件后缀名获取解析器
     * @param filePath
     * @return
     */
    private static PackageParser getParser(String filePath) {
        String extension = FilenameUtils.getExtension(filePath);
        try {
            // 动态获取解析器
            Class aClass = Class.forName("com.cube.utils.parser." + extension.toUpperCase()+"Parser");
            PackageParser packageParser = (PackageParser)aClass.newInstance();
            return packageParser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
