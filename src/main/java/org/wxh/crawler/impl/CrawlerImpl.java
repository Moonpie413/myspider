package org.wxh.crawler.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.wxh.crawler.ICrawler;
import org.wxh.html.parser.IParser;
import org.wxh.html.parser.JsoupParserImpl;
import org.wxh.httphandle.IFileDownload;
import org.wxh.httphandle.LocalFileDownload;
import org.wxh.linkQueue.ILinkQueue;
import org.wxh.linkQueue.impl.HashLinkedQueue;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by Maroon on 2016/8/8.
 * project: MySpider
 */
public class CrawlerImpl implements ICrawler {
    private Logger logger = Logger.getLogger(this.getClass());
    private ILinkQueue linkQueue;
    private IParser parser;

    public CrawlerImpl(List<String> seedList) {
        this.linkQueue = new HashLinkedQueue();
        this.parser = new JsoupParserImpl();
        this.initWithSeed(seedList);
    }

    public void initWithSeed(List<String> seedList) {
        for (String seed : seedList) {
            this.linkQueue.addUnvisedUrl(seed);
        }
    }

    @Override
    public void crawing() {
        IFileDownload fileDownload = new LocalFileDownload();
        while (!this.linkQueue.isUnVisedUrlEmpty() && this.linkQueue.getVisedUrlNum() < 10) {

            logger.debug("visedUrlNum" + this.linkQueue.getVisedUrlNum());

            String target = this.linkQueue.unVisedUrlDeque();
            if (StringUtils.isBlank(target)) {
                this.logger.error("请求的url不存在");
            }
            String path = fileDownload.downloadFile(target);
            if (StringUtils.isBlank(path)) {
                this.logger.error("下载文件不成功");
            }
            logger.info(target + "已下载至 -> " + path);
            this.linkQueue.addVisedUrl(target);
            try {
                Set<String> urlLinks = this.parser.getAllLinks(path);
                for (String unvisedLink : urlLinks) {
                    this.linkQueue.addUnvisedUrl(unvisedLink);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
