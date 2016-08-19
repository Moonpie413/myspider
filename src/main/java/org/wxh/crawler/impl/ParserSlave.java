package org.wxh.crawler.impl;

import org.apache.log4j.Logger;
import org.wxh.crawler.MyCrawler;
import org.wxh.html.parser.IParser;
import org.wxh.html.parser.JsoupParserImpl;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Maroon on 2016/8/19.
 * project: MySpider
 */
public class ParserSlave implements Runnable {
    private Logger logger = Logger.getLogger(this.getClass());
    private BlockingQueue blockingQueue;
    private MyCrawler myCrawler;
    private IParser parser = new JsoupParserImpl();

    public ParserSlave(BlockingQueue blockingQueue, MyCrawler myCrawler) {
        this.blockingQueue = blockingQueue;
        this.myCrawler = myCrawler;
    }

    /**
     * 获取下一条数据
     * @return 下一条url
     */
    private String getNextPath() {
        String nextUrl = null;
        try {
            nextUrl =  (String) this.blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return nextUrl;
    }

    private Set<String> parseFile(String filePath) {
        logger.debug("[" + Thread.currentThread().getName() + "] 正在解析文件: [" + filePath + "]");
        Set<String> urlLinks = null;
        try {
            // 解析url放到urlLinks
            urlLinks = this.parser.getHTMLLinks(filePath);
            logger.debug("[" + Thread.currentThread().getName() + "] 文件解析完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (urlLinks == null) {
            logger.debug("[" + Thread.currentThread().getName() + "] 文件解析失败");
            return null;
        }
        return urlLinks;
    }

    @Override
    public void run() {
        String filePath;
        while ((filePath = this.getNextPath()) != null) {
            Set<String> urlLinks = this.parseFile(filePath);
            if (urlLinks != null) {
                for (String url : urlLinks) {
                    this.myCrawler.parserCallback(url);
                }
            }
        }
    }
}
