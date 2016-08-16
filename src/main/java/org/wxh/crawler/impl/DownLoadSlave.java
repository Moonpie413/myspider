package org.wxh.crawler.impl;

import org.apache.log4j.Logger;
import org.wxh.httphandle.IFileDownload;
import org.wxh.httphandle.LocalFileDownload;
import org.wxh.linkQueue.ILinkQueue;

/**
 * Created by Maroon on 2016/8/16.
 * project: MySpider
 */
public class DownLoadSlave implements Runnable{
    private Logger logger = Logger.getLogger(this.getClass());

    //存储处理的url
    private ILinkQueue linkQueue;

    public DownLoadSlave(ILinkQueue linkQueue) {
        this.linkQueue = linkQueue;
    }

    public String getNextURL() {
        return this.linkQueue.unVisedUrlDeque();
    }

    public boolean addToVised(String url) {
        return this.linkQueue.addVisedUrl(url);
    }

    public boolean downloadFile(String url) {
        IFileDownload fileDownload = new LocalFileDownload();
        String filePath = fileDownload.downloadFile(url);
        return false;
    }

    @Override
    public void run() {

    }
}
