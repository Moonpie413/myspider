package org.wxh.utils;

import org.apache.log4j.Logger;
import org.wxh.crawler.impl.ThreadCrawler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maroon on 2016/8/13.
 * project: MySpider
 */
public class ThreadPoolUtil {
    private static Logger logger = Logger.getLogger(ThreadPoolUtil.class);
    private static ThreadPoolUtil poolInstance;
    private ThreadPoolUtil() {}

    // 活动线程数，也用来初始化值为10
    public static int THREAD_NUM = 10;
    private List<Thread> threadList = new ArrayList<>(THREAD_NUM);

    //初始化线程池
    private void init() {
        ThreadCrawler crawler = new ThreadCrawler();
        for (int i=0; i<THREAD_NUM; i++) {
            threadList.add(new Thread(crawler, "爬虫线程" + i));
        }
        logger.debug("线程池初始化成功");
    }

    public int getListNum() {
        return threadList.size();
    }

    public void addThread(Thread thread) {
        threadList.add(thread);
    }

    public static ThreadPoolUtil getInstance() {
        if (poolInstance == null) {
            poolInstance = new ThreadPoolUtil();
            poolInstance.init();
            return poolInstance;
        }
        return poolInstance;
    }

    public void startAll() {
        if (threadList != null) {
            for (Thread thread : threadList) {
                thread.start();
            }
        }
    }
}
