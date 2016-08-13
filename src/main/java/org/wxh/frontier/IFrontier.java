package org.wxh.frontier;

import org.wxh.beans.CrawUrl;

/**
 * Created by Maroon on 2016/8/10.
 * project: MySpider
 */
public interface IFrontier {
    /**
     * 获取下一条数据
     * @return url对象
     * @throws Exception
     */
    public CrawUrl getNext() throws Exception;

    /**
     * 往队列中添加url
     * @param url url对象
     * @return 队列中是否存在url
     * @throws Exception
     */
    public boolean pubUrl(CrawUrl url) throws Exception;
}
