package org.wxh.linkQueue.impl;

import org.apache.commons.lang3.StringUtils;
import org.wxh.linkQueue.ILinkQueue;
import org.wxh.queue.IQueue;
import org.wxh.queue.impl.LinkedListQueue;

import java.util.HashSet;

/**
 * Created by Maroon on 2016/8/7.
 * project: MySpider
 */
public class HashLinkedQueue implements ILinkQueue {

    /**
     * 保存已访问队列
     */
    private HashSet<String> vised = new HashSet<>();

    /**
     * 未访问队列
     */
    private IQueue unvised = new LinkedListQueue();

    @Override
    public IQueue getUnvisedUrl() {
        return unvised;
    }

    @Override
    public void addUnvisedUrl(String url) {
        if (!StringUtils.isEmpty(url) && !vised.contains(url) && !unvised.contains(url)) {
            unvised.inQueue(url);
        }
    }

    @Override
    public void addVisedUrl(String url) {
        if (!StringUtils.isEmpty(url)) {
            vised.add(url);
        }
    }

    @Override
    public Integer getVisedUrlNum() {
        return vised.size();
    }

    @Override
    public void removeVisedUrl(String url) {
        vised.remove(url);
    }

    @Override
    public boolean isVisedUrlEmpty() {
        return vised.isEmpty();
    }

    @Override
    public boolean isUnVisedUrlEmpty() {
        return unvised.isEmpty();
    }

    @Override
    public String unVisedUrlDeque() {
        return unvised.deQueue();
    }
}
