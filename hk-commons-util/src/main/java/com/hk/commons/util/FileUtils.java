package com.hk.commons.util;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;

/**
 * 文件工具类
 *
 * @author kevin
 * @date 2018-05-30 11:17
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    /**
     * 图片后缀名
     */
    private static final String[] IMAGE_EXT = {"png", "jpg", "gif", "ico", "jpeg", "bmp"};

    /**
     * 递推删除所有指定扩展名的文件，不会删除目录
     *
     * @param file 目录或文件
     * @param ext  文件扩展名
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

    /**
     * 删除目录
     *
     * @param dir dir
     */
    public static boolean deleteDir(File dir) {
        boolean result = false;
        if (dir.exists() && dir.isDirectory()) {
            try {
                deleteDirectory(dir);
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
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
        return file.exists() && file.isFile() && isImage(file.getName());
    }

    /**
     * 是否为图片
     *
     * @param fileName fileName
     * @return boolean
     */
    public static boolean isImage(String fileName) {
        String extension = getExtension(fileName);
        return StringUtils.isNotEmpty(extension) && ArrayUtils.contains(IMAGE_EXT, extension.toLowerCase());
    }

}
