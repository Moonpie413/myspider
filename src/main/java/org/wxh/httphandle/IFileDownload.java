package org.wxh.httphandle;

import java.io.InputStream;

/**
 * Created by Maroon on 2016/8/7.
 * project: MySpider
 */
public interface IFileDownload {
    /**
     * 获取文件名
     * @return
     */
    String getFileNameByUrl(String url, String ContentType);

    /**
     * 保存网页字节数组
     * @param data
     * @param filePath
     */
    void save(byte[] data, String filePath);

    /**
     * 保存inputstream
     * @param in
     * @param filePath
     */
    void save(InputStream in, String filePath);

    /**
     * 下载url指向的网页并返回路径
     * @param url
     * @return
     */
    String downloadFile(String url);

}
