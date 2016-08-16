package org.wxh.crawler;

/**
 * Created by Maroon on 2016/8/16.
 * project: MySpider
 */
public interface IThreadCrawler {
    String getNextURL();
    boolean addToVised();
    void addToUnvised();
    boolean parseFile(String file);
    boolean downloadFile(String url);
}
