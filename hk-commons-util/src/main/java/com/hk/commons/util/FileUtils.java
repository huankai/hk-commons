package com.hk.commons.util;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author: kevin
 * @date 2018-05-30 11:17
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    /**
     * 递推删除所有指定扩展名的文件，不会删除目录
     *
     * @param file
     * @param ext
     * @return true if success
     */
    public static boolean deleteFile(File file, String ext) {
        boolean result = false;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (ArrayUtils.isNotEmpty(files)) {
                for (File item : files) {
                    if (item.isDirectory()) {
                        deleteFile(item, ext);
                    } else {
                        if (FilenameUtils.isExtension(item.getName(), ext)) {
                            result = item.delete();
                        }
                    }
                }
            }
        } else if (file.isFile() && FilenameUtils.isExtension(file.getName(), ext)) {
            result = file.delete();
        }
        return result;
    }

    public static void deleteDir(File dir) {
        if (dir.exists() && dir.isDirectory()) {
            try {
                deleteDirectory(dir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
