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

    private int downloadThreadNum = 6;
    private int parserThreadNum = 3;

    // 爬取的url上限，0表示没有上限
    private int maxTaskNum = 0;

    private Logger logger = Logger.getLogger(this.getClass());

    public MyCrawler(List<String> initSeeds) {
        this.linkQueue = new BlockingLinkQueue(100000);
        this.blockingQueue = new ArrayBlockingQueue(1000);
        this.downLoadSlave = new DownLoadSlave(this.linkQueue, this);
        this.parserSlave = new ParserSlave(this.blockingQueue, this);
        this.init(initSeeds);
    }

    public MyCrawler(List<String> initSeeds, int downloadThreadNum, int parserTreadNum) {
        this(initSeeds);
        this.initThreads(downloadThreadNum, parserTreadNum);
    }

    public MyCrawler(List<String> initSeeds, int downloadThreadNum, int parserTreadNum, int maxTaskNum) {
        this(initSeeds, downloadThreadNum, parserTreadNum);
        this.maxTaskNum = maxTaskNum;
        this.downLoadSlave.setMaxTask(this.maxTaskNum);
        this.parserSlave.setMaxTask(this.maxTaskNum);
    }

    private void init(List<String> seedList) {
        for (String url : seedList) {
            this.linkQueue.addUnvisedUrl(url);
        }
    }

    private void initThreads() {
        for (int i = 0; i < this.downloadThreadNum; i++) {
            Thread threadTemp = new Thread(this.downLoadSlave, "下载线程：" + i);
            logger.debug("[" + threadTemp.getName() + "] 初始化成功");
            threadTemp.start();
        }
        for (int j = 0; j < this.parserThreadNum; j++) {
            Thread threadTemp = new Thread(this.parserSlave, "解析线程：" + j);
            logger.debug("[" + threadTemp.getName() + "] 初始化成功");
            threadTemp.start();
        }
    }

    private void initThreads(int downloadThreadNum, int parserThreadNum) {
        this.setDownloadThreadNum(downloadThreadNum);
        this.setParserThreadNum(parserThreadNum);
        this.initThreads();
    }

    public void parserCallback(String url) {
        if (StringUtils.isNotBlank(url) && !this.linkQueue.unvisedContains(url) && !this.linkQueue.visedContains(url)) {
            logger.debug("[" + Thread.currentThread().getName() + "] 把 [" + url + "] 添加到待下载队列");
            logger.debug("当前待下载队列长度：" + this.linkQueue.getUnVisedUrlSize());
            this.linkQueue.addUnvisedUrl(url);
        }
    }

    public void downLoadCallback(String url) {
        if (StringUtils.isNotBlank(url) && !this.blockingQueue.contains(url)) {
            try {
                logger.debug("[" + Thread.currentThread().getName() + "] 把 [" + url + "] 添加到待解析队列");
                logger.debug("当前待解析队列长度：" + this.blockingQueue.size());
                this.blockingQueue.put(url);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDownloadThreadNum(int downloadThreadNum) {
        this.downloadThreadNum = downloadThreadNum;
    }

    public void setParserThreadNum(int parserThreadNum) {
        this.parserThreadNum = parserThreadNum;
    }

    public ILinkQueue getLinkQueue() {
        return linkQueue;
    }


    @Override
    public void crawing() {
        this.initThreads(20,1);
    }

    public static void main(String args[]) {
        List<String> seeds = new ArrayList<>();
        seeds.add("https://www.douban.com/group/134539/");
        MyCrawler myCrawler = new MyCrawler(seeds);
        myCrawler.crawing();
    }

}
