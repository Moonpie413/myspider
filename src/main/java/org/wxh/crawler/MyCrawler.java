package org.wxh.crawler;

import org.apache.log4j.Logger;
import org.wxh.crawler.impl.DownLoadSlave;
import org.wxh.crawler.impl.ParserSlave;
import org.wxh.linkQueue.ILinkQueue;
import org.wxh.linkQueue.impl.BlockingLinkQueue;
import org.wxh.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Maroon on 2016/8/19.
 * project: MySpider
 */
public class MyCrawler implements ICrawler {

    private ILinkQueue linkQueue;
    private BlockingQueue blockingQueue;

    private DownLoadSlave downLoadSlave;
    private ParserSlave parserSlave;

    private Logger logger = Logger.getLogger(this.getClass());

    public MyCrawler(List<String> initSeeds) {
        this.linkQueue = new BlockingLinkQueue(100);
        this.blockingQueue = new ArrayBlockingQueue(100);

        this.downLoadSlave = new DownLoadSlave(this.linkQueue, this);
        this.parserSlave = new ParserSlave(this.blockingQueue, this);
        this.init(initSeeds);
    }

    private void init(List<String> seedList) {
        for (String url : seedList) {
            this.linkQueue.addUnvisedUrl(url);
        }
    }

    public void parserCallback(String url) {
        if (StringUtils.isNotBlank(url)) {
            logger.debug("[" + Thread.currentThread().getName() + "] 把 [" + url + "] 添加到待下载队列");
            this.linkQueue.addUnvisedUrl(url);
        }
    }

    public void downLoadCallback(String url) {
        if (StringUtils.isNotBlank(url)) {
            try {
                logger.debug("[" + Thread.currentThread().getName() + "] 把 [" + url + "] 添加到待解析队列");
                this.blockingQueue.put(url);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void crawing() {
        Thread downloadThread1 = new Thread(downLoadSlave, "下载线程-1");
        Thread downloadThread2 = new Thread(downLoadSlave, "下载线程-2");
        Thread downloadThread3 = new Thread(downLoadSlave, "下载线程-3");
        Thread downloadThread4 = new Thread(downLoadSlave, "下载线程-4");
        Thread downloadThread5 = new Thread(downLoadSlave, "下载线程-5");
        Thread parserThread1 = new Thread(parserSlave, "解析线程-1");
        Thread parserThread2 = new Thread(parserSlave, "解析线程-2");
        downloadThread1.start();
        downloadThread2.start();
        downloadThread3.start();
        downloadThread4.start();
        downloadThread5.start();
        parserThread1.start();
        parserThread2.start();
    }

    public static void main(String args[]) {
        List<String> seeds = new ArrayList<>();
        seeds.add("http://bbs.gfan.com/forum-1184-1.html");
        MyCrawler myCrawler = new MyCrawler(seeds);
        myCrawler.crawing();
    }
}
