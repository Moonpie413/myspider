package org.wxh.utils;

import org.apache.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Maroon on 2016/8/7.
 * project: MySpider
 */
public class EnvironmentUtilTest {

    Logger logger = Logger.getLogger(this.getClass());

    @Test
    public void testGetDownloadPath() throws Exception {
        logger.debug(EnvironmentUtil.getDownloadPath());
    }
}