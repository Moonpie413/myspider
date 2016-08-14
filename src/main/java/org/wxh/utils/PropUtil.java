package org.wxh.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * Created by Maroon on 2016/8/7.
 * project: MySpider
 */
public class PropUtil {

    private static Logger logger = Logger.getLogger(PropUtil.class);

    private PropUtil() {}

    /**
     * 获取properties对象
     * @author wangxh
     * @param fileName 文件名
     * @return
     * @throws IOException
     */
    public static Properties getProperties(String fileName) throws IOException {
        Properties prop = new Properties();
    	String resourceName = null;
    	if (StringUtils.isNotBlank(fileName)) {
			resourceName = fileName + ".properties";
		}
        try (InputStream in = PropUtil.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (in != null) {
                prop.load(in);
                logger.debug("读取配置文件 {" + fileName + "} 成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
    
    /**
     * 获取属性配置文件的map
     * @author wangxh
     * @param fileName 文件名
     * @return
     */
    public static Map<String, Object> getPropMap(String fileName) {
        Properties prop = new Properties();
        Map<String, Object> resultMap = new HashMap<>();
    	String resourceName = null;
    	if (StringUtils.isNotBlank(fileName)) {
			resourceName = fileName + ".properties";
		}
        try (InputStream in = PropUtil.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (in != null) {
                prop.load(in);
                logger.debug("读取配置文件 {" + fileName + "} 成功");
            }
            Set<Entry<Object, Object>> set = prop.entrySet();
            for (Entry<Object, Object> entry : set) {
                resultMap.put((String) entry.getKey(), entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    	return resultMap;
    }

}
