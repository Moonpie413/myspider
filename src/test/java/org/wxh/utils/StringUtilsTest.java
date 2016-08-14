package org.wxh.utils;

import org.junit.Test;

/**
 * Created by Maroon on 2016/8/13.
 * project: MySpider
 */
public class StringUtilsTest {

    @Test
    public void testHandleRelativePath() throws Exception {
        String path = "/group/explore/life";
        String baseUrl = "https://www.douban.com";
        System.out.println(StringUtils.handleRelativeUrl(path, baseUrl));
    }
}