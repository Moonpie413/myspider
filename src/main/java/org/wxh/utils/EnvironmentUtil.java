package org.wxh.utils;

import java.io.IOException;

/**
 * Created by Maroon on 2016/8/7.
 * project: MySpider
 */
public class EnvironmentUtil {

    public static String getDownloadPath() {
        String pathPerfix = null;
        try {
             pathPerfix = PropUtil.getProperties("file").getProperty("file.winpath");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathPerfix;
    }
}
