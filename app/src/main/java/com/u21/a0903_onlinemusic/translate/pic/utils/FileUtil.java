package com.u21.a0903_onlinemusic.translate.pic.utils;

import java.io.File;

public class FileUtil {
    /**
     * Creates MD5 digest of a {@link File}.
     *
     * @param file {@link File} to create digest of.
     * @return MD5 digest of the {@link File}.
     */
    public static String md5(final File file) {
        try {
            return DigestEngine.md5().digestString(file);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Creates MD5 digest of a {@link String}.
     */
    public static String md5(final String content) {
        try {
            return DigestEngine.md5().digestString(content);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
