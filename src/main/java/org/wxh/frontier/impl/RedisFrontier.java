package org.wxh.frontier.impl;

import org.wxh.beans.CrawUrl;
import org.wxh.frontier.IFrontier;
import org.wxh.utils.RedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

/**
 * Created by Maroon on 2016/8/10.
 * project: MySpider
 */
public class RedisFrontier implements IFrontier {
    private Pool<Jedis> jedisPool;

    public RedisFrontier() {
        super();
        this.jedisPool = RedisUtil.getJedisPool();
    }

    @Override
    public CrawUrl getNext() throws Exception {
        CrawUrl url = null;

        return null;
    }

    @Override
    public boolean pubUrl(CrawUrl url) throws Exception {
        return false;
    }
}
