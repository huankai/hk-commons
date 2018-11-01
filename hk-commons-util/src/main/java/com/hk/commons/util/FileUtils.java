package com.hk.commons.util;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author: kevin
 * @date: 2018-05-30 11:17
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    public static final String[] IMAGE_EXT = {"png", "jpg", "gif", "ico", "jpeg", "bmp"};

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

    /**
     * 获取文件扩展名
     *
     * @param fileName fileName
     * @return ext
     */
    public static String getExtension(String fileName) {
        return FilenameUtils.getExtension(fileName);
    }

    /**
     * 是否为图片
     *
     * @param file file
     * @return boolean
     */
    public static boolean isImage(File file) {
        if (!file.exists() || file.isDirectory()) {
            return false;
        }
        String extension = FilenameUtils.getExtension(file.getName());
        return ArrayUtils.contains(IMAGE_EXT, extension.toLowerCase());
    }

    /**
     * 是否为图片
     *
     * @param fileName fileName
     * @return boolean
     */
    public static boolean isImage(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);
        return ArrayUtils.contains(IMAGE_EXT, extension.toLowerCase());
    }

}
