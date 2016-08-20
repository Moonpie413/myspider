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
    private int maxTask;

    public ParserSlave(BlockingQueue blockingQueue, MyCrawler myCrawler) {
        this.blockingQueue = blockingQueue;
        this.myCrawler = myCrawler;
    }

    public ParserSlave(BlockingQueue blockingQueue, MyCrawler myCrawler, int maxTask) {
        this(blockingQueue, myCrawler);
        this.maxTask = maxTask;
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

    public int getMaxTask() {
        return maxTask;
    }

    public void setMaxTask(int maxTask) {
        this.maxTask = maxTask;
    }

    @Override
    public void run() {
        String filePath;
        while (true) {
            if ((filePath = this.getNextPath()) != null) {
                logger.debug("[" + Thread.currentThread().getName() + "] " + "开始解析 [" + filePath + "]");
                Set<String> urlLinks = this.parseFile(filePath);
                if (urlLinks != null) {
                    for (String url : urlLinks) {
                        this.myCrawler.parserCallback(url);
                    }
                    logger.debug("[" + Thread.currentThread().getName() + "] " + "文件 [" + filePath + "] 解析完成");
                }
            } else {
                try {
                    // 如果等待两秒后队列中没有新的任务就退出
                    logger.debug("[" + Thread.currentThread().getName() + "] " + "进入等待状态");
                    Thread.sleep(2000);
                    if (this.getNextPath() == null) {
                        logger.debug("[" + Thread.currentThread().getName() + "] " + "已退出");
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
