package org.wxh.utils;

import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Maroon on 2016/8/7.
 * project: MySpider
 */
public class PropUtil {

    private static Logger logger = Logger.getLogger(PropUtil.class);
    private static Properties prop = new Properties();

    private PropUtil() {}

    public static Properties getProperties(String propName) throws IOException {
        String resourceName = propName + ".properties";
        System.out.println(PropUtil.class.getClassLoader().getResource(""));
        InputStream in = PropUtil.class.getClassLoader().getResourceAsStream(resourceName);
        try {
            if (in != null) {
                prop.load(in);
                logger.debug("读取配置文件{ " + propName + " }成功");
            } else {
                throw new FileNotFoundException(resourceName + " not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return prop;
    }

}
