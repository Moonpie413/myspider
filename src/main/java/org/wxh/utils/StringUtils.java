package org.wxh.utils;

/**
 * Created by Maroon on 2016/8/13.
 * project: MySpider
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String handleRelativeUrl(String url, String baseUrl) {
        if (isNotBlank(url) && isNotBlank(baseUrl)) {
            //如果path是相对路径就将baseurl添加到path的前面
            if (!startsWith(url, "http")) {
                return baseUrl + url;
            }
        }
        return url;
    }

}
