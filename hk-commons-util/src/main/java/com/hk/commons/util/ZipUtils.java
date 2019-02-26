package com.hk.commons.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author kevin
 * @date 2018-05-30 08:42
 */
public class ZipUtils {

    public static void toZip(String dir, OutputStream out, boolean keepDirStructure) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(out)) {
            File file = new File(dir);
            compress(file, zipOutputStream, file.getName(), keepDirStructure);
        } catch (IOException e) {
            // ignore.
        }
    }

    private static void compress(File sourceFile, ZipOutputStream zops, String fileName, boolean keepDirStructure) throws IOException {
        byte[] buf = new byte[4 * 1024];
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zops.putNextEntry(new ZipEntry(fileName));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zops.write(buf, 0, len);
            }
            // Complete the entry
            zops.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (ArrayUtils.isEmpty(listFiles)) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if (keepDirStructure) {
                    // 空文件夹的处理
                    zops.putNextEntry(new ZipEntry(fileName + "/"));
                    // 没有文件，不需要文件的copy
                    zops.closeEntry();
                }

            } else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (keepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zops, fileName + "/" + file.getName(), keepDirStructure);
                    } else {
                        compress(file, zops, file.getName(), keepDirStructure);
                    }

                }
            }
        }
    }

}
