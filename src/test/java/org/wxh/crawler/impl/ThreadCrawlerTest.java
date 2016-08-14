package org.wxh.crawler.impl;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.wxh.utils.ThreadPoolUtil;

import java.util.Date;

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
        ThreadPoolUtil.getInstance().startAll();
    }

    public static void main(String[] args) {
        Date began = new Date();
        ThreadPoolUtil.getInstance().startAll();
//        Thread thread = new Thread(new ThreadCrawler());
//        thread.start();
        Date end = new Date();
        logger.info("多线程消耗时间: " + (end.getTime() - began.getTime()));
    }
}