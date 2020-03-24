//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.springboot.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * gzip压缩工具类
 * @author mrliz
 */
@Slf4j
public class GZIPUtils {
    public GZIPUtils() {
    }

    public static void decompress(String source, String dest, boolean deleteSourceFile) {
        GZIPInputStream gzip = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File sourceFile = null;

        try {
            sourceFile = new File(source);
            inputStream = new FileInputStream(sourceFile);
            outputStream = new FileOutputStream(dest);
            gzip = new GZIPInputStream(inputStream);
            byte[] b = new byte[4096];
            boolean var8 = false;

            int n;
            while((n = gzip.read(b)) != -1) {
                outputStream.write(b, 0, n);
            }
        } catch (IOException var25) {
            var25.printStackTrace();
        } finally {
            try {
                if (gzip != null) {
                    gzip.close();
                }
            } catch (IOException var24) {
                var24.printStackTrace();
            }

            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException var23) {
                var23.printStackTrace();
            }

            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException var22) {
                var22.printStackTrace();
            }

            if (deleteSourceFile && sourceFile != null) {
                log.debug("delete " + sourceFile.getAbsolutePath() + "->" + sourceFile.delete());
            }

        }

    }

    public static void compress(MultipartFile source, String dest) {
        GZIPOutputStream gzip = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            inputStream = source.getInputStream();
            outputStream = new FileOutputStream(dest);
            gzip = new GZIPOutputStream(outputStream);
            byte[] b = new byte[4096];
            boolean var6 = false;

            int n;
            while((n = inputStream.read(b)) != -1) {
                gzip.write(b, 0, n);
            }
        } catch (IOException var23) {
            var23.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException var22) {
                var22.printStackTrace();
            }

            try {
                if (gzip != null) {
                    gzip.close();
                }
            } catch (IOException var21) {
                var21.printStackTrace();
            }

            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException var20) {
                var20.printStackTrace();
            }

        }

    }

    public static boolean compress(String source, String dest) {
        GZIPOutputStream gzip = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        boolean var6;
        try {
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(dest);
            gzip = new GZIPOutputStream(outputStream);
            byte[] b = new byte[4096];
            var6 = false;

            int n;
            while((n = inputStream.read(b)) != -1) {
                gzip.write(b, 0, n);
            }

            boolean var7 = true;
            return var7;
        } catch (IOException var25) {
            var25.printStackTrace();
            var6 = false;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException var24) {
                var24.printStackTrace();
            }

            try {
                if (gzip != null) {
                    gzip.close();
                }
            } catch (IOException var23) {
                var23.printStackTrace();
            }

            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException var22) {
                var22.printStackTrace();
            }

        }

        return var6;
    }
}
