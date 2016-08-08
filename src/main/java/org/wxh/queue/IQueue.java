package org.wxh.queue;

/**
 * Created by Maroon on 2016/8/7.
 * project: MySpider
 */
public interface IQueue {
    /**
     * 入队列
     */
    void inQueue(String url);

    /**
     * 出队列
     */
    String deQueue();

    /**
     * 判断队列是否为空
     * @return 是否为空
     */
    boolean isEmpty();

    /**
     * 判断队列是否包含object
     * @param object
     * @return 是否包含obj
     */
    boolean contains(Object object);

}
