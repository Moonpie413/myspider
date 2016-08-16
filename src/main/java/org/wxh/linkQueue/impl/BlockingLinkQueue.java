package org.wxh.linkQueue.impl;

import org.wxh.linkQueue.ILinkQueue;

import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Maroon on 2016/8/16.
 * project: MySpider
 */
public class BlockingLinkQueue implements ILinkQueue {

    // 保存未访问url
    private BlockingQueue<String> unvisedURL;
    //保存已访问队列
    private HashSet<String> visedURL;

    public BlockingLinkQueue(int queueNum) {
        this.unvisedURL = new ArrayBlockingQueue<>(queueNum);
        this.visedURL =  new HashSet<>();
    }

    @Override
    public void addUnvisedUrl(String url) {
        // put方法再队列满时阻塞
        try {
            this.unvisedURL.put(url);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addVisedUrl(String url) {
        return this.visedURL.add(url);
    }

    @Override
    public Integer getVisedUrlNum() {
        return this.visedURL.size();
    }

    @Override
    public boolean removeVisedUrl(String url) {
        return this.visedURL.remove(url);
    }

    @Override
    public boolean isVisedUrlEmpty() {
        return this.visedURL.isEmpty();
    }

    @Override
    public boolean isUnVisedUrlEmpty() {
        return this.unvisedURL.isEmpty();
    }

    @Override
    public String unVisedUrlDeque() {
        String result = null;
        try {
            result = this.unvisedURL.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean visedContains(String url) {
        return this.visedURL.contains(url);
    }

    @Override
    public boolean unvisedContains(String url) {
        return this.unvisedURL.contains(url);
    }
}
