package org.wxh.utils;

/**
 * Created by Maroon on 2016/8/13.
 * project: MySpider
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String handleRelativePath(String path, String baseUrl) {
        if (isNotBlank(path) && isNotBlank(baseUrl)) {
            //如果path是相对路径就将baseurl添加到path的前面
            if (indexOf(path, "http") == -1) {
                return baseUrl + path;
            }
        }
        return path;
    }

}
