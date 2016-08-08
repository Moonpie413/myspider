package org.wxh.crawler.impl;

import org.junit.Before;
import org.junit.Test;
import org.wxh.crawler.ICrawler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Maroon on 2016/8/8.
 * project: MySpider
 */
public class CrawlerImplTest {
    private ICrawler crawler;

    @Before
    public void init() {
        List<String > seedList = new ArrayList<>();
        seedList.add("https://www.douban.com/");
        this.crawler = new CrawlerImpl(seedList);
    }

    @Test
    public void testCrawing() throws Exception {
        this.crawler.crawing();
    }
}