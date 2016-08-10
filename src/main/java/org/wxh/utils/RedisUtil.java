package org.wxh.utils;

import java.util.Map;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.Pool;

/**
 * 功能说明: <br>
 * 系统版本: v1.0<br>
 * 开发人员: @author wangxh<br>
 * 开发时间: 2016年8月10日<br>
 */
public class RedisUtil {
	private static Logger logger = Logger.getLogger(RedisUtil.class);
	private static Pool<Jedis> jedisPool;
	
	/**
	 * 获取jedispool实例
	 * @author wangxh
	 * @return
	 */
	public static Pool<Jedis> getJedisPool() {
		if (jedisPool != null) {
			return jedisPool;
		}
		logger.debug("------jedis pool 初始化------");
		Map<String, Object> resultMap = PropUtil.getPropMap("redis");
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(Integer.parseInt((String)resultMap.get("maxConn")));
		config.setMaxIdle(Integer.parseInt((String)resultMap.get("maxIdle")));
		config.setMaxWaitMillis(Long.parseLong((String)resultMap.get("maxBusyTime")));
		int timeout = Integer.parseInt((String)resultMap.get("timeout"));
		String host = (String) resultMap.get("host");
		int port = Integer.parseInt((String) resultMap.get("port"));
//		String password = (String)resultMap.get("password");
		jedisPool = new JedisPool(config, host, port, timeout);
		return jedisPool;
	}
	
	public static void close() {
		if (jedisPool != null) {
			jedisPool.close();
			logger.debug("------jedis pool 已关闭------");
		}
	}
}
