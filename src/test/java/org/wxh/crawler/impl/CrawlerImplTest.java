package org.wxh.crawler.impl;

import org.junit.Before;
import org.junit.Test;
import org.wxh.crawler.ICrawler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maroon on 2016/8/8.
 * project: MySpider
 */
public class CrawlerImplTest {
    private ICrawler crawler;
    private String baseUrl = "http://www.douban.com/";

    @Before
    public void init() {
        List<String > seedList = new ArrayList<>();
        seedList.add("https://www.douban.com/");
        this.crawler = new CrawlerImpl(seedList, baseUrl);
    }

    @Test
    public void testCrawing() throws Exception {

    }
}