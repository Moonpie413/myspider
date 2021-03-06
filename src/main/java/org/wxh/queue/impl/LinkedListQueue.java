package org.wxh.queue.impl;

import org.wxh.queue.IQueue;

import java.util.LinkedList;

/**
 * Created by Maroon on 2016/8/7.
 * project: MySpider
 */
public class LinkedListQueue implements IQueue {

    public LinkedList<String> linkedList = new LinkedList<>();

    @Override
    public void inQueue(String url) {
        this.linkedList.addLast(url);
    }

    @Override
    public String deQueue() {
        return this.linkedList.removeFirst();
    }

    @Override
    public boolean isEmpty() {
        return this.linkedList.isEmpty();
    }

    @Override
    public boolean contains(Object object) {
        return this.linkedList.contains(object);
    }

    @Override
    public String toString() {
        return "LinkedListQueue{" +
                "linkedList=" + linkedList +
                '}';
    }
}
