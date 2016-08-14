package org.wxh.crawler.impl;

import org.apache.log4j.Logger;
import org.wxh.crawler.ICrawler;
import org.wxh.html.parser.IParser;
import org.wxh.html.parser.JsoupParserImpl;
import org.wxh.httphandle.IFileDownload;
import org.wxh.httphandle.LocalFileDownload;
import org.wxh.linkQueue.impl.HashLinkedQueue;
import org.wxh.utils.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by Maroon on 2016/8/8.
 * project: MySpider
 */
public class CrawlerImpl implements ICrawler {
    private Logger logger = Logger.getLogger(this.getClass());
    private IParser parser;
    private String baseUrl;

    public CrawlerImpl(List<String> seedList, String baseUrl) {
        this.parser = new JsoupParserImpl();
        this.initWithSeed(seedList);
        this.baseUrl = baseUrl;
    }

    public void initWithSeed(List<String> seedList) {
        seedList.forEach(HashLinkedQueue::addUnvisedUrl);
    }

    @Override
    public void crawing() {
        IFileDownload fileDownload = new LocalFileDownload();
        while (!HashLinkedQueue.isUnVisedUrlEmpty() && HashLinkedQueue.getVisedUrlNum() < 100) {
            //调用handleRelatuvePath处理url
            String target = StringUtils.handleRelativePath(HashLinkedQueue.unVisedUrlDeque(), this.baseUrl);
            if (StringUtils.isBlank(target)) {
                this.logger.error("请求的url不存在");
            }
            String path = fileDownload.downloadFile(target);
            if (StringUtils.isBlank(path)) {
                this.logger.error("下载文件不成功");
            }
            logger.info(target + "已下载至 -> " + path);
            HashLinkedQueue.addVisedUrl(target);
            try {
                Set<String> urlLinks = this.parser.getAllLinks(path);
                urlLinks.forEach(HashLinkedQueue::addUnvisedUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
