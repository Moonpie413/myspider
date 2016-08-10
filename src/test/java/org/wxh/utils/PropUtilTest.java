package org.wxh.utils;

import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * 功能说明: <br>
 * 系统版本: v1.0<br>
 * 开发人员: @author wangxh<br>
 * 开发时间: 2016年8月10日<br>
 */
public class PropUtilTest {
	
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * Test method for {@link org.wxh.utils.PropUtil#getProperties(java.lang.String)}.
	 */
	@Test
	public void testGetProperties() {
	}

	/**
	 * Test method for {@link org.wxh.utils.PropUtil#getPropMap(java.lang.String)}.
	 */
	@Test
	public void testGetPropMap() {
		Map<String, Object> resultMap = PropUtil.getPropMap("redis");
		logger.debug(resultMap);
	}

}
