package org.wxh.crawler.impl;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.wxh.utils.FakeThreadPoolUtil;

/**
 * Created by Maroon on 2016/8/13.
 * project: MySpider
 */
public class ThreadCrawlerTest {
    private static Logger logger = Logger.getLogger(ThreadCrawlerTest.class);

    @Test
    public void testDequedURL() throws Exception {

    }

    @Test
    public void testEnqueueURL() throws Exception {

    }

    @Test
    public void testRun() throws Exception {
        FakeThreadPoolUtil.getInstance().startAll();
    }

    public static void main(String[] args) {
        FakeThreadPoolUtil.getInstance().startAll();
    }
}