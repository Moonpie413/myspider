package org.wxh.linkQueue;

/**
 * Created by Maroon on 2016/8/7.
 * project: MySpider
 */
public interface ILinkQueue {

    /**
     * 添加到待访问队列
     * @param url
     */
    void addUnvisedUrl(String url);

    /**
     * 添加到已经访问的队列中
     * @param url
     */
    boolean addVisedUrl(String url);

    /**
     * 返回已访问队列长度
     * @return
     */
    Integer getVisedUrlNum();

    /**
     * 移除已访问的url
     * @param url
     */
    boolean removeVisedUrl(String url);

    /**
     * 判断已访问队列是否为空
     * @return
     */
    boolean isVisedUrlEmpty();

    boolean isUnVisedUrlEmpty();

    String unVisedUrlDeque();

    boolean visedContains(String url);

    boolean unvisedContains(String url);

    int getUnVisedUrlSize();
}
