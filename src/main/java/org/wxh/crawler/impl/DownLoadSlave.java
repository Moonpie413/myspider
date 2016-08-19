package org.wxh.crawler.impl;

import org.apache.log4j.Logger;
import org.wxh.crawler.ICrawler;
import org.wxh.crawler.MyCrawler;
import org.wxh.httphandle.IFileDownload;
import org.wxh.httphandle.LocalFileDownload;
import org.wxh.linkQueue.ILinkQueue;
import org.wxh.utils.StringUtils;

/**
 * Created by Maroon on 2016/8/16.
 * project: MySpider
 */
public class DownLoadSlave implements Runnable{
    private Logger logger = Logger.getLogger(this.getClass());
    private MyCrawler crawler;

    //存储处理的url
    private ILinkQueue linkQueue;

    public DownLoadSlave(ILinkQueue linkQueue, MyCrawler crawler) {
        this.linkQueue = linkQueue;
        this.crawler = crawler;
    }

    public String getNextURL() {
        return this.linkQueue.unVisedUrlDeque();
    }

    public boolean addToVised(String url) {
        return this.linkQueue.addVisedUrl(url);
    }

    public String downloadFile(String url) {
        IFileDownload fileDownload = new LocalFileDownload();
        String filePath = fileDownload.downloadFile(url);
        if (StringUtils.isBlank(filePath)) {
            logger.debug("[" + Thread.currentThread().getName() + "] " + "下载文件出错，返回地址为空");
            return null;
        }
        return filePath;
    }

    @Override
    public void run() {
        logger.debug("[" + Thread.currentThread().getName() + "] " + "开始执行");
        String todoUrl;
        // 一直从队列中获取url，为空的时候则什么都不做
        while ((todoUrl = this.getNextURL()) != null) {
            // 添加到已访问队列
            this.addToVised(todoUrl);
            String path = this.downloadFile(todoUrl);
            // 每次循环结束后回调
            if (StringUtils.isNotBlank(path)) {
                this.crawler.downLoadCallback(path);
            }
        }
    }
}
