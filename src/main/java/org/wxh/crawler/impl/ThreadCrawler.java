package org.wxh.crawler.impl;

import org.apache.log4j.Logger;
import org.wxh.constant.Constant;
import org.wxh.html.parser.IParser;
import org.wxh.html.parser.JsoupParserImpl;
import org.wxh.httphandle.IFileDownload;
import org.wxh.httphandle.LocalFileDownload;
import org.wxh.linkQueue.impl.HashLinkedQueue;
import org.wxh.utils.StringUtils;
import org.wxh.utils.FakeThreadPoolUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Maroon on 2016/8/13.
 * project: MySpider
 */
public class ThreadCrawler implements Runnable {
    private static List<String> initSeeds;
    // 等待线程数
    private static int waitCount = 0;
    private IParser parser = new JsoupParserImpl();
    static {
        initSeeds = new ArrayList<>();
        initSeeds.add(Constant.BASE_URL);
    }

    private Logger logger = Logger.getLogger(this.getClass());

    private static void initWithSeed(List<String> seedList) {
        seedList.forEach(HashLinkedQueue::addUnvisedUrl);
    }

    static {
        if (HashLinkedQueue.isUnVisedUrlEmpty()) {
            initWithSeed(initSeeds);
        }
    }

    /**
     * 获取未访问URL，若队列为空则返回空
     * @return 未访问队列中的url
     * @throws InterruptedException
     */
    public synchronized String dequedURL() throws InterruptedException {
        //关键点 循环返回url，必须要用while，因为队列为空可能只是暂时的状态，要一直检查
        while (true) {
            if (!HashLinkedQueue.isUnVisedUrlEmpty()) {
                String resultUrl = StringUtils.handleRelativeUrl(HashLinkedQueue.unVisedUrlDeque(), Constant.BASE_URL);
                enqueueVised(resultUrl);
                logger.debug("[" + Thread.currentThread().getName() + "] url [" + resultUrl + "] 出队,准备爬取");
                return resultUrl;
            } else {
                // 如果其他线程都在等待则全部唤醒
                if (waitCount >= FakeThreadPoolUtil.THREAD_NUM - 1) {
                    waitCount = 0;
                    logger.debug("所有线程都在等待，尝试唤醒其他线程");
                    notifyAll();
                    // 线程等待1秒，如果队列依旧为空则退出程序
                    Thread.sleep(1000);
                    if (HashLinkedQueue.isUnVisedUrlEmpty()) {
                        logger.debug("所有线程都没事干了，洗洗睡吧");
                        System.exit(0);
                    }
                    return null;
                }
                else {
                    // 若其他线程还有在运行的则进入等待状态
                    waitCount++;
                    logger.debug("线程" + Thread.currentThread().getName() + "进入等待状态");
                    logger.debug("当前等待线程数量: " + waitCount);
                    wait();
                }
            }
        }
    }

    public synchronized void enqueueVised(String url) {
        // 添加到已访问队列
        if (!HashLinkedQueue.visedContains(url)) {
            HashLinkedQueue.addVisedUrl(url);
            logger.debug("[" + Thread.currentThread().getName() +  "]将 url [" + url + "] 添加到已访问队列");
        }
    }

    public synchronized void enqueUnvised(String url) {
        if (StringUtils.isNotBlank(url)) {
            if (!HashLinkedQueue.visedContains(url) && !HashLinkedQueue.unvisedContains(url)) {
                HashLinkedQueue.addUnvisedUrl(url);
                logger.debug("[" + Thread.currentThread().getName() +  "] 将新的待访问url [" + url + "] 入队");
                // 等待线程数大于等于最大线程数减一(只剩下自己或全都睡着了)时唤醒
                if (waitCount >= FakeThreadPoolUtil.THREAD_NUM - 1) {
                    logger.debug("------ 线程们好像都睡着了，拉起来干活 ------");
                    notifyAll();
                }
                waitCount = 0;
            }
        }
    }

    public synchronized void parse(String path) {
        logger.debug("[" + Thread.currentThread().getName() + "] 正在解析文件: [" + path + "]");
        Set<String> urlLinks = null;
        try {
            // 解析url放到urlLinks
            urlLinks = parser.getHTMLLinks(path);
            logger.debug("[" + Thread.currentThread().getName() + "] 文件解析完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (urlLinks != null) urlLinks.forEach(this::enqueUnvised);
    }

    @Override
    public void run() {
        IFileDownload fileDownload = new LocalFileDownload();
        logger.debug("[" + Thread.currentThread().getName() + "] " + "开始执行");
        String todoUrl;
        try {
            // 一直从队列中获取url，为空的时候则什么都不做
            while ((todoUrl = dequedURL()) != null) {
                String path = fileDownload.downloadFile(todoUrl);
                parse(path);
            }
        } catch (InterruptedException e) {
            logger.error("线程 [" + Thread.currentThread().getName() + "]" + "发生错误, 错误信息: ");
            e.printStackTrace();
        }
    }
}
