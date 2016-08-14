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
public class HashLinkedQueue {

    /**
     * 保存已访问队列
     */
    public static HashSet<String> vised = new HashSet<>();

    /**
     * 未访问队列
     */
    public static IQueue unvised = new LinkedListQueue();

    public static IQueue getUnvisedUrl() {
        return unvised;
    }

    public static void addUnvisedUrl(String url) {
        if (!StringUtils.isEmpty(url) && !vised.contains(url) && !unvised.contains(url)) {
            unvised.inQueue(url);
        }
    }

    public static void addVisedUrl(String url) {
        if (!StringUtils.isEmpty(url)) {
            vised.add(url);
        }
    }

    public static Integer getVisedUrlNum() {
        return vised.size();
    }

    public static void removeVisedUrl(String url) {
        vised.remove(url);
    }

    public static boolean isVisedUrlEmpty() {
        return vised.isEmpty();
    }

    public static boolean isUnVisedUrlEmpty() {
        return unvised.isEmpty();
    }

    public static String unVisedUrlDeque() {
        return unvised.deQueue();
    }

    public static boolean visedContains(String url) {
        return vised.contains(url);
    }

    public static boolean unvisedContains(String url) {
        return unvised.contains(url);
    }
}
