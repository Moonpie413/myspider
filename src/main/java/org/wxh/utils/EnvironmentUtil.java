package org.wxh.utils;

import java.io.IOException;

/**
 * Created by Maroon on 2016/8/7.
 * project: MySpider
 */
public class EnvironmentUtil {
    private static String pathPerfix;
    static {
        try {
            pathPerfix = PropUtil.getProperties("file").getProperty("file.winpath");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDownloadPath() {
        return pathPerfix;
    }
}
