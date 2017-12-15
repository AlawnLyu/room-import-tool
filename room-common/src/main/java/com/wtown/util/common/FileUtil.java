/**
 * @author LYU
 * @create 2017年12月14日 11:04
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.wtown.util.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;

public class FileUtil {

    public static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                if (logger.isDebugEnabled()){
                    logger.debug("删除单个文件" + fileName + "成功！");
                }
                return true;
            } else {
                if (logger.isDebugEnabled()){
                    logger.debug("删除单个文件" + fileName + "失败！");
                }
                return false;
            }
        } else {
            if (logger.isDebugEnabled()){
                logger.debug("删除单个文件失败：" + fileName + "不存在！");
            }
            return false;
        }
    }
}
