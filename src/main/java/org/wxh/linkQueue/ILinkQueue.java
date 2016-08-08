package org.wxh.linkQueue;

import org.wxh.queue.IQueue;

/**
 * Created by Maroon on 2016/8/7.
 * project: MySpider
 */
public interface ILinkQueue {

    /**
     * 返回未访问的队列
     * @return 未访问队列
     */
    IQueue getUnvisedUrl();

    /**
     * 添加到待访问队列
     * @param url
     */
    void addUnvisedUrl(String url);

    /**
     * 添加到已经访问的队列中
     * @param url
     */
    void addVisedUrl(String url);

    /**
     * 返回已访问队列长度
     * @return
     */
    Integer getVisedUrlNum();

    /**
     * 移除已访问的url
     * @param url
     */
    void removeVisedUrl(String url);

    /**
     * 判断已访问队列是否为空
     * @return
     */
    boolean isVisedUrlEmpty();

    boolean isUnVisedUrlEmpty();

    String unVisedUrlDeque();
}
