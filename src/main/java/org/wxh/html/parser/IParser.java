package org.wxh.html.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * Created by Maroon on 2016/8/8.
 * project: MySpider
 */
public interface IParser {

    /**
     * 解析本地html
     * @param filePath 文件路径
     * @return
     * @throws IOException
     */
    Set<String> getAllLinks(String filePath) throws IOException;
    /**
     * 获取页面上所有链接
     * @return 所有链接
     */
    Set<String> getAllLinks(InputStream inputStream, String url) throws IOException;

    /**
     * 只提取html页面链接
     * @return html页面链接
     */
    Set<String> getHTMLLinks(InputStream inputStream);

    /**
     * 只提取图片链接
     * @return 图片链接
     */
    Set<String> getIMGLinks(InputStream inputStream);
}
