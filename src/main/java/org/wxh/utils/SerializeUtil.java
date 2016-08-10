package org.wxh.utils;

import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by Maroon on 2016/8/6.
 * project: MySpider
 */

/**
 * 序列化工具类
 */
public class SerializeUtil {

    private static Logger logger = Logger.getLogger(SerializeUtil.class);

    public static byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oops = new ObjectOutputStream(baos);
        oops.writeObject(object);
        byte[] bytes = baos.toByteArray();
        if (bytes == null) {
            logger.error("对象" + object + "序列化失败");
        }
        return baos.toByteArray();
    }

    public static Object unserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        Object object = null;
        if (bytes != null) {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream oips = new ObjectInputStream(bais);
            object = oips.readObject();
            if (object == null) {
                logger.error("反序列化失败");
            }
        }
        return object;
    }
}
